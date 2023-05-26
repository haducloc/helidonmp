package labs.restapi;

import io.helidon.security.annotations.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import labs.models.Result;

@Path("/api")
@ApplicationScoped
public class ApiResource {

    @Context
    protected jakarta.ws.rs.core.SecurityContext context;

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Result user() {
        return new Result().message("user access: " + context.getUserPrincipal());
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Result admin() {
        return new Result().message("admin access: " + context.getUserPrincipal());
    }

    @GET
    @Path("/security")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public Result securityContext() {
        return new Result().message("securityContext: " + context.getUserPrincipal());
    }
}
