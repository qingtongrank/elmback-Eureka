package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.Food;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, String> {
    List<Food> findByBusinessId(String businessId);
    Optional<Food> findByFoodId(String foodId);
}