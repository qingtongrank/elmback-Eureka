package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.Cart;

import java.util.List;

public interface CartService {

    @Cacheable(value = "cartByUser", key = "#userId", unless = "#result == null or #result.empty")
    List<Cart> listCartByUser(String userId);

    @Cacheable(value = "cartByUserBusiness", key = "{#userId, #businessId}", unless = "#result == null or #result.empty")
    List<Cart> listCartByUserAndBusiness(String userId, String businessId);

    @CacheEvict(value = {"cartByUser", "cartByUserBusiness"}, allEntries = true)
    Cart saveCart(Cart cart);

    @CacheEvict(value = {"cartByUser", "cartByUserBusiness"}, allEntries = true)
    Cart updateCart(Cart cart);

    @CacheEvict(value = {"cartByUser", "cartByUserBusiness"}, allEntries = true)
    Integer removeCart(String userId, String businessId);

    @CacheEvict(value = {"cartByUser", "cartByUserBusiness"}, allEntries = true)
    void clearCartByUser(String userId);
}