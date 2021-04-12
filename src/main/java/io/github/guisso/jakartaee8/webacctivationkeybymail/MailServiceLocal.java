package io.github.guisso.jakartaee8.webacctivationkeybymail;

import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Local
public interface MailServiceLocal {
    
    public void sendMail(String to, String name, String key)
            throws MessagingException;
    
}
