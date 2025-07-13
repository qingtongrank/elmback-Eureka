package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.OrderDetailet;
import ynu.elm.service.OrderDetailetService;

import java.util.List;

@RestController
@RequestMapping("/OrderDetailetController")
public class OrderDetailetController {
    /**
     * 用于操作订单详情信息的服务层对象。
     */
    @Resource
    private OrderDetailetService orderDetailetService;

    /**
     * 断路器工厂，用于创建断路器实例。
     */
    @Resource
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 根据订单ID获取订单详情列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定订单的所有详情信息，如果服务不可用，则返回503状态码。
     *
     * @param orderId 订单ID
     * @return 订单详情列表响应实体
     */
    @GetMapping("/listDetailetByOrder")
    public ResponseEntity<List<OrderDetailet>> listDetailetByOrder(@RequestParam String orderId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderDetailetCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<OrderDetailet> list = orderDetailetService.listDetailetByOrder(orderId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存订单详情信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存订单详情信息，如果保存成功，则返回保存后的订单详情信息。
     * 如果服务不可用，则返回503状态码。
     *
     * @param detailet 订单详情信息
     * @return 保存后的订单详情信息响应实体
     */
    @PostMapping("/saveDetailet")
    public ResponseEntity<OrderDetailet> saveDetailet(@RequestBody OrderDetailet detailet) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderDetailetCircuitBreaker");
        return circuitBreaker.run(() -> {
            OrderDetailet saved = orderDetailetService.saveDetailet(detailet);
            return ResponseEntity.ok(saved);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 删除订单详情信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法删除指定ID的订单详情信息，如果删除成功，则返回204状态码。
     * 如果服务不可用，则返回503状态码。
     *
     * @param odId 订单详情ID
     * @return 无内容响应实体
     */
    @DeleteMapping("/removeDetailet")
    public ResponseEntity<Void> removeDetailet(@RequestParam String odId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderDetailetCircuitBreaker");
        return circuitBreaker.run(() -> {
            orderDetailetService.removeDetailet(odId);
            return ResponseEntity.noContent().build();
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }
}