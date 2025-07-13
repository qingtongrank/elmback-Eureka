package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.OrderDetailet;

import java.util.List;

public interface OrderDetailetService {

    @Cacheable(value = "detailetByOrder", key = "#orderId", unless = "#result == null or #result.empty")
    List<OrderDetailet> listDetailetByOrder(String orderId);

    @CacheEvict(value = "detailetByOrder", allEntries = true)
    OrderDetailet saveDetailet(OrderDetailet detailet);

    @CacheEvict(value = "detailetByOrder", allEntries = true)
    void removeDetailet(String odId);
}