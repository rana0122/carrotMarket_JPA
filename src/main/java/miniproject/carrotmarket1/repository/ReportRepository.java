package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.Category;
import miniproject.carrotmarket1.entity.Report;
import miniproject.carrotmarket1.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ReportRepository extends JpaRepository<Report, Long> {

    // 신고 목록 조회 (페이징)
    @Query(value = "SELECT r FROM Report r " +
            "JOIN r.reporter u " +
            "JOIN r.product p " +
            "JOIN r.category c " +
            "WHERE " +
            "(:startDate IS NULL OR r.createdAt > :startDate) " +
            "AND (:endDate IS NULL OR r.createdAt < :endDate) " +
            "AND (:status IS NULL OR r.status = :status) " +
            "AND (" +
            "(:tag IS NULL OR " +
            "(:tag = 'email' AND :search IS NOT NULL AND u.email LIKE CONCAT('%', :search, '%')) " +
            "OR (:tag = 'title' AND :search IS NOT NULL AND p.title LIKE CONCAT('%', :search, '%')) " +
            "OR (:tag = 'name' AND :search IS NOT NULL AND c.name LIKE CONCAT('%', :search, '%')))" +
            ")")
    Page<Report> getReportListPagination(
            @Param("startDate") Timestamp startDate,
            @Param("endDate") Timestamp endDate,
            @Param("status") ReportStatus status,  // <-- 수정: String → ReportStatus
            @Param("tag") String tag,
            @Param("search") String search,
            Pageable pageable
    );



    // 신고 목록 필터별 개수
    @Query(value = "SELECT COUNT(r) FROM Report r " +
            "JOIN r.reporter u " +
            "JOIN r.product p " +
            "JOIN r.category c " +
            "WHERE " +
            "(:startDate IS NULL OR r.createdAt > :startDate) " +
            "AND (:endDate IS NULL OR r.createdAt < :endDate) " +
            "AND (:status IS NULL OR r.status = :status) " +
            "AND ((:tag = 'email' AND :search IS NOT NULL AND u.email LIKE %:search%) " +
            "OR (:tag = 'title' AND :search IS NOT NULL AND p.title LIKE %:search%) " +
            "OR (:tag = 'name' AND :search IS NOT NULL AND c.name LIKE %:search%))")
    long countFilterReports(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("status") String status,
            @Param("tag") String tag,
            @Param("search") String search
            );

    // 신고 상세 조회
    Optional<Report> findById(Long id);
    // 신고 처리 상태 변경
    @Modifying
    @Query("UPDATE Report r SET r.status = :status, r.resolvedAt = :resolvedAt WHERE r.id = :reportId")
    void updateReportStatus(@Param("reportId") Long reportId,
                            @Param("status") String status,
                            @Param("resolvedAt") String resolvedAt);
    //게시글 숨김 관리
    @Modifying
    @Query("UPDATE Product p SET p.usedYn = :lockYn WHERE p.id = :productId")
    void updateProductLock(@Param("productId") Long productId, @Param("lockYn") String lockYn);
    //계정 잠금 기능
    @Modifying
    @Query("UPDATE User u SET u.lockedYn = :lockYn WHERE u.id = :userId")
    void updateUserLock(@Param("userId") Long userId, @Param("lockYn") String lockYn);
    //신고 페이지 카테고리
//    List<Category> getCategoriesByRange();
    //신고 내용 insert 기능
//    Object save(Report report);

}
