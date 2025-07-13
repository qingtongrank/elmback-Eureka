package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "business")
public class Business {
    @Id
    @Column(name = "business_id", nullable = false)
    private String businessId;

    @Column(name = "business_password", nullable = false)
    private String businessPassword;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "business_address", nullable = false)
    private String businessAddress;

    @Column(name = "business_explain")
    private String businessExplain;

    @Column(name = "business_img")
    private String businessImg;

    @Column(name = "order_type_id", nullable = false)
    private Integer orderTypeId;

    @Column(name = "star_price", nullable = false)
    private Double starPrice;

    @Column(name = "delivery_price", nullable = false)
    private Double deliveryPrice;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "del_tag", columnDefinition = "int default 1")
    private Integer delTag = 1;
}