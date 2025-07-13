package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Cart;
import ynu.elm.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/CartController")
public class CartController {
    /**
     * 用于操作购物车信息的服务层对象。
     */
    @Resource
    private CartService cartService;

    /**
     * 断路器工厂，用于创建断路器实例。
     */
    @Resource
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 根据用户ID获取购物车列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取用户的购物车列表，如果服务不可用，则返回503状态码。
     *
     * @param userId 用户ID
     * @return 购物车列表响应实体
     */
    @GetMapping("/listCartByUser")
    public ResponseEntity<List<Cart>> listCartByUser(@RequestParam String userId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cartCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Cart> list = cartService.listCartByUser(userId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据用户ID和商家ID获取购物车列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取用户的购物车列表（仅包含指定商家的商品），如果服务不可用，则返回503状态码。
     *
     * @param userId      用户ID
     * @param businessId  商家ID
     * @return 购物车列表响应实体
     */
    @GetMapping("/listCartByUserAndBusiness")
    public ResponseEntity<List<Cart>> listCartByUserAndBusiness(@RequestParam String userId,
                                                                @RequestParam String businessId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cartCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Cart> list = cartService.listCartByUserAndBusiness(userId, businessId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存购物车项。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存购物车项，默认数量为1。如果服务不可用，则返回503状态码。
     *
     * @param cart 购物车项
     * @return 保存后的购物车项响应实体
     */
    @PostMapping("/saveCart")
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cartCircuitBreaker");
        return circuitBreaker.run(() -> {
            cart.setQuantity(1); // 默认数量为1
            Cart saved = cartService.saveCart(cart);
            return ResponseEntity.ok(saved);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 更新购物车项。
     * <p>
     * 该方法通过断路器模式调用服务层方法更新购物车项，如果服务不可用，则返回503状态码。
     *
     * @param cart 购物车项
     * @return 更新后的购物车项响应实体
     */
    @PostMapping("/updateCart")
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cartCircuitBreaker");
        return circuitBreaker.run(() -> {
            Cart updated = cartService.updateCart(cart);
            return ResponseEntity.ok(updated);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 从购物车中移除指定商家的商品。
     * <p>
     * 该方法通过断路器模式调用服务层方法移除用户购物车中指定商家的所有商品，返回受影响的行数。
     * 如果服务不可用，则返回503状态码。
     *
     * @param userId      用户ID
     * @param businessId  商家ID
     * @return 受影响的行数响应实体
     */
    @PostMapping("/removeCart")
    public ResponseEntity<Integer> removeCart(@RequestParam String userId, @RequestParam String businessId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cartCircuitBreaker");
        return circuitBreaker.run(() -> {
            Integer row = cartService.removeCart(userId, businessId);
            return ResponseEntity.ok(row);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 清空指定用户的购物车。
     * <p>
     * 该方法通过断路器模式调用服务层方法清空指定用户的购物车，如果服务不可用，则返回503状态码。
     *
     * @param userId 用户ID
     * @return 无内容响应实体
     */
    @DeleteMapping("/clearCartByUser")
    public ResponseEntity<Void> clearCartByUser(@RequestParam String userId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("cartCircuitBreaker");
        return circuitBreaker.run(() -> {
            cartService.clearCartByUser(userId);
            return ResponseEntity.noContent().build();
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }
}