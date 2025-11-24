package kafka.consumer;
import io.smallrye.common.annotation.NonBlocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.PaymentService;


@ApplicationScoped
public class KafkaConsumerPayment {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerPayment.class);
    @Inject
    private PaymentService paymentService;

    // payment out adalah topik untuk proses payment
    @Incoming("payment")
    public boolean consumePayment(String payment) {
        log.info("Payment telah masuk || (order-out/payment) ||>  " + payment);
        return  paymentService.processPayment(payment);
    }
}