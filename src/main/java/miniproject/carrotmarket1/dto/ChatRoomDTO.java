package miniproject.carrotmarket1.dto;

import lombok.*;

import java.sql.Timestamp;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private Timestamp createdAt;

    // Product 와 User 객체 관계 매핑
    private ProductDTO product;
    private UserDTO buyer;
    private UserDTO seller;
}
