package miniproject.carrotmarket1.service;

import miniproject.carrotmarket1.dto.ChatRoomDTO;
import miniproject.carrotmarket1.entity.ChatRoom;
import miniproject.carrotmarket1.entity.Product;
import miniproject.carrotmarket1.entity.User;
import miniproject.carrotmarket1.repository.ChatRoomRepository;
import miniproject.carrotmarket1.repository.ProductRepository;
import miniproject.carrotmarket1.repository.UserRepository;
import miniproject.carrotmarket1.util.ChatRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, ChatRoomMapper chatRoomMapper,
                           UserRepository userRepository, ProductRepository productRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMapper = chatRoomMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public ChatRoomDTO createChatRoom(Long productId, Long sellerId, Long buyerId) {
        // 이미 존재하는 채팅방 확인
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByProductAndUsers(productId, buyerId);
        if (existingChatRoom.isPresent()) {
            return chatRoomMapper.toDto(existingChatRoom.get());
        }

        // 새 채팅방 생성
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        ChatRoom chatRoom = ChatRoom.builder()
                .product(product)
                .seller(seller)
                .buyer(buyer)
                .build();

        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoomMapper.toDto(chatRoom);
    }

    public Optional<ChatRoomDTO> findById(Long id) {
        return Optional.ofNullable(chatRoomMapper.toDto(chatRoomRepository.findById(id).get()));
    }

    public List<ChatRoomDTO> findAllByUser(Long id) {
        return chatRoomMapper.toDtoList(chatRoomRepository.findAllByUser(id));
    }
}
