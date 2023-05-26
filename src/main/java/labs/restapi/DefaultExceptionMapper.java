package labs.restapi;

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

		var result = new Result ();
		result.setCode(1);
		result.setMessage(ExceptionUtils.buildMessage(ex));

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
