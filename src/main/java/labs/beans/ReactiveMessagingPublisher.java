package labs.beans;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ReactiveMessagingPublisher {

    @Inject
    @Channel("messaging-channel-1")
    private Emitter<String> emitter;

    public void sendMessage(String payload) {
        emitter.send(payload);
    }
}
