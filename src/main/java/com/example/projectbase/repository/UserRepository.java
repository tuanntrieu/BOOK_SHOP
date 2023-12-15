package com.example.projectbase.repository;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.domain.entity.User;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.security.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsAllByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT  u FROM User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findById(String id);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email=?2")
    Optional<User> fidByUsernameOrEmail(String username, String email);

    default User getUser(UserPrincipal currentUser) {
        return findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                        new String[]{currentUser.getUsername()}));
    }

}
