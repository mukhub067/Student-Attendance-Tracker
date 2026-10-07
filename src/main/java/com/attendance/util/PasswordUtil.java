package com.attendance.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtil {
    private static final int ITERATIONS = 100_000, KEY_BITS = 256;
    private PasswordUtil() { }
    public static String hash(String password) {
        byte[] salt = new byte[16]; new SecureRandom().nextBytes(salt);
        return encode(ITERATIONS, salt, derive(password.toCharArray(), salt, ITERATIONS));
    }
    public static boolean matches(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            if (parts.length != 3) return false;
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expected = Base64.getDecoder().decode(parts[2]);
            byte[] actual = derive(password.toCharArray(), salt, iterations);
            return java.security.MessageDigest.isEqual(expected, actual);
        } catch (Exception ignored) { return false; }
    }
    private static byte[] derive(char[] password, byte[] salt, int iterations) {
        try { KeySpec spec = new PBEKeySpec(password, salt, iterations, KEY_BITS); return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        catch (Exception ex) { throw new IllegalStateException("Could not create password hash", ex); }
    }
    private static String encode(int iterations, byte[] salt, byte[] hash) { return iterations + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash); }
}
