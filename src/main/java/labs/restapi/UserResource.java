package labs.restapi;

import javax.sql.DataSource;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import labs.entities.User;
import labs.entities.UserModel;
import labs.models.Result;

@Path("/user")
@ApplicationScoped
@PermitAll
public class UserResource {

    @Inject
    @Named("myds") 
    private DataSource ds; 

    @GET
    @Path("/get/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@NotNull @PathParam("userId") Integer userId) {

        if (userId <= 0 || userId >= 100) {
            throw new NotFoundException("User not found");
        }

        var u = new User();
        u.setUserId(userId);
        u.setUsername("user-" + userId);
        return u;
    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Result insert(@Valid UserModel user) {

        return new Result().data(user.getUser().getUsername()).message("user inserted successfully.");
    }

    @GET
    @Path("/conn")
    @Produces(MediaType.APPLICATION_JSON)
    public Result testConn() throws Exception {

        try (var conn = this.ds.getConnection()) {
            String str = this.ds.getConnection().toString();

            return new Result().data(str);
        }
    }
}
