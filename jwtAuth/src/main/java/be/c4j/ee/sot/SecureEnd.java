package be.c4j.ee.sot;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.SignedJWT;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Scanner;

/**
 *
 */
@Path("/secure")
@Singleton
public class SecureEnd {

    @Path("{token}")
    @GET
    public void test(@PathParam("token") String token) {
        try {
            System.out.println(getSubject(token));
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String getSubject(String token) throws JOSEException, ParseException, ParseException {
        // Parse the JWE string
        JWEObject jweObject = JWEObject.parse(token);
        String apiKey = jweObject.getHeader().getKeyID();
        System.out.println("apiKey = " + apiKey);
        // Use this apiKey to select the correct privateKey

        String privateContent = readFile("private.jwk");
        RSAKey privateKey = (RSAKey) JWK.parse(privateContent);

        // Decrypt with shared key
        jweObject.decrypt(new RSADecrypter(privateKey));

        // Extract payload
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

        // Check the HMAC, Optional
        //signedJWT.verify(new MACVerifier(apiKey));

        // Retrieve the JWT claims...
        return signedJWT.getJWTClaimsSet().getSubject();
    }

    private static String readFile(String fileName) {
        InputStream keys = SecureEnd.class.getClassLoader().getResourceAsStream(fileName);
        return new Scanner(keys).useDelimiter("\\Z").next();
    }

}
