
package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import static sun.security.x509.CertificateAlgorithmId.ALGORITHM;

public class Security {

    public static String getHash(String password) {
        
        StringBuffer sb = new StringBuffer();
        byte[] hashedPasseword = null;
        
        try {
            
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes("UTF-8"));
            hashedPasseword = digest.digest();

            for (int i = 0; i < hashedPasseword.length; i++) {
                
                sb.append(Integer.toString((hashedPasseword[i] & 0xff) + 0x100, 64).substring(1));
                
            }

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            
            System.out.println(ex.getMessage());
            
        }
        
        return sb.toString();
    }
    
    private static Random rand = new Random((new Date()).getTime());

    public static String encrypt(String str) {

        BASE64Encoder encoder = new BASE64Encoder();

        byte[] salt = new byte[8];

        rand.nextBytes(salt);

//        return encoder.encode(salt) + encoder.encode(str.getBytes());

        return  encoder.encode(str.getBytes());
        
    }
    
    public static String decrypt(String encstr) {

        if (encstr.length() > 1) {

            String cipher = encstr.substring(0);

            BASE64Decoder decoder = new BASE64Decoder();

            try {

                return new String(decoder.decodeBuffer(cipher));

            } catch (IOException e) {
                
                System.out.println(e);
                
            }

        }

        return null;
    }


}
