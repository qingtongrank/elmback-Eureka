package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.OrderDetailet;

import java.util.List;

@Repository
public interface OrderDetailetRepository extends JpaRepository<OrderDetailet, String> {
    List<OrderDetailet> findByOrderId(String orderId);
}