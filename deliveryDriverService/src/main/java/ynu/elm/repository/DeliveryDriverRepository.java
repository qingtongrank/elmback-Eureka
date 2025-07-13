package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.DeliveryDriver;

import java.util.Optional;

@Repository
public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriver, String> {

    Optional<DeliveryDriver> findByDeliveryDriverIdAndDeliveryDriverPasswordAndDelTag(String deliveryDriverId,
                                                                                      String deliveryDriverPassword,
                                                                                      Integer delTag);

    boolean existsByDeliveryDriverIdAndDelTag(String deliveryDriverId, Integer delTag);

    @Modifying
    @Query("UPDATE DeliveryDriver d SET d.delTag = ?1 WHERE d.deliveryDriverId = ?2")
    void updateDelTag(Integer delTag, String deliveryDriverId);
}