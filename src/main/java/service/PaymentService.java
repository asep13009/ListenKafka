package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import model.entity.OrderEntity;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ApplicationScoped
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    @Inject
    @RestClient
    PaymentsGateway paymentsGateway;
//    @Incoming("payment")
    @Transactional
    public  boolean processPayment(String payment)  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderEntity order = objectMapper.readValue(payment, OrderEntity.class);
            order.setTotalPrice(order.getQuantity() * order.getPrice());

            //jika bukan cod
            if(!order.getPaymentMethod().equalsIgnoreCase("COD")){
                // misal punya DTO CARD
//                Response response = paymentsGateway.makePayment(order.getCARD());
                Response response = paymentsGateway.makePayment(order);
                log.info("=============== DUMMY PAYMENT GATEWAY  ==================");
                log.info("=============== https://postman-echo.com  ==================");
                // add logic harusnya
                if(response.getStatus() == Response.Status.OK.getStatusCode()){
                    log.info("=============== save to db  ==================");
                    order.persist();
                    return true;
                } else {
                    log.info("=============== GAGAL PEMBAYARAN  ==================");
                    return false;
                }
            } else{
                log.info("=============== save to db  ==================");
                order.persist();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("PESANAN ANDA Tidak BERBENTUK ORDER ENTITY, >>  message anda || "+payment);

            log.info("=================info========================");

            log.info("{\"orderId\":\"123\",\"product\":\"Laptop\",\"quantity\":2,\"price\":50}");

                    //{"product":"Laptop","quantity":1,"price":5,"paymentMethod":"COD"}

            return false;

        }
    }
}
