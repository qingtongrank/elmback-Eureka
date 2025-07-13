package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Food;
import ynu.elm.repository.FoodRepository;
import ynu.elm.service.FoodService;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> listFoodByBusiness(String businessId) {
        return foodRepository.findByBusinessId(businessId);
    }

    @Override
    public Optional<Food> getFoodById(String foodId) {
        return foodRepository.findByFoodId(foodId);
    }

    @Override
    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public void removeFood(String foodId) {
        foodRepository.deleteById(foodId);
    }
}