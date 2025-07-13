package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import ynu.elm.entity.Business;
import ynu.elm.entity.BusinessLoginResp;
import ynu.elm.service.BusinessService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/BusinessController")
public class BusinessController {
    /**
     * 用于操作商家信息的服务层对象。
     */
    @Resource
    private BusinessService businessService;

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
     * 根据商家ID和密码获取商家信息并生成JWT令牌。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取商家信息，如果商家存在，则生成JWT令牌并返回。
     * 如果商家不存在，则返回404状态码。如果服务不可用，则返回503状态码。
     *
     * @param businessId 商家ID
     * @param password   商家密码
     * @return 包含商家信息和JWT令牌的响应实体
     */
    @GetMapping("/getBusinessByIdByPass")
    public ResponseEntity<BusinessLoginResp> getBusinessByIdByPass(@RequestParam String businessId,
                                                                   @RequestParam String password) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("businessCircuitBreaker");
        return circuitBreaker.run(() -> {
            Optional<Business> businessOptional = businessService.getBusinessByIdByPass(businessId, password);
            if (businessOptional.isPresent()) {
                String token = Jwts.builder()
                        .setSubject(businessOptional.get().getBusinessId())
                        .claim("role", "business")
                        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                        .compact();

                BusinessLoginResp resp = new BusinessLoginResp();
                resp.setBusiness(businessOptional.get());
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
     * 根据商家ID获取商家信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取商家信息，如果商家存在，则返回商家信息。
     * 如果商家不存在，则返回404状态码。如果服务不可用，则返回503状态码。
     *
     * @param businessId 商家ID
     * @return 商家信息响应实体
     */
    @GetMapping("/getBusinessExistsById")
    public ResponseEntity<Business> getBusinessExistsById(@RequestParam String businessId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("businessCircuitBreaker");
        return circuitBreaker.run(() -> {
            Optional<Business> business = businessService.getBusinessById(businessId);
            if (business.isPresent()) {
                return ResponseEntity.ok(business.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据订单类型ID获取商家列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取商家列表，如果服务不可用，则返回503状态码。
     *
     * @param orderTypeId 订单类型ID
     * @return 商家列表响应实体
     */
    @PostMapping("/listBusinessByOrderTypeId")
    public ResponseEntity<List<Business>> listBusinessByOrderTypeId(@RequestParam Integer orderTypeId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("businessCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Business> list = businessService.listBusinessByOrderTypeId(orderTypeId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 获取所有商家信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取所有商家信息，如果服务不可用，则返回503状态码。
     *
     * @return 所有商家信息响应实体
     */
    @GetMapping("/getAllBusinesses")
    public ResponseEntity<List<Business>> getAllBusinesses() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("businessCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Business> list = businessService.getAllBusinesses();
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存商家信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存商家信息，如果保存成功，则返回保存后的商家信息。
     * 如果商家ID已存在，则返回409状态码。如果服务不可用，则返回503状态码。
     *
     * @param business 商家信息
     * @return 保存后的商家信息响应实体
     */
    @PostMapping("/saveBusiness")
    public ResponseEntity<Business> saveBusiness(@RequestBody Business business) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("businessCircuitBreaker");
        return circuitBreaker.run(() -> {
            try {
                Business saved = businessService.saveBusiness(business);
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