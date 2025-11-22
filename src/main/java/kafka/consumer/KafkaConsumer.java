package kafka.consumer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.OrderService;


@ApplicationScoped
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    @Inject
    private OrderService orderService;

    // my-channel-in adalah channel untuk menngabil properties  dengan metode Reactive Messaging dengan topik order-input
    @Incoming("my-channel-in")
    public void consume(String order) {
        log.info("Pesan diterima (order-input): " + order);

        // datanya akan di manipulasi ke orderService
        orderService.process(order);
    }
}