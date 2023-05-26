package labs.restapi;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;

@org.eclipse.microprofile.auth.LoginConfig(authMethod = "MP-JWT")
@ApplicationScoped
//@DenyAll
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(

                AuthResource.class,
                TestResource.class,
                ApiResource.class,

                DefaultExceptionMapper.class);
    }
}