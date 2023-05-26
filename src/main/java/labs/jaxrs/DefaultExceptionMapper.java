package labs.jaxrs;

import com.appslandia.common.utils.ExceptionUtils;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import labs.models.Result;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception ex) {

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(new Result().asError().message(ExceptionUtils.buildMessage(ex)))
				.type(MediaType.APPLICATION_JSON).build();
	}
}