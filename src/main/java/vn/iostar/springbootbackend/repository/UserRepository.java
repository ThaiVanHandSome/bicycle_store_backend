package vn.iostar.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.iostar.springbootbackend.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.idUser = ?1")
    Optional<User> findByIdUser(Long id_user);
    public Optional<User> findByEmail(String email);
}
