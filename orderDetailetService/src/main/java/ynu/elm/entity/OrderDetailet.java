package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "order_detailet")
public class OrderDetailet {

    @Id
    @Column(name = "od_id", nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String odId;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "food_id", nullable = false)
    private String foodId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}