package be.c4j.ee.sot;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 */
public class JWEMain {

    public static void main(String[] args) throws ParseException, JOSEException {
        String publicContent = readFile("public.jwk");
        JWK publicJWK = JWK.parse(publicContent);

        String apiKey = publicJWK.getKeyID();

        System.out.println(createToken((RSAKey) publicJWK, apiKey));
    }

    private static String readFile(String fileName) {
        InputStream keys = JWEMain.class.getClassLoader().getResourceAsStream(fileName);
        return new Scanner(keys).useDelimiter("\\Z").next();
    }

    private static String createToken(RSAKey publicKey, String apiKey) throws JOSEException {

        // Create HMAC signer
        JWSSigner signer = new MACSigner(apiKey);

        // Prepare JWT with claims set
        JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
        claimsSetBuilder.subject("Rudy");
        claimsSetBuilder.audience("SOT");

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSetBuilder.build());

        // Apply the HMAC
        signedJWT.sign(signer);

        // Create JWE object with signed JWT as payload
        JWEObject jweObject = new JWEObject(
                new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM)
                        .contentType("JWT") // required to signal nested JWT
                        .keyID(apiKey)
                        .build(),
                new Payload(signedJWT));

        JWEEncrypter encrypter = new RSAEncrypter(publicKey);

        jweObject.encrypt(encrypter);

        return jweObject.serialize();

    }
}
