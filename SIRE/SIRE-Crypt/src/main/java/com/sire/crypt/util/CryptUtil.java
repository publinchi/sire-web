package com.sire.crypt.util;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import java.security.*;

public class CryptUtil {
    private static String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    public static String encryptWithAesCBC(String plaintext, String iv, String key) {
        try {
            PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine(128)),
                    new PKCS7Padding());
            CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key.getBytes()), iv.getBytes());
            aes.init(true, ivAndKey);
            return new String(Base64.encode(cipherData(aes, plaintext.getBytes())));
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptWithAesCBC(String encrypted, String iv, String key) {
        try {
            byte[] ciphertext = Base64.decode(encrypted);
            PaddedBufferedBlockCipher aes = new PaddedBufferedBlockCipher(new CBCBlockCipher(new RijndaelEngine(128)),
                    new PKCS7Padding());
            CipherParameters ivAndKey = new ParametersWithIV(new KeyParameter(key.getBytes()), iv.getBytes());
            aes.init(false, ivAndKey);
            return new String(cipherData(aes, ciphertext));
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] cipherData(PaddedBufferedBlockCipher cipher, byte[] data) throws InvalidCipherTextException {
        int minSize = cipher.getOutputSize(data.length);
        byte[] outBuf = new byte[minSize];
        int length1 = cipher.processBytes(data, 0, data.length, outBuf, 0);
        int length2 = cipher.doFinal(outBuf, length1);
        int actualLength = length1 + length2;
        byte[] cipherArray = new byte[actualLength];
        for (int x = 0; x < actualLength; x++) {
            cipherArray[x] = outBuf[x];
        }
        return cipherArray;
    }

    public static String encrypt(PublicKey key, String plaintext) throws NoSuchAlgorithmException,
            IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.toBase64String(cipher.doFinal(plaintext.getBytes()));
    }

    public static String decrypt(PrivateKey key, String plaintext) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decode(plaintext)));
    }
}