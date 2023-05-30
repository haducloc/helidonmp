package labs.restapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.appslandia.common.crypto.KeyFactoryUtil;
import com.appslandia.common.jose.DsaJwtSigner;
import com.appslandia.common.jose.JoseHeader;
import com.appslandia.common.jose.JwtPayload;
import com.appslandia.common.jose.JwtSigner;
import com.appslandia.common.jose.JwtToken;
import com.appslandia.common.json.JsonProcessor;
import com.appslandia.common.utils.IOUtils;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import labs.models.Result;

@Path("/auth")
@ApplicationScoped
public class AuthResource {

    @Inject
    protected JsonProcessor jsonProcessor;

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Result login(@NotNull @QueryParam("username") String username,
            @NotNull @QueryParam("password") String password) throws Exception {

        if ((!"locha".equalsIgnoreCase(username) && !"admin".equalsIgnoreCase(username))
                || !"password".equals(password)) {

            return new Result().asError().message("Invalid credentials");
        }

        String[] userRoles = "locha".equalsIgnoreCase(username) ? new String[] { "user" } : new String[] { "admin" };

        // JWT
        String jwt = generateJwt(username, userRoles, 120, TimeUnit.MINUTES);

        return new Result().data(jwt).message("Login Successfully");
    }

    // JWT: RS256 & SHA256withRSA
    private String generateJwt(String username, String[] userRoles, int expiresIn, TimeUnit expiresInUnit)
            throws Exception {

        // RSA KeyFactoryUtil
        KeyFactoryUtil keyUtil = new KeyFactoryUtil("RSA");

        // signer
        JwtSigner signer = DsaJwtSigner.RS256()
                .setJsonProcessor(this.jsonProcessor)
                .setPrivateKey(buildPrivateKey())
                .setPublicKey(keyUtil.toPublicKey(loadKeyInPem("jwt_pubkey.pem")))
                .setIss("Issuer1").build();

        // Header
        JoseHeader header = signer.newHeader();

        // Payload
        JwtPayload payload = signer.newPayload().setExp(expiresIn, expiresInUnit)
                .setSub(username)

                // MP: groups
                .set("groups", Arrays.asList(userRoles))
                .setIatNow();

        return signer.sign(new JwtToken(header, payload));
    }

    private static String loadKeyInPem(String keyFileName) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                AuthResource.class.getClassLoader().getResourceAsStream("META-INF/" + keyFileName)))) {

            return IOUtils.toString(reader);
        }
    }

    private static PrivateKey buildPrivateKey() throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(PK_BYTES);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    static byte[] PK_BYTES = {
            48, -126, 4, -67, 2, 1, 0, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 4, -126, 4, -89, 48,
            -126, 4, -93, 2, 1, 0, 2, -126, 1, 1, 0, -94, -83, 33,
            111, -35, 15, 73, 26, 25, 102, 88, 29, -27, -12, -34, 45, -59, 22, 1, -50, -44, -40, -108, -40, -79, -105,
            70, 74, 102, 109, 77, 125, -40, -24, -25, 98, -91, 81, -34, 60, 127, 89, -46, 74, -7, 56, -62, 45, 25, 42,
            85, 18, -77, 67, 24, -5, 5, -26, 64, -99, -63, -54, -49, 116, 120, 19, 27, -30, -121, 116, -66, 49, -51,
            121, 85, -73, 43, -21, 4, 64, 68, 51, 82, -89, -57, -1, -45, -44, 126, 73, 5, 11, -8, -65, 74, 83, -128,
            -36, 47, -57, -12, 32, -30, -45, 36, -104, 114, 54, -64, 93, 0, -72, -44, -41, 121, -20, 65, 103, 88, -60,
            83, 39, 122, -97, -119, 49, 86, 3, 98, 13, 84, 33, 14, -80, 32, -9, 80, -105, -10, 68, 29, -51, 121, -66,
            46, 7,
            -83, -65, 99, 87, -109, -90, 107, 79, -91, 65, 125, -13, 42, 44, -74, -44, -7, -53, 47, 40, 49, 122, -61,
            -107, -114, 120, -17, 124, 112, 40, -30, 76, -96, 85, 60, -63, -115, 24, 22, 24, -100, 14, -65, -54, -94,
            62, 34, 11, -115, -85, -45, 12, -114, 105, 37, -52, -20, -78, 116, -27, 32, 105, 47, 28, -61, -20, 37, -77,
            43, 36, 38, -99, 28, 45, -101, -11, 45, 13, 21, -46, -86, -25, -61, -8, 88, -94, 42, 17, 19, -14, 41, 99,
            75, -69, 73, 31, 29, 123, 51, 18, 100, -78, 73, -100, -31, -22, -6, 23, -77, -19, 2, 3, 1, 0, 1, 2, -126, 1,
            0, 53, 15, -108, -82, -8, -93, 114, 60, 53, -103, -72, -41, 51, -71, 29, -71, 63, 38, 80, -5, -90, 88, 104,
            -96, -113, -72, -96, -128, 71, 13, -110, -17, -96, 15, 67, -35, -36, -65, -87, -26, 0, -62, 11, 2, 82, 109,
            45, -124, -23, 70, -14, 13, -47, -13, -22, 83, -126, -106, -102, 111, -6, -108, -116, -5, 87, -23, 31, -72,
            -47, 103, 6, 92, -105, 65, 98, -16, -122, -66, 55, 26, 65, 68, -98, 74, -60, -59, 16, -46, -88, 72, 104, 1,
            -126, 38, -34, -9, 2, -70, -113, 73, 64, -69, 115, 80, -36, -120, 111, 4, -91, -43, 16, 21, -71, -78, -8,
            -3, 83, 78, 2, -39, -78, -89, -41, 106, -17, 36, 109,
            1, -45, 63, 21, -27, -18, -58, -73, 4, -95, 24, -74, 59, 83, 64, -25, 102, -46, -106, -93, 94, 50, 13, -1,
            -107, -36, -91, -126, -93, -80, 78, 53, -21, -11, -105, 54, -67, -123, 26, -116, -79, -99, 38, 9, -12, 8, 2,
            -120, -54, 13, -83, -107, -104, -40, -90, -51, 65, -58, -34, 64, -109, 33, -76, 7, 54, 78, 109, -82, 67,
            -20, 107, 120, 12, -114, -117, 16, -127, -108, 45, -94, 80, -93, 37, 40, -54, -128, 101, -96, -32, 121, 121,
            -37, 10, 44, 52, -80, 71, 98, -99, 66, -121, -46, -12, 84, 70, 37, 72, -126, 49, 113, 121, 74, 113, 115, 62,
            7, -74, 23, 53, 98, 16, 85, -104, 80, -50, -6, -101, 65, -33, 2, -127, -127, 0, -60, 12, -95, 6, -78, -37,
            -3, 106, -11, 31, 17, 73, -88, -9, 127, 72, -105, -79, -12, -34, 67, 113, 57, -33, 97, 67, -66, -2, 10, -62,
            -75, 53, 39, -24, -39, 127, -28, 43, -70, 54, 56,
            -37, 124, 44, -22, 19, 114, -29, -120, 79, 41, 96, 101, 74, 38, 15, -127, -63, -46, -30, 18, -35, 86, -3,
            -2, -73, -12, 124, -99, 13, 27, 108, -90, -52, -18, 28, 127, -118, 42, 63, -22, -56, 90, 82, 51, -71, 25,
            -104, -69, 122, -113, 58, 105, -48, 39, -126, -120, -107, 11, 61, -16, 50, -103, -46, -124, -93, 70, -25,
            23,
            63, 114, 42, 56, -34, -125, 33, -23, -53, -21, 124, -92, -58, 127, -30, -126, -113, 50, 119, 2, -127, -127,
            0, -44, 107, -13, -18, -2, 11, 110, -10, 39, -26, -121, 86, -31, 42, 120, -74, 13, -116, 80, 114, 91, 13,
            66, 30, -4, -117, -101, 10, -110, 91, -128, 70, -82, -102, 40, -9, -66, -17, 75, -125, 39, 49, -60, -97, 12,
            -80, -54, -34, -83, 123, -58, -102, 34, 92, 112, -84, 77, -2, 53, 47, 115, 86, -21, -43, -23, 71, -72, -38,
            99, -19, 27, -48, 123, -22, 46, 22, 0, 73, 97, -105, -78, 41, 116, -56, -25, -21, -17, 16, -84, -76, 78,
            -74, 42, 23, -78, -29, -69, 5, -72, 20, -70, -90, -60, 109, -108, -6, 116, -27, 108, 75, -5, 78, 52, -33,
            -34, -124, -10, 123, 43, 105, -112, 28, -113, -27, -112, -121, -95, -69, 2, -127, -127, 0, -90, -5, 74, -69,
            -11, -29, -62, 57, 51, -122, -66, -90, -54, -81, -29,
            77, -27, 70, -63, -107, -2, 67, -106, 83, 88, -8, -26, -40, 98, 57, -19, 53, -43, 68, 82, -36, -57, 111, 39,
            47, -65, -59, -46, 66, 63, 45, 73, -111, -37, 43, -15, -58, 20, -1, -50, -29, 46, 85, -12, 84, 114, 84, -20,
            31, 114, -79, 36, 32, 111, 116, 22, -125, -72, -98, -101, -64, -46, 48, -48, 67, -104, -86, 5, -92, 54,
            65, -42, 55, 108, -105, -69, 30, -71, -71, 125, -83, -124, -65, -51, -49, 119, -94, -31, -71, -103, 23, -88,
            -16, 115, -80, -86, -128, -93, -25, -126, 49, -124,
            125, 23, -102, 22, -114, -85, -90, 54, 37, -39, -119, 2, -127, -128, 71, -55, -125, -46, -116, -64, 72, 107,
            -97, -24, 100, 108, -108, 3, -54, 111, -101, -4, -125, 99, 98, 42, 4, -30, 123, -18, -94, 34, -36, 123, 87,
            -54, -117, 79, 69, -15, 19, -15, -62, -49, 59, -5, -79, -69, 30, -118, -48, -60, -104, 84, 112, 53, -66,
            -49, 6, -76, -12, 86, -88, -102, -76, -101, -9, 111, 127, 58, -31, -39, -52, 83, 97, 70, -1, -39, -112, 97,
            -105, 15, 53, -112, 18, -127, -72, 25, -3, 102, -38,
            -112, 4, 13, -101, 92, 44, -75, 125, -101, 64, -91, 26, 36, -57, 63, 124, -70, -101, -46, 88, -105, 0, 22,
            93, -125, 124, -110, -5, -100, 107, -64, 12, 87, -52,
            -128, 26, -111, 54, 90, -33, -23, 2, -127, -128, 29, 91, 9, -71, -32, -26, -107, 56, 55, 119, -95, 39, -43,
            -107, 65, -125, -127, 111, -89, -53, -52, 82, -107, 93, -38, -73, 41, 81, -43, 61, 59, -9, -72, 88, 46, -98,
            -110, 26, 118, 36, 28, 29, -49, 80, 21, -82, 60, 4, 103, -15, 96, -91, -93, 116, -126, -86, -30, -21, -42,
            82, 0, 28, -42, 100, 90, -104, -104, 117, 57, 53, -111, -27, 88, 25, -61, -114, 113, 0, 95, -55, 115, -54,
            -113, -48, 82, -102, -72, -46, -35, -14, 84, 95, 95, -20, -106, 118, -112, 90, -26, 12, 23, 126, -115, -4,
            -42, 39, -2, -67, 109, 127, 120, -22, 13, 17, 87, -40, -47, 36, -86, -55, -54, 118, 18, 86, 58, 85, 87, 29
    };

}
