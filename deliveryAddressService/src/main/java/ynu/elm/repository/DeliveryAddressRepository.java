package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.DeliveryAddress;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, String> {
    List<DeliveryAddress> findByUserId(String userId);
    Optional<DeliveryAddress> findByDaId(String daId);
}