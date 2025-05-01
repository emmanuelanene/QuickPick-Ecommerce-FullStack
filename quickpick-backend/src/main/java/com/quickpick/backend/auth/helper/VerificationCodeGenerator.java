package com.quickpick.backend.auth.helper;

import java.security.SecureRandom;

public class VerificationCodeGenerator {

    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(5);

        for(int i=0; i<5; i++) {
            token.append(random.nextInt(10));
        }

        return token.toString();
    }

}
