package labs.jaxrs;

import com.appslandia.common.utils.ExceptionUtils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import labs.models.Result;

@Provider
public class WebAppExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException ex) {

        return Response.status(ex.getResponse().getStatus())
                .entity(new Result().asError().message(ExceptionUtils.buildMessage(ex)))
                .type(MediaType.APPLICATION_JSON).build();
    }
}
