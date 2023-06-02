package labs.jaxrs;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;
import labs.restapi.ApiResource;
import labs.restapi.AuthResource;
import labs.restapi.ReactiveMessagingResource;
import labs.restapi.TestResource;
import labs.restapi.UserResource;

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
                UserResource.class,
                ReactiveMessagingResource.class,
                
                DefaultExceptionMapper.class,
                BeanValidationExceptionMapper.class,
                JsonbContextResolver.class
                );
    }
}