package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import kafka.producer.KafkaProducerPayment;
import model.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);


    @Inject
    KafkaProducerPayment producer;

    // misal kita punya stok hanya 100
    private final int stock =100;


    public  boolean processOrder(String message)  {

        // recurement :  dan menulisnya kembali ke Kafka atau menyimpannya ke database.

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderEntity order = objectMapper.readValue(message, OrderEntity.class);
            order.setTotalPrice(order.getQuantity() * order.getPrice());

            if(order.getQuantity() >= stock) {
                log.info("STOK anda hanya "+ stock);
                return false;
            }else {
                log.info("=================send kafka payment==========="+ stock);
                return producer.sendToPayment(order);
            }
        } catch (Exception e) {
//            e.printStackTrace();
           log.error("PESANAN ANDA Tidak BERBENTUK ORDER ENTITY, >>  message anda || "+message);

           log.info("=================info========================");

           log.info("{\"product\":\"Laptop\",\"quantity\":2,\"price\":52.0,\"paymentMethod\":\"COD\"}");

                //{"product":"Laptop","quantity":2,"price":52.0,"paymentMethod":"COD"}

            return false;

        }

    }

}
