package miniproject.carrotmarket1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long reviewerId;
    private Long revieweeId;
    private String content;
    private Integer rating;
    private Timestamp createdAt;

    // User 객체 관계 매핑 (리뷰어와 리뷰이)
    private UserDTO reviewer;
    private UserDTO reviewee;
}
