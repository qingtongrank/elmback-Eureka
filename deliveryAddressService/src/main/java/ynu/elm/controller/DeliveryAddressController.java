package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.DeliveryAddress;
import ynu.elm.service.DeliveryAddressService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DeliveryAddressController")
public class DeliveryAddressController {
    /**
     * 用于操作配送地址信息的服务层对象。
     */
    @Resource
    private DeliveryAddressService deliveryAddressService;

    /**
     * 断路器工厂，用于创建断路器实例。
     */
    @Resource
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 根据用户ID获取配送地址列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取用户的配送地址列表，如果服务不可用，则返回503状态码。
     *
     * @param userId 用户ID
     * @return 配送地址列表响应实体
     */
    @GetMapping("/listAddressByUser")
    public ResponseEntity<List<DeliveryAddress>> listAddressByUser(@RequestParam String userId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryAddressCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<DeliveryAddress> list = deliveryAddressService.listAddressByUser(userId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据配送地址ID获取配送地址信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定ID的配送地址信息，如果地址存在，则返回该地址信息。
     * 如果地址不存在，则返回404状态码。如果服务不可用，则返回503状态码。
     *
     * @param daId 配送地址ID
     * @return 配送地址信息响应实体
     */
    @GetMapping("/getAddressBydaId")
    public ResponseEntity<DeliveryAddress> getAddressBydaId(@RequestParam String daId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryAddressCircuitBreaker");
        return circuitBreaker.run(() -> {
            Optional<DeliveryAddress> deliveryAddress = deliveryAddressService.getAdderssBydaId(daId);
            if (deliveryAddress.isPresent()) {
                return ResponseEntity.ok(deliveryAddress.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存配送地址信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存配送地址信息，如果保存成功，则返回保存后的地址信息。
     * 如果服务不可用，则返回503状态码。
     *
     * @param address 配送地址信息
     * @return 保存后的配送地址信息响应实体
     */
    @PostMapping("/saveAddress")
    public ResponseEntity<DeliveryAddress> saveAddress(@RequestBody DeliveryAddress address) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryAddressCircuitBreaker");
        return circuitBreaker.run(() -> {
            DeliveryAddress saved = deliveryAddressService.saveAddress(address);
            return ResponseEntity.ok(saved);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 删除配送地址信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法删除指定ID的配送地址信息，如果删除成功，则返回204状态码。
     * 如果服务不可用，则返回503状态码。
     *
     * @param daId 配送地址ID
     * @return 无内容响应实体
     */
    @DeleteMapping("/removeAddress")
    public ResponseEntity<Void> removeAddress(@RequestParam String daId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("deliveryAddressCircuitBreaker");
        return circuitBreaker.run(() -> {
            deliveryAddressService.removeAddress(daId);
            return ResponseEntity.noContent().build();
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }
}