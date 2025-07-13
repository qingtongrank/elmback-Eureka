package ynu.elm.entity;

import lombok.Data;
import ynu.elm.entity.DeliveryDriver;

@Data
public class DriverLoginResp {
    private DeliveryDriver driver;
    private String token;
}