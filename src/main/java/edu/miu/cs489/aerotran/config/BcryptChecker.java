package edu.miu.cs489.aerotran.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptChecker {
    public static void main(String[] args) {
        String encodedPassword = "$2a$10$6YdL/CoLavnnVDNeyLa7Z.qUvRZ.DHmy3Gh0bvPVSKNp7ovA3d5De";
        String rawPassword = "tn@em";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isMatch = encoder.matches(rawPassword, encodedPassword);

        System.out.println("Password match: " + isMatch);
    }
}
