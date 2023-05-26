package labs.restapi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import labs.entities.User;
import labs.models.Result;

@Path("/user")
@ApplicationScoped
public class UserResource {

    @GET
    @Path("/get/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@NotNull Integer userId) {

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
    public Result insert(@Valid User user) {

        return new Result().data(user.getUsername()).message("user inserted successfully.");
    }
}
