package labs.restapi;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import labs.beans.ReactiveMessagingPublisher;

@Path("/msg")
@ApplicationScoped
@PermitAll
public class ReactiveMessagingResource {

    @Inject
    private ReactiveMessagingPublisher msgPublisher;

    @GET
    @Path("/send")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public String send() {
        String msg = "Sending this message: " + System.currentTimeMillis() + ", out-thread: " + Thread.currentThread().getName();
        this.msgPublisher.sendMessage(msg);
        return msg;
    }
}
