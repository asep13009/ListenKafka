package kafka.producer;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class KafkaProducerOrderTest {
@Inject
    KafkaProducerOrder producer;

    @Test
    public void testSendToOrder_SUKSES_CARD() {
        Emitter<String> mockEmitter = Mockito.mock(Emitter.class);
             String testMessage = "{\"product\":\"Laptop KARTU\",\"quantity\":2,\"price\":52.0,\"paymentMethod\":\"CARD\"}";
        producer.myChannelEmitter = mockEmitter;
        producer.sendToOrder(testMessage);
    }

    @Test
    public void testSendToOrder_SUKSES_COD() {
        Emitter<String> mockEmitter = Mockito.mock(Emitter.class);
        String testMessage = "{\"product\":\"Laptop THINKPAD COD\",\"quantity\":2,\"price\":52.0,\"paymentMethod\":\"COD\"}";
        producer.myChannelEmitter = mockEmitter;
        producer.sendToOrder(testMessage);
    }

    @Test
    public void testSendToOrder_GAGAL_CARD_MELEBIHI_STOK() {
        //TIDAK MASUK ORDER (tidal ke KAFKA ORDE-OUT) > tidak ke metode payment
        Emitter<String> mockEmitter = Mockito.mock(Emitter.class);
        String testMessage = "{\"product\":\"Laptop THINKPAD COD\",\"quantity\":101,\"price\":52.0,\"paymentMethod\":\"COD\"}";
        producer.myChannelEmitter = mockEmitter;
        producer.sendToOrder(testMessage);
    }

    @Test
    public void testSendToOrder_GAGAL_CARD_UANG_TIDAK_CUKUP() {
        //TIDAK MASUK KE DB KARENA GAGAL PEMBAYARAN
        Emitter<String> mockEmitter = Mockito.mock(Emitter.class);
        String testMessage = "{\"product\":\"Laptop THINKPAD CARD\",\"quantity\":3,\"price\":500000.0,\"paymentMethod\":\"CARD\"}";
        producer.myChannelEmitter = mockEmitter;
        producer.sendToOrder(testMessage);
    }


}


