package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUserId(String userId);

    List<Order> findByOrderId(String orderId);

    List<Order> findByBusinessId(String businessId);

    List<Order> findByDeliveryDriverId(String deliveryDriverId);

    List<Order> findByOrderState(Integer orderState);

    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}