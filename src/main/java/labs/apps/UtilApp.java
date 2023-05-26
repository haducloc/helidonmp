package labs.apps;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import com.appslandia.common.crypto.PKIUtils;

public class UtilApp {

    public static void main(String[] args) {

        try {

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            System.out.println(PKIUtils.toPemEncoded(keyPair.getPrivate()));

            System.out.println();
            System.out.println(PKIUtils.toPemEncoded(keyPair.getPublic()));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
