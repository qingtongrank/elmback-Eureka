package ynu.elm.entity;

import lombok.Data;

@Data
public class UserLoginResp {
    private User user;
    private String token;
}