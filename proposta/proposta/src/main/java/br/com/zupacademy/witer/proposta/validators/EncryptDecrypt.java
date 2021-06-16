package br.com.zupacademy.witer.proposta.validators;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class EncryptDecrypt {

    private static String senha = "apiproposta";
    private static String salt = "39273f5bb6c17cdf5c3faa34e6adc3ed";

    private static TextEncryptor textEncryptor = Encryptors.queryableText(senha, salt);

    public static String encryption(String string) {
        return textEncryptor.encrypt(string);
    }

    public static String decryption(String string) {
        return textEncryptor.decrypt(string);
    }
}
