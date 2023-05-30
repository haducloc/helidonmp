package labs.restapi;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/test")
@ApplicationScoped
public class TestResource {

    @Inject
    @ConfigProperty(name = "app.myconfig")
    protected String myconfig;

    @Inject
    protected JsonWebToken principal;

    @Context
    protected jakarta.ws.rs.core.SecurityContext jaxrsContext;

    @Context
    protected io.helidon.security.SecurityContext helidonContext;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String hello() {
        return "Hello world! JsonWebToken=" + principal 
            + ", jaxrsContext=" + jaxrsContext.getUserPrincipal() 
            + ", helidonContext=" + helidonContext.userPrincipal();
    }
}