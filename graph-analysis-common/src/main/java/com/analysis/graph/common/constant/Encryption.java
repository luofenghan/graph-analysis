package com.analysis.graph.common.constant;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * Created by cwc on 2017/4/14 0014.
 */
public enum Encryption {
    DATA_BASE(SecKey.DB, Salt.DB),
    COOKIE(SecKey.COOKIE, Salt.COOKIE);

    public static final int PASSWORD_ENCODER_STRENGTH = 8;    // mock id for calculate fof_fund
    private final SecKey secKey;
    private final Salt salt;
    private static final String ENCRYPTION_ALGORITHM = "AES";

    Encryption(SecKey key, Salt salt) {
        this.secKey = key;
        this.salt = salt;
    }

    private enum SecKey {
        DB("Emj^[tgt9?4V$UP."),
        COOKIE("vP0_J&)DCv-z%8sC");
        private final Key key;

        SecKey(String secKey) {
            this.key = new SecretKeySpec(secKey.getBytes(), ENCRYPTION_ALGORITHM);
        }

        Key getKey() {
            return key;
        }
    }

    private enum Salt {
        DB("vP0_J&)DCv-z%8sC"),
        COOKIE("Cv-zL0-2");
        private final String salt;

        Salt(String salt) {
            this.salt = salt;
        }

        String getSalt() {
            return salt;
        }
    }

    public String encrypt(final String sourceValue) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secKey.getKey());
            byte[] encValue = cipher.doFinal(sourceValue.getBytes());
            return Base64.getEncoder().encodeToString(encValue);
        } catch (Exception e) {
            String errorMsg = String.format("failed to get encryption string for %s", sourceValue);
            throw new RuntimeException(errorMsg);
        }

    }

    public String encryptWithSalt(final String sourceValue) {
        return encrypt(sourceValue + salt.getSalt());
    }

    public String decrypt(final String encryptedValue) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secKey.getKey());
            byte[] decorVal = Base64.getDecoder().decode(encryptedValue);
            return new String(cipher.doFinal(decorVal));
        } catch (Exception e) {
            throw new RuntimeException(String.format("failed to get decryption string for %s", encryptedValue));
        }
    }

    public String decryptWithSalt(final String encryptedValueWithSalt) {
        String valueWithSalt = decrypt(encryptedValueWithSalt);
        if (valueWithSalt.endsWith(salt.getSalt())) {
            return valueWithSalt.substring(0, valueWithSalt.length() - salt.getSalt().length());
        } else {
            throw new IllegalArgumentException("Input salt is invalid, decryption failed");
        }
    }


}
