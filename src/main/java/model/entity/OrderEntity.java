package model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity extends PanacheEntity {
    public String orderId;
    public String product;
    public int quantity;
    public double price;
    public double totalPrice;

    public void setTotalPrice(double v) {
        totalPrice = v;
    }
}