package ynu.elm.entity;

import lombok.Data;
import ynu.elm.entity.Business;

@Data
public class BusinessLoginResp {
    private Business business;
    private String token;
}