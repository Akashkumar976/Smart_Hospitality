package com.example.hospital;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCrypt {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode("12345");
System.out.println(hashedPassword);


        // String rawPassword = "12345";
        // String hashedPassword = "$2a$10$D4G5f18o7aMMfwasBL7GHe1.NpQSZ9F6H1fVn.sQz1kzj0/GlZk6O";

        // boolean matches = encoder.matches(rawPassword, hashedPassword);
        // System.out.println("Password matches? " + matches);
    }
}
