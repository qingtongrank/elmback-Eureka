package ynu.elm.controller;

import jakarta.annotation.Resource;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ynu.elm.entity.Food;
import ynu.elm.service.FoodService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/FoodController")
public class FoodController {
    /**
     * 用于操作菜品信息的服务层对象。
     */
    @Resource
    private FoodService foodService;

    /**
     * 断路器工厂，用于创建断路器实例。
     */
    @Resource
    private CircuitBreakerFactory circuitBreakerFactory;

    /**
     * 根据商家ID获取菜品列表。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定商家的所有菜品信息，如果服务不可用，则返回503状态码。
     *
     * @param businessId 商家ID
     * @return 菜品列表响应实体
     */
    @GetMapping("/listFoodByBusiness")
    public ResponseEntity<List<Food>> listFoodByBusiness(@RequestParam String businessId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("foodCircuitBreaker");
        return circuitBreaker.run(() -> {
            List<Food> list = foodService.listFoodByBusiness(businessId);
            return ResponseEntity.ok(list);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 根据菜品ID获取菜品信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法获取指定ID的菜品信息，如果菜品存在，则返回该菜品信息。
     * 如果菜品不存在，则返回404状态码。如果服务不可用，则返回503状态码。
     *
     * @param foodId 菜品ID
     * @return 菜品信息响应实体
     */
    @GetMapping("/getFoodById")
    public ResponseEntity<Food> getFoodById(@RequestParam String foodId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("foodCircuitBreaker");
        return circuitBreaker.run(() -> {
            Optional<Food> food = foodService.getFoodById(foodId);
            return food.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 保存菜品信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法保存菜品信息，如果保存成功，则返回保存后的菜品信息。
     * 如果服务不可用，则返回503状态码。
     *
     * @param food 菜品信息
     * @return 保存后的菜品信息响应实体
     */
    @PostMapping("/saveFood")
    public ResponseEntity<Food> saveFood(@RequestBody Food food) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("foodCircuitBreaker");
        return circuitBreaker.run(() -> {
            Food saved = foodService.saveFood(food);
            return ResponseEntity.ok(saved);
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }

    /**
     * 删除菜品信息。
     * <p>
     * 该方法通过断路器模式调用服务层方法删除指定ID的菜品信息，如果删除成功，则返回204状态码。
     * 如果服务不可用，则返回503状态码。
     *
     * @param foodId 菜品ID
     * @return 无内容响应实体
     */
    @DeleteMapping("/removeFood")
    public ResponseEntity<Void> removeFood(@RequestParam String foodId) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("foodCircuitBreaker");
        return circuitBreaker.run(() -> {
            foodService.removeFood(foodId);
            return ResponseEntity.noContent().build();
        }, throwable -> {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        });
    }
}