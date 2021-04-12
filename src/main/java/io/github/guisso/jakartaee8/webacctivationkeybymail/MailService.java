package io.github.guisso.jakartaee8.webacctivationkeybymail;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Stateless
public class MailService
        implements MailServiceLocal {

    @Resource(name = "java:/WebAppActivation")
    private Session mailSession;

    @Override
    public void sendMail(String name, String to, String link)
            throws MessagingException {

        MimeMessage mail = new MimeMessage(mailSession);

        mail.setFrom("webappactivation@outlook.com");
        mail.setSubject("System Key Activation");
        mail.setRecipient(Message.RecipientType.TO,
                new InternetAddress(to));

        MimeMultipart content = new MimeMultipart();

        MimeBodyPart body = new MimeBodyPart();
        body.setContent(
                String.format("<html><h1>System Key Activation</h1>"
                        + "<h2>Hi, %s!</h2>"
                        + "<p>Clik <a href=\"%s\" style=\"padding: 0 .25em; background-color: #ccc;\">here</a> to <b>activate</b> your account.</p></html>", name, link),
                "text/html; charset=utf-8");

        content.addBodyPart(body);
        mail.setContent(content);
        Transport.send(mail);

    }

}
