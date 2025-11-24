package service;

import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.entity.OrderEntity;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient
public interface PaymentsGateway {

    final double uangAnda=1000000;
//    DUMMY payment gatway ya
    @POST
    @Path("/post")
    public default Response makePayment(OrderEntity payment){
        if(payment.getTotalPrice()>uangAnda){
            return Response.status(400).type(MediaType.TEXT_PLAIN).build();
        }
        return Response.ok().build();
    }
}
