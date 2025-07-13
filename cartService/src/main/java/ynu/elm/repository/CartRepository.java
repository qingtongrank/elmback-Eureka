package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.Cart;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

    List<Cart> findByUserId(String userId);
    List<Cart> findByUserIdAndBusinessId(String userId, String businessId);
    List<Cart> findByUserIdAndBusinessIdAndFoodId(String userId, String businessId, String foodId);
    void deleteByUserIdAndBusinessId(String userId, String businessId);

    int deleteByUserId(String userId);
}