package ynu.elm.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Cart;
import ynu.elm.repository.CartRepository;
import ynu.elm.service.CartService;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> listCartByUser(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public List<Cart> listCartByUserAndBusiness(String userId, String businessId) {
        return cartRepository.findByUserIdAndBusinessId(userId, businessId);
    }

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Cart cart) {
        List<Cart> existing = cartRepository.findByUserIdAndBusinessIdAndFoodId(
                cart.getUserId(), cart.getBusinessId(), cart.getFoodId());
        if (!existing.isEmpty()) {
            Cart existingCart = existing.get(0);
            existingCart.setQuantity(cart.getQuantity());
            return cartRepository.save(existingCart);
        }
        return null;
    }

    @Override
    @Transactional
    public Integer removeCart(String userId, String businessId) {
        List<Cart> carts = cartRepository.findByUserIdAndBusinessId(userId, businessId);
        if (!carts.isEmpty()) {
            cartRepository.deleteByUserIdAndBusinessId(userId, businessId);
            return 1;
        }
        return 0;
    }

    @Override
    public void clearCartByUser(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}