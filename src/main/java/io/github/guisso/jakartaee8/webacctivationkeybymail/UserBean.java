package io.github.guisso.jakartaee8.webacctivationkeybymail;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean
        implements Serializable {

    @Inject
    private MailServiceLocal mailService;

    // Backing the UI
    private String email;
    private String password;
    private Boolean authenticated;

    private User user;

    public UserBean() {
        user = new User();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //</editor-fold>

    public String processPassword() {

        // Recupera usuário do banco de dados
        User registeredUser
                = FakeDatabase.findByEmail(email);

        if (registeredUser == null) {
            // Se o usuário não está cadastrado,
            // redireciona para página de cadastro
            // com um novo usuário com o email já preenchido
            user = new User();
            user.setEmail(email);
            return "registration?faces-redirect=true";

        } else {
            // Se o usuário está cadastrado, valida acesso
            if (Util.isAuthentic(password, registeredUser)) {
                if (registeredUser.getActive()) {
                    // e redireciona para entrada do sistema
                    // se já tiver validado seu email
                    authenticated = true;
                    return "/admin/index?faces-redirect=true";
                } else {
                    // ou informa que a validação do email
                    // é requerida
                    return "checkemail?faces-redirect=true";
                }

            } else {
                // ou informa falha no acesso
                authenticated = false;
                return null;
            }
        }
    }

    public String userRegistration() {
        user.setKey(UUID.randomUUID());
        user.setActive(false);
        FakeDatabase.saveUser(user);

        System.out.println(">> User registration: "
                + FakeDatabase.findByEmail(
                        user.getEmail()));

        String link = "http://127.0.0.1:8080/WebAcctivationKeyByMail-1.0-SNAPSHOT/Activation?email=" + user.getEmail() + "&activationKey=" + user.getKey();
        System.out.println(">> " + link);

        //
        // Send mail
        // 
        try {
            mailService.sendMail(user.getName(),
                    user.getEmail(), link);
        } catch (MessagingException ex) {
            Logger.getLogger(UserBean.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        // Reset
        password = null;
        authenticated = null;
        user = new User();

        // Reload login page
        return "checkemail?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();
        return "/index?faces-redirect=true";
    }
}
