package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Order;
import ynu.elm.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/OrderController")
public class OrderController {
    /**
     * 用于操作订单信息的服务层对象。
     */
    @Resource
    private OrderService orderService;

    /**
     * 断路器工厂，用于创建断路器实例。
     */
    @Resource
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 根据用户ID获取订单列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定用户的订单列表，如果服务不可用，则返回503状态码。
     *
     * @param userId 用户ID
     * @return 订单列表响应实体
     */
    @GetMapping("/listOrderByUser")
    public ResponseEntity<List<Order>> listOrderByUser(@RequestParam String userId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");

        return circuitBreaker.run(() -> {
            List<Order> list = orderService.listOrderByUser(userId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据订单ID获取订单列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定订单ID的订单列表，如果服务不可用，则返回503状态码。
     *
     * @param orderId 订单ID
     * @return 订单列表响应实体
     */
    @GetMapping("/listOrderByOrderId")
    public ResponseEntity<List<Order>> listOrderByOrderId(@RequestParam String orderId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Order> list = orderService.listOrderByOrderId(orderId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据商家ID获取订单列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定商家的订单列表，如果服务不可用，则返回503状态码。
     *
     * @param businessId 商家ID
     * @return 订单列表响应实体
     */
    @GetMapping("/listOrderByBusiness")
    public ResponseEntity<List<Order>> listOrderByBusiness(@RequestParam String businessId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Order> list = orderService.listOrderByBusiness(businessId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据配送员ID获取订单列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定配送员的订单列表，如果服务不可用，则返回503状态码。
     *
     * @param driverId 配送员ID
     * @return 订单列表响应实体
     */
    @GetMapping("/listOrderByDriver")
    public ResponseEntity<List<Order>> listOrderByDriver(@RequestParam String driverId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Order> list = orderService.listOrderByDriver(driverId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据订单状态获取订单列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定状态的订单列表，如果服务不可用，则返回503状态码。
     *
     * @param orderState 订单状态
     * @return 订单列表响应实体
     */
    @GetMapping("/listOrderByState")
    public ResponseEntity<List<Order>> listOrderByState(@RequestParam Integer orderState) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Order> list = orderService.listOrderByState(orderState);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据时间范围获取订单列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定时间范围内的订单列表，如果服务不可用，则返回503状态码。
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 订单列表响应实体
     */
    @GetMapping("/listOrderByDateBetween")
    public ResponseEntity<List<Order>> listOrderByDateBetween(@RequestParam LocalDateTime start,
                                                              @RequestParam LocalDateTime end) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Order> list = orderService.listOrderByDateBetween(start, end);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存订单信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存订单信息，如果保存成功，则返回保存后的订单信息。
     * 如果服务不可用，则返回503状态码。
     *
     * @param order 订单信息
     * @return 保存后的订单信息响应实体
     */
    @PostMapping("/saveOrder")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");
        return circuitBreaker.run(() -> {
            Order saved = orderService.saveOrder(order);
            return ResponseEntity.ok(saved);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 删除订单信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法删除指定ID的订单信息，如果删除成功，则返回204状态码。
     * 如果服务不可用，则返回503状态码。
     *
     * @param orderId 订单ID
     * @return 无内容响应实体
     */
    @DeleteMapping("/removeOrder")
    public ResponseEntity<Void> removeOrder(@RequestParam String orderId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderCircuitBreaker");
        return circuitBreaker.run(() -> {
            orderService.removeOrder(orderId);
            return ResponseEntity.noContent().build();
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }
}