package ynu.elm.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import ynu.elm.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    @Cacheable(value = "ordersByUser", key = "#userId", unless = "#result == null or #result.empty")
    List<Order> listOrderByUser(String userId);

    @Cacheable(value = "ordersByOrderId", key = "#orderId", unless = "#result == null or #result.empty")
    List<Order> listOrderByOrderId(String orderId);

    @Cacheable(value = "ordersByBusiness", key = "#businessId", unless = "#result == null or #result.empty")
    List<Order> listOrderByBusiness(String businessId);

    @Cacheable(value = "ordersByDriver", key = "#driverId", unless = "#result == null or #result.empty")
    List<Order> listOrderByDriver(String driverId);

    @Cacheable(value = "ordersByState", key = "#orderState", unless = "#result == null or #result.empty")
    List<Order> listOrderByState(Integer orderState);

    @Cacheable(
            value = "ordersByDate",
            key = "{#start.toString(), #end.toString()}",
            unless = "#result == null or #result.empty"
    )
    List<Order> listOrderByDateBetween(LocalDateTime start, LocalDateTime end);

    @CacheEvict(value = {
            "ordersByUser",
            "ordersByOrderId",
            "ordersByBusiness",
            "ordersByDriver",
            "ordersByState",
            "ordersByDate"
    }, allEntries = true)
    Order saveOrder(Order order);

    @CacheEvict(value = {
            "ordersByUser",
            "ordersByOrderId",
            "ordersByBusiness",
            "ordersByDriver",
            "ordersByState",
            "ordersByDate"
    }, allEntries = true)
    void removeOrder(String orderId);
}