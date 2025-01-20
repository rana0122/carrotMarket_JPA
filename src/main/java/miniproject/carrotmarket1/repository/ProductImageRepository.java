package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository  extends JpaRepository<ProductImage, Long> {
    //상품이미지 업로드
//    Object save(ProductImage productImage);

    //상품이미지 업데이트
//    void updateProductImage(ProductImage productImage);

    //상품이미지 조회
    Optional<ProductImage> findById(Long id);

    //상품이미지 삭제
    void deleteById(Long id);

    // 상품의 id를 기준으로 이미지 삭제
//    void deleteByProductId(Long productId);

    // 상품 id의 모든 이미지 리스트 조회
//    List<ProductImage> findByProductId(Long productId);
}
