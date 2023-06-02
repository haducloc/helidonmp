package labs.jaxrs;

import com.appslandia.common.json.JsonbProcessor;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JsonbContextResolver implements ContextResolver<Jsonb> {

    static final Jsonb IMPL = JsonbBuilder.create(JsonbProcessor.newConfig(true, true));

    @Override
    public Jsonb getContext(Class<?> type) {
        return IMPL;
    }
}
