package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.Food;

import java.util.List;
import java.util.Optional;

public interface FoodService {

    @Cacheable(value = "foodByBusiness", key = "#businessId", unless = "#result == null or #result.empty")
    List<Food> listFoodByBusiness(String businessId);

    @Cacheable(value = "foodById", key = "#foodId", unless = "#result == null")
    Optional<Food> getFoodById(String foodId);

    @CacheEvict(value = {"foodByBusiness", "foodById"}, allEntries = true)
    Food saveFood(Food food);

    @CacheEvict(value = {"foodByBusiness", "foodById"}, allEntries = true)
    void removeFood(String foodId);
}