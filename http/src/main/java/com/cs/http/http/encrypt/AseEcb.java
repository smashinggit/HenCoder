package com.cs.http.http.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AseEcb {
    public static String encrypt(String content, String passwd) {
        try {
            Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(passwd.getBytes(), "AES");
            aesECB.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = aesECB.doFinal(content.getBytes("UTF-8"));
            return Base64.encode(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
