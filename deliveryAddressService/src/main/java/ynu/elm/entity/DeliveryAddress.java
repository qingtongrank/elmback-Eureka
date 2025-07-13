package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "delivery_address")
public class DeliveryAddress {

    @Id
    @Column(name = "da_id", nullable = false, length = 36)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String daId;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "contact_sex", nullable = false)
    private Integer contactSex;

    @Column(name = "contact_tel", nullable = false)
    private String contactTel;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "user_id", nullable = false)
    private String userId;
}