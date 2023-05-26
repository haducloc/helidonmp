package labs.beans;

import com.appslandia.common.cdi.CDIFactory;
import com.appslandia.common.jose.JoseJsonb;
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
}
