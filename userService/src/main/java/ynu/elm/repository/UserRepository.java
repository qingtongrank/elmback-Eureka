package ynu.elm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ynu.elm.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserIdAndPasswordAndDelTag(String userId, String password, Integer delTag);
    boolean existsByUserIdAndDelTag(String userId, Integer delTag);


    @Modifying
    @Query("UPDATE User u SET u.delTag = ?1 WHERE u.userId = ?2")
    void updateDelTag(Integer delTag, String userId);
}