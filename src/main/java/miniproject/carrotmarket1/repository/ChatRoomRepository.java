package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

//    void insertChatRoom(ChatRoom chatRoom);
    Optional<ChatRoom> findById(Long id);

    // 특정 상품과 구매자에 해당하는 채팅방 조회
    @Query("SELECT c FROM ChatRoom c WHERE c.product.id = :productId AND c.buyer.id = :buyerId")
    Optional<ChatRoom> findByProductAndUsers(@Param("productId") Long productId, @Param("buyerId") Long buyerId);

    // 특정 사용자와 연관된 모든 채팅방 조회
    @Query("SELECT c FROM ChatRoom c WHERE c.buyer.id = :userId OR c.seller.id = :userId")
    List<ChatRoom> findAllByUser(@Param("userId") Long userId);
}
