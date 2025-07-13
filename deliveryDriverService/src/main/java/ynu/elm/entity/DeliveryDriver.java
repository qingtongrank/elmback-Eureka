package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "delivery_driver")
public class DeliveryDriver {
    @Id
    @Column(name = "delivery_driver_id", nullable = false)
    private String deliveryDriverId;

    @Column(name = "delivery_driver_password", nullable = false)
    private String deliveryDriverPassword;

    @Column(name = "delivery_driver_name", nullable = false)
    private String deliveryDriverName;

    @Column(name = "delivery_driver_img")
    private String deliveryDriverImg;

    @Column(name = "del_tag", columnDefinition = "int default 1")
    private Integer delTag = 1;
}