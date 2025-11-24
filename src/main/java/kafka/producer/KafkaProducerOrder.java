package kafka.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.entity.OrderEntity;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaProducerOrder {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerOrder.class);


    @Inject
    @Channel("hanya-order")
    Emitter<String> myChannelEmitter;
    public void sendToOrder(String message) {
        myChannelEmitter.send(message);
        log.info("Sent data (order-input) {}", message);

    }

}
