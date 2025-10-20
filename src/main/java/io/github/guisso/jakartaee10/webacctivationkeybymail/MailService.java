package io.github.guisso.jakartaee10.webacctivationkeybymail;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Stateless
public class MailService
        implements MailServiceLocal {

//    @Resource(name = "java:/WebAppActivation")
    @Resource(name = "java:/MailGunSMTP")
    private Session mailSession;

    @Override
    public void sendMail(String name, String to, String link)
            throws MessagingException {

        MimeMessage mail = new MimeMessage(mailSession);

//        mail.setFrom("webappactivation@outlook.com");
        mail.setFrom("luis.guisso@sandbox32f8a64c46054887862677d7d2932d61.mailgun.org");
        mail.setSubject("System Key Activation");
        mail.setRecipient(Message.RecipientType.TO,
                new InternetAddress(to));

        MimeMultipart content = new MimeMultipart();

        MimeBodyPart body = new MimeBodyPart();
        body.setContent(
                String.format("<html><h1>System Key Activation</h1>"
                        + "<h2>Hi, %s!</h2>"
                        + "<p style=\"background-color: #eee; padding: .25em; border: solid #999 thin; border-left: solid #999 4px;\">Click <a href=\"%s\" style=\"padding: 0 .25em; background-color: #ccc;\">here</a> to <b>activate</b> your account.</p></html>", name, link),
                "text/html; charset=utf-8");

        content.addBodyPart(body);
        mail.setContent(content);
        Transport.send(mail);

    }

}
