package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    //ID로 상품 상세 조회
    Optional<Product> findById(Long productId);

    //게시글 생성
//    Object save (Product product);
    
    //게시글 수정
//    void updateProduct(Product existingProduct);

    // 반경 내 게시글 조회 (쿼리 추가)
    @Query(value = "SELECT p FROM Product p WHERE " +
            "ST_Distance_Sphere(point(:longitude, :latitude), point(p.longitude, p.latitude)) <= :radiusKm * 1000 " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:status IS NULL OR :status = 'ALL' OR p.status = :status) " +
            "AND (:keyword IS NULL OR p.title LIKE %:keyword%)")
    Page<Product> findProductsWithinRadius(@Param("latitude") Double latitude,
                                           @Param("longitude") Double longitude,
                                           @Param("radiusKm") Double radiusKm,
                                           @Param("categoryId") Long categoryId,
                                           @Param("status") String status,
                                           @Param("keyword") String keyword,
                                           Pageable pageable);

    // 게시글 조회 (로그인하지 않은 경우)
    @Query(value = "SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:status IS NULL OR :status = 'ALL' OR p.status = :status) " +
            "AND (:keyword IS NULL OR p.title LIKE %:keyword%)")
    Page<Product> findProductsByConditions(@Param("categoryId") Long categoryId,
                                           @Param("status") String status,
                                           @Param("keyword") String keyword,
                                           Pageable pageable);

    //채팅에서 상품 거래 상태 변경
    @Modifying
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :productId")
    void updateReservationStatus(@Param("productId") Long productId, @Param("status") String status);


    //상품 삭제
    void deleteById(Long id);

    List<Product> findByUserId(Long userId);
}
