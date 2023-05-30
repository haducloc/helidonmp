package labs.apps.scheduling;

import java.util.concurrent.TimeUnit;

import io.helidon.microprofile.scheduling.FixedRate;
import io.helidon.microprofile.scheduling.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Scheduler {

    @FixedRate(initialDelay = 0, value = 5, timeUnit = TimeUnit.SECONDS)
    public void scheduling1() {
        System.out.println("Every 5 seconds using @FixedRate: Thread=" + Thread.currentThread().getName());
    }

    @Scheduled("0/10 * * * * ?")
    public void scheduling2() {
        System.out.println("Every 10 seconds using @Scheduled: Thread=" + Thread.currentThread().getName());
    }
}
