package kafka.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.entity.OrderEntity;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaProducerPayment {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerPayment.class);


//    @Outgoing("my-channel-out")
//    public Multi<String> produce() {
//        return Multi.createFrom().items("test send message");
//    }


    // order-out adalah channel untuk menngabil properties  dengan metode Reactive Messaging
    // dengan topik order-output berbeda topiknya dengan my-chanel-in
    @Inject
    @Channel("order-out")
    Emitter<String> myChannelEmitter;
    public boolean sendToPayment(OrderEntity order) {
        ObjectMapper mapper = new ObjectMapper();
        String pesanan = null;
        try {
            pesanan = mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
             log.error(e.getMessage());
             return false;
        }
        myChannelEmitter.send(pesanan);
        log.info("Sent data (order-output) {}", pesanan);
        return true;
    }

}
