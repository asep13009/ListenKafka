package kafka.consumer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.OrderService;


@ApplicationScoped
public class KafkaConsumerOrder {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerOrder.class);
    @Inject
    private OrderService orderService;

    // order-in adalah channel untuk menngabil properties  dengan metode Reactive Messaging dengan topik order-input
    @Incoming("order-in")
    public boolean consumeOrder(String order) {
        log.info("ORDER telah masuk || (order-input) ||>  " + order);

        // datanya akan di manipulasi ke orderService
        return  orderService.processOrder(order);
    }
}