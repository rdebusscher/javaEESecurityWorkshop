package be.c4j.ee.sot;

/**
 *
 */
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 *
 */
public class JWKManager {

    public static void main(String[] args) {
        String xApiKey = UUID.randomUUID().toString();
        JWK jwk = make(2048, KeyUse.SIGNATURE, new Algorithm("PS512"), xApiKey);

        System.out.println("x-api-key");
        System.out.println(xApiKey);

        System.out.println("Private");
        System.out.println(jwk.toJSONString());

        System.out.println("Public");
        System.out.println(jwk.toPublicJWK().toJSONString());
    }

    private static RSAKey make(Integer keySize, KeyUse keyUse, Algorithm keyAlg, String kid) {

        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(keySize);
            KeyPair kp = generator.generateKeyPair();

            RSAPublicKey pub = (RSAPublicKey) kp.getPublic();
            RSAPrivateKey priv = (RSAPrivateKey) kp.getPrivate();

            RSAKey rsaKey = new RSAKey.Builder(pub)
                    .privateKey(priv)
                    .keyUse(keyUse)
                    .algorithm(keyAlg)
                    .keyID(kid)
                    .build();

            return rsaKey;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
