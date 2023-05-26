package labs.restapi;

import io.helidon.security.annotations.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import labs.models.Result;

@Path("/api")
@ApplicationScoped
public class ApiResource {

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Result user() {

        var result = new Result();
        result.setMessage("user accessed");
        return result;
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Result admin() {
        var result = new Result();
        result.setMessage("admin accessed");
        return result;
    }

    @GET
    @Path("/security")
    @Produces(MediaType.TEXT_PLAIN)
    @Authenticated
    public String securityContext(@Context SecurityContext context) {
        return "Hello, " + context.getUserPrincipal().getName();
    }
}
