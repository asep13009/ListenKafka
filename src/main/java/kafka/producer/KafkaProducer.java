package kafka.producer;


import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);


//    @Outgoing("my-channel-out")
//    public Multi<String> produce() {
//        return Multi.createFrom().items("test send message");
//    }


    // my-channel-out adalah channel untuk menngabil properties  dengan metode Reactive Messaging
    // dengan topik order-output berbeda topiknya dengan my-chanel-in
    @Inject
    @Channel("my-channel-out")
    Emitter<String> myChannelEmitter;
    public void sendMessage(String messagePayload) {
        log.info("Sending data (order-output) {}", messagePayload);
        myChannelEmitter.send(messagePayload);
    }

}
