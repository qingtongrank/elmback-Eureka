package ynu.elm.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.DeliveryDriver;
import ynu.elm.entity.DriverLoginResp;
import ynu.elm.service.DeliveryDriverService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DeliveryDriverController")
public class DeliveryDriverController {
    /**
     * 用于操作配送员信息的服务层对象。
     */
    @Resource
    private DeliveryDriverService deliveryDriverService;

    /**
     * JWT签名密钥，用于生成和验证JWT令牌。
     */
    private final String secret = "wang-yong-shuo-w-y-s-12345678901";

    /**
     * 断路器工厂，用于创建断路器实例。
     */
    @Resource
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 根据配送员ID和密码获取配送员信息并生成JWT令牌。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取配送员信息，如果配送员存在，则生成JWT令牌并返回。
     * 如果配送员不存在，则返回404状态码。如果服务不可用，则返回503状态码。
     *
     * @param deliveryDriverId 配送员ID
     * @param password         配送员密码
     * @return 包含配送员信息和JWT令牌的响应实体
     */
    @GetMapping("/getDriverByIdByPass")
    public ResponseEntity<DriverLoginResp> getDriverByIdByPass(@RequestParam String deliveryDriverId,
                                                               @RequestParam String password) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryDriverCircuitBreaker");
        return circuitBreaker.run(() -> {
            Optional<DeliveryDriver> driverOptional = deliveryDriverService.getDriverByIdByPass(deliveryDriverId, password);
            if (driverOptional.isPresent()) {
                String token = Jwts.builder()
                        .setSubject(driverOptional.get().getDeliveryDriverId())
                        .claim("role", "delivery_driver")
                        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                        .compact();

                DriverLoginResp resp = new DriverLoginResp();
                resp.setDriver(driverOptional.get());
                resp.setToken(token);
                return ResponseEntity.ok(resp);
            } else {
                return ResponseEntity.notFound().build();
            }
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据配送员ID判断配送员是否存在。
     * <p>
     * 该方法通过断路器模式调用服务层方法判断配送员是否存在，如果服务不可用，则返回503状态码。
     *
     * @param deliveryDriverId 配送员ID
     * @return 表示配送员是否存在的布尔值响应实体
     */
    @GetMapping("/getDriverExistsById")
    public ResponseEntity<Boolean> getDriverExistsById(@RequestParam String deliveryDriverId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryDriverCircuitBreaker");
        return circuitBreaker.run(() -> {
            boolean exist = deliveryDriverService.isDriverExist(deliveryDriverId);
            return ResponseEntity.ok(exist);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 获取所有配送员信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取所有配送员信息，如果服务不可用，则返回503状态码。
     *
     * @return 所有配送员信息响应实体
     */
    @GetMapping("/getAllDeliveryDriver")
    public ResponseEntity<List<DeliveryDriver>> getAllDeliveryDriver() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryDriverCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<DeliveryDriver> list = deliveryDriverService.getAllDeliveryDriver();
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存配送员信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存配送员信息，如果保存成功，则返回保存后的配送员信息。
     * 如果配送员ID已存在，则返回409状态码。如果服务不可用，则返回503状态码。
     *
     * @param driver 配送员信息
     * @return 保存后的配送员信息响应实体
     */
    @PostMapping("/saveDriver")
    public ResponseEntity<DeliveryDriver> saveDriver(@RequestBody DeliveryDriver driver) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryDriverCircuitBreaker");
        return circuitBreaker.run(() -> {
            try {
                DeliveryDriver saved = deliveryDriverService.saveDriver(driver);
                return ResponseEntity.ok(saved);
            } catch (DataAccessException e) {
                if (e instanceof DuplicateKeyException) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                throw e;
            }
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }
}