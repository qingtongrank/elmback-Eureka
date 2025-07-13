package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.Admin;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findByAdminIdAndAdminPasswordAndDelTag(String adminId,
                                                           String adminPassword,
                                                           Integer delTag);

    boolean existsByAdminIdAndDelTag(String adminId, Integer delTag);

    @Modifying
    @Query("UPDATE Admin a SET a.delTag = ?1 WHERE a.adminId = ?2")
    void updateDelTag(Integer delTag, String adminId);
}