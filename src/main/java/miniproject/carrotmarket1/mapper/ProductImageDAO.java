package miniproject.carrotmarket1.mapper;

import miniproject.carrotmarket1.entity.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductImageDAO {

    //게시글 이미지 생성
    @Insert("INSERT INTO product_image (product_id, image_url, uploaded_at) " +
            "VALUES (#{productId}, #{imageUrl}, #{uploadedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // id 자동 생성 활성화
    void insertProductImage(ProductImage productImage);

    //게시글 이미지 수정
    @Update("UPDATE product_image SET image_url = #{imageUrl} WHERE id = #{id}")
    void updateProductImage(ProductImage productImage);

    //id로 게시글 이미지 조회
    @Select("SELECT * FROM product_image WHERE id = #{id}")
    ProductImage findById(Long id);

    //게시글 이미지 삭제
    @Delete("DELETE FROM product_image WHERE id = #{id}")
    void deleteById(Long id);

    // 상품의 id를 기준으로 이미지 삭제
    @Delete("DELETE FROM product_image WHERE product_id = #{productId}")
    void deleteByProductId(Long productId);

    // 상품 id의 모든 이미지 리스트 조회
    @Select("SELECT * FROM product_image WHERE product_id = #{productId}")
    List<ProductImage> findByProductId(Long productId);
}
