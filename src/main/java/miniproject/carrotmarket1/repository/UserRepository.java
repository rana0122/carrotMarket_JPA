package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

//    Object save(User user);

    Optional<User> findById(Long id);

    User findByEmail(String email);

//    List<User> selectAllUsers();

//    void updateUser(User user);

//    void deleteUser(Long id);

    @Modifying
    @Query("UPDATE User u SET u.latitude = :latitude, u.longitude = :longitude, u.location = :location WHERE u.id = :userId")
    default void updateLocation(@Param("userId") Long userId, @Param("latitude") Double latitude,
                                @Param("longitude") Double longitude, @Param("location") String location) {

    }
}
