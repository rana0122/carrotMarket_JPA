package miniproject.carrotmarket1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import miniproject.carrotmarket1.entity.ReportStatus;

import java.sql.Timestamp;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private Long id;
    private Long productId;
    private Long reporterId;
    private Long categoryId;
    private Long adminId;
    private String details;
    private ReportStatus status;
    private Timestamp createdAt;
    private Timestamp resolvedAt;

    // Product, User, Category 객체 관계 매핑
    private ProductDTO product;
    private UserDTO reporter;
    private UserDTO admin;
    private CategoryDTO category;
}
