package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.Business;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {

    Optional<Business> findByBusinessIdAndBusinessPasswordAndDelTag(String businessId,
                                                                    String businessPassword,
                                                                    Integer delTag);

    boolean existsByBusinessIdAndDelTag(String businessId, Integer delTag);

    Optional<Business> findByBusinessIdAndDelTag(String businessId, Integer delTag);

    List<Business> findByOrderTypeIdAndDelTag(Integer orderTypeId, Integer delTag);

    @Modifying
    @Query("UPDATE Business b SET b.delTag = ?1 WHERE b.businessId = ?2")
    void updateDelTag(Integer delTag, String businessId);
}