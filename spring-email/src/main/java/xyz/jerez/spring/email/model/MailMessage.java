package xyz.jerez.spring.email.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Builder
@Getter
@Setter
public class MailMessage {
    /**
     * 收件人地址
     */
    private String[] to;

    /**
     * 抄送人地址
     */
    private String[] cc;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 邮件附件
     */
    private File attachment;

    /**
     * 昵称
     */
    private String nickname;

}
