package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "admin_id", nullable = false)
    private String adminId;

    @Column(name = "admin_password", nullable = false)
    private String adminPassword;

    @Column(name = "admin_name", nullable = false)
    private String adminName;

    @Column(name = "user_img")
    private String userImg;

    @Column(name = "del_tag", columnDefinition = "int default 1")
    private Integer delTag = 1;
}