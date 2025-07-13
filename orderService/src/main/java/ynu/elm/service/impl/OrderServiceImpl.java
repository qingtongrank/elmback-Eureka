package ynu.elm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ynu.elm.entity.Order;
import ynu.elm.repository.OrderRepository;
import ynu.elm.service.OrderService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> listOrderByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> listOrderByOrderId(String orderId) {return orderRepository.findByOrderId(orderId);}

    @Override
    public List<Order> listOrderByBusiness(String businessId) {
        return orderRepository.findByBusinessId(businessId);
    }

    @Override
    public List<Order> listOrderByDriver(String driverId) {
        return orderRepository.findByDeliveryDriverId(driverId);
    }

    @Override
    public List<Order> listOrderByState(Integer orderState) {
        return orderRepository.findByOrderState(orderState);
    }

    @Override
    public List<Order> listOrderByDateBetween(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByOrderDateBetween(start, end);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }
}