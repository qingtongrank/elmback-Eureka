package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "cart_id", nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String cartId;

    @Column(name = "food_id", nullable = false)
    private String foodId;

    @Column(name = "business_id", nullable = false)
    private String businessId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}