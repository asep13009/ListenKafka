package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import kafka.producer.KafkaProducer;
import model.entity.OrderEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);


    @Inject
    KafkaProducer producer;

    @Transactional
    public  void process(String message)  {

        // recurement :  dan menulisnya kembali ke Kafka atau menyimpannya ke database.
        // saya buat jika total price nya >= 100 maka simpan ke DB, jika tidak ke kafka baru
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderEntity order = objectMapper.readValue(message, OrderEntity.class);
            order.setTotalPrice(order.quantity * order.price);
            if (order.totalPrice >= 100) {
                //simpan ke db
                log.info("=============== send to db order ====================");
                order.persist();
            } else {
                //kirim ke kafka orders-output
                log.info("=============== send to kafka (order-output) ==================");
                producer.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
           log.error("message tidak berbentuk JsonString ORDER ENTITY,  message anda: "+message);

           log.info("=================info========================");
           log.info("jika ingin di save oleh db , contoh request:");
           log.info("{\"orderId\":\"123\",\"product\":\"Laptop\",\"quantity\":2,\"price\":50}");
            log.info("=================info========================");
            log.info("jika ingin di save oleh kafka , contoh request:");
            log.info("{\"orderId\":\"123\",\"product\":\"Laptop\",\"quantity\":1,\"price\":5}");


           //jika ingin di save oleh db , contoh request:
            //{"orderId":"123","product":"Laptop","quantity":2,"price":50}
            //jika ingin di save oleh kafka , contoh request:
            //{"orderId":"123","product":"Laptop","quantity":1,"price":5}

        }

    }

}
