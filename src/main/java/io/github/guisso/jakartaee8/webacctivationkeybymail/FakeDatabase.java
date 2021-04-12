package io.github.guisso.jakartaee8.webacctivationkeybymail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
public class FakeDatabase
        implements Serializable {

    private static Map<String, User> users;

    public static void init() {
        users = new HashMap<>();

        // - Admin ;)
        String[] passwordAndSalt = Util.hash("123");

        User user = new User();
        user.setId(1001L);
        user.setName("Andréa Zaira");
        user.setEmail("az@mail.com");
        user.setEncryptedPassword(passwordAndSalt[Util.ENCPASSWD]);
        user.setSalt(passwordAndSalt[Util.SALT]);
        user.setKey(UUID.randomUUID());
        user.setActive(true);

        users.put(user.getEmail(), user);
        System.out.println(">> " + user);
    }

    public static void saveUser(User user) {
        if (users == null) {
            init();
        }
        
        users.put(user.getEmail(), user);

        System.out.println("\r\rkeySet()");
        for (String email : users.keySet()) {
            System.out.println("\rUser\r"
                    + users.get(email));
        }
        // OU
        System.out.println("\r\rentrySet()");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User u = entry.getValue();
            String email = entry.getKey(); // Example only
            System.out.println("\r\rUser\r" + u);
        }
        // OU
        System.out.println("\r\rvalues()");
        for (User u : users.values()) {
            System.out.println("\r\rUser\r" + u);
        }
    }

    public static User findByEmail(String email) {
        if (users == null) {
            init();
        }
        
        if (email != null) {
            return users.get(email);
        }
        return null;
    }
}
