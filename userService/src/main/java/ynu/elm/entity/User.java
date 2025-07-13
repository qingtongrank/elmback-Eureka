package ynu.elm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_sex", nullable = false)
    private Integer userSex;

    @Column(name = "user_img")
    private String userImg;

    @Column(name = "del_tag", columnDefinition = "int default 1")
    private Integer delTag = 1;
}
