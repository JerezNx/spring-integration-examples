package xyz.jerez.spring.email;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import xyz.jerez.spring.email.model.MailMessage;
import xyz.jerez.spring.email.service.MailService;

@Slf4j
@SpringBootTest
class SpringEmailApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailService mailService;

    @Test
    void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("13851827544@163.com");
        message.setTo("772691544@qq.com");
        message.setSubject("This is a test email by spring boot");
        message.setText("测试发送邮件。");
        try {
            mailSender.send(message);
            log.info("发送成功");
        } catch (Exception e) {
            log.warn("发送失败:", e);
        }
    }

    @Test
    void asyncSendMail() throws InterruptedException {
        mailService.send(MailMessage.builder()
                .to(new String[]{"772691544@qq.com"})
                .subject("This is a test email by spring boot")
                .content("测试发送邮件").build());
        Thread.sleep(1000);
    }

}
