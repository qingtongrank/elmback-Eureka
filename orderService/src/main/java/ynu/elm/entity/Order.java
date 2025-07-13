package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id", nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String orderId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "business_id", nullable = false)
    private String businessId;

    @Column(name = "delivery_driver_id")
    private String deliveryDriverId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "order_total", nullable = false)
    private Double orderTotal;

    @Column(name = "da_id", nullable = false)
    private String daId;

    @Column(name = "order_state", nullable = false)
    private Integer orderState;
}