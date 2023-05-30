package labs.beans;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ReactiveMessagingConsumer {
    
    @Incoming("messaging-channel-1")
    public void printMessage(String msg) {
        System.out.println("Received message: " + msg + ", in-thread: " + Thread.currentThread().getName());
    }
}
