package miniproject.carrotmarket1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Entity
@Table(name = "report")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "resolved_at")
    private Timestamp resolvedAt;

    // 상태 변경 로직을 캡슐화한 메서드
    public void changeStatus(ReportStatus newStatus) {
        this.status = newStatus;

        if (ReportStatus.PENDING == newStatus) {
            this.resolvedAt = null; // PENDING 상태에서는 처리 시간 초기화
        } else {
            this.resolvedAt = new Timestamp(System.currentTimeMillis()); // 완료 상태 처리 시간 기록
        }
    }
}

