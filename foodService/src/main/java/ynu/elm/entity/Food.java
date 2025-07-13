package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "food")
public class Food {

    @Id
    @Column(name = "food_id", nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String foodId;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "food_explain")
    private String foodExplain;

    @Column(name = "food_img")
    private String foodImg;

    @Column(name = "food_price", nullable = false)
    private Double foodPrice;

    @Column(name = "business_id", nullable = false)
    private String businessId;

    @Column(name = "remarks")
    private String remarks;
}