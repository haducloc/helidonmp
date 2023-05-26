package labs.beans;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.concurrent.TimeUnit;

import com.appslandia.common.cdi.CDIFactory;
import com.appslandia.common.jose.DsaJwtSigner;
import com.appslandia.common.jose.JoseHeader;
import com.appslandia.common.jose.JoseJsonb;
import com.appslandia.common.jose.JwtPayload;
import com.appslandia.common.jose.JwtSigner;
import com.appslandia.common.jose.JwtToken;
import com.appslandia.common.json.JsonProcessor;
import com.appslandia.common.json.JsonbProcessor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.json.bind.JsonbConfig;

@ApplicationScoped
public class JsonProcessorFactory implements CDIFactory<JsonProcessor> {

    @Produces
    @ApplicationScoped
    @Override
    public JsonProcessor produce() {
        JsonbConfig config = JoseJsonb.newJsonbConfig(true, true);
        return new JsonbProcessor().setConfig(config);
    }

    @Override
    public void dispose(@Disposes JsonProcessor impl) {
        impl.destroy();
    }

    private static KeyPair generateRSKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static void main(String[] args) {

        try {
            KeyPair keyPair = generateRSKeyPair();

            // signer
            JwtSigner signer = DsaJwtSigner.RS256().setJsonProcessor(JoseJsonb.newJsonProcessor())
                    .setPrivateKey(keyPair.getPrivate()).setPublicKey(keyPair.getPublic())
                    .setIss("Issuer1").build();

            JoseHeader header = signer.newHeader();
            JwtPayload payload = signer.newPayload().setExp(1, TimeUnit.DAYS).setIatNow();

            String token = signer.sign(new JwtToken(header, payload));

            System.out.println(token);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
