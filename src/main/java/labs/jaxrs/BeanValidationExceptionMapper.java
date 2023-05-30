package labs.jaxrs;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import labs.apps.utils.ConstraintViolationUtil;
import labs.models.Result;

@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException ex) {

		return Response.status(Response.Status.BAD_REQUEST)
				.entity(new Result().asError().message(ConstraintViolationUtil.buildMessage(ex)))
				.type(MediaType.APPLICATION_JSON).build();

	}
}
