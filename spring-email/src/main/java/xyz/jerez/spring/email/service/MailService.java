package xyz.jerez.spring.email.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;
import xyz.jerez.spring.email.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2020/10/26 14:18
 */
@Slf4j
@Component
public class MailService {

    /**
     * 发件人
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 时间格式
     */
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private JavaMailSender mailSender;

    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(1, 1,
            10L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(512), new CustomizableThreadFactory("email-thread-pool-"));

    public void send(MailMessage mail) {
        THREAD_POOL.execute(() -> {
            File attachment = mail.getAttachment();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, attachment != null);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject(mail.getSubject());
                mimeMessageHelper.setTo(mail.getTo());
                mimeMessageHelper.setText(sign(mail.getContent()), true);
                // 添加抄送
                if (mail.getCc() != null) {
                    mimeMessageHelper.setCc(mail.getCc());
                }
                // 添加附件
                if (attachment != null && mimeMessageHelper.isMultipart()) {
                    mimeMessageHelper.addAttachment(attachment.getName(), attachment);
                }
                // 发送邮件
                log.info("\n\nMail Message:{}\n\n", mail);
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                // 发送失败，重新放回队列
                send(mail);
                log.error("【mail send error】", e);
            }
        });
    }

    /**
     * 添加签名
     * @param content 正文
     * @return 正文
     */
    private String sign(String content) {
        StringBuffer sb = new StringBuffer(content);
        sb.append("<hr style=\" background-color: #E0E0E0;  \" />");
        sb.append("<span style=\"font-size: 14px; \">测试</span>");
        sb.append("<br />");
        sb.append("<span style=\"font-size: 14px; \">T E S T.</span>");
        sb.append("<br />");
        sb.append(LocalDateTime.now().format(FORMAT));
        return sb.toString();
    }
}
