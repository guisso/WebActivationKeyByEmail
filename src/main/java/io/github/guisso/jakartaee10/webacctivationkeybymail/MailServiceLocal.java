package io.github.guisso.jakartaee10.webacctivationkeybymail;

import jakarta.ejb.Local;
import jakarta.mail.MessagingException;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Local
public interface MailServiceLocal {

    public void sendMail(String to, String name, String key)
            throws MessagingException;

}
