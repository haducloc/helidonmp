package labs.restapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        String jwt = generateJwt(username, userRoles, 45, TimeUnit.MINUTES);

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
                .setPrivateKey(keyUtil.toPrivateKey(loadKeyInPem("jwt_prikey.pem")))
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
}
