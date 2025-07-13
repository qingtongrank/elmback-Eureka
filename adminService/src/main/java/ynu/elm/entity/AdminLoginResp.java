package ynu.elm.entity;

import lombok.Data;
import ynu.elm.entity.Admin;

@Data
public class AdminLoginResp {
    private Admin admin;
    private String token;
}