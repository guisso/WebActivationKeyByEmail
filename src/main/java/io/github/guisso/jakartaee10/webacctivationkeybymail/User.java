package io.github.guisso.jakartaee10.webacctivationkeybymail;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
public class User {

    private Long id;
    private String name;
    private String email; // UNIQUE
    private String plainPassword;
    private String encryptedPassword;
    private String salt;
    private UUID key;
    private Boolean active;

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        String[] hash = Util.hash(plainPassword);
        
        setEncryptedPassword(hash[Util.ENCPASSWD]);
        setSalt(hash[Util.SALT]);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID key) {
        this.key = key;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="hashCode/equals/toString">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "User{" 
                + "id=" + id 
                + ", name=" + name 
                + ", email=" + email 
                + ", encryptedPassword=" + encryptedPassword
                + ", salt=" + salt 
                + ", key=" + key 
                + ", active=" + active 
                + '}';
    }
    //</editor-fold>    
}
