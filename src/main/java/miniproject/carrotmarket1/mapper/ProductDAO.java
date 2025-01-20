package miniproject.carrotmarket1.mapper;

import miniproject.carrotmarket1.dto.CategoryDTO;
import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.entity.ProductImage;
import miniproject.carrotmarket1.dto.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductDAO {

    //게시글 생성
    @Insert("INSERT INTO product (title, description, price, location, latitude, longitude, " +
            "category_id, created_at, user_id, status) " +
            "VALUES (#{title}, #{description}, #{price}, #{location}, #{latitude}, #{longitude}, " +
            "#{categoryId}, #{createdAt}, #{userId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProduct(ProductDTO product);

    //게시글 생성시 이미지 업로드
    @Insert("INSERT INTO product_image (product_id, image_url, uploaded_at) " +
            "VALUES (#{product_id}, #{image_url}, #{uploaded_at})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProductImage(ProductImage productImage);


    //게시글 상세조회
    @Select("SELECT * FROM product WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "categoryId", column = "category_id"),
            @Result(property = "user", column = "user_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "category", column = "category_id", javaType = CategoryDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.CategoryDAO.selectById")), // 경로 수정
            @Result(property = "images", column = "id", javaType = List.class,
                    many = @Many(select = "selectProductImagesByProductId"))
    })
    ProductDTO findById(Long id);


    // Product ID에 의해 ProductImage들을 조회하는 메소드
    @Select("SELECT * FROM product_image WHERE product_id = #{id}")
    List<ProductImage> selectProductImagesByProductId(Long id);


    //게시글 수정
    @Update("UPDATE product SET title = #{title}, description = #{description}, " +
            "location = #{location}, latitude = #{latitude}, longitude = #{longitude}, " +
            "price = #{price}, category_id = #{categoryId} WHERE id = #{id}")
    void updateProduct(ProductDTO product);


    //조건에 따라 동적으로 상품 조회
    List<ProductDTO> findProductsByConditions(@Param("categoryId") Long categoryId,
                                              @Param("status") String status,
                                              @Param("keyword") String keyword,
                                              @Param("pageSize") int pageSize,
                                              @Param("offset") int offset);

    //반경 내 상품 조회
    List<ProductDTO> findProductsWithinRadius(@Param("latitude") double latitude,
                                              @Param("longitude") double longitude,
                                              @Param("radiusKm") double radiusKm,
                                              @Param("categoryId") Long categoryId,
                                              @Param("status") String status,
                                              @Param("keyword") String keyword,
                                              @Param("pageSize") int pageSize,
                                              @Param("offset") int offset);

    //채팅룸에서 거래 상태 변경
    @Update("UPDATE product SET status = #{status} WHERE id = #{productId}")
    void updateReservationStatus(@Param("productId") Long productId, @Param("status") String status);

    @Delete("DELETE FROM product WHERE id = #{id}")
    void deleteById(Long id);

    /* 사용자 id로 작성한 게시글을 조회*/
    @Select("SELECT * FROM product WHERE user_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "category", column = "category_id", javaType = CategoryDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.CategoryDAO.selectById")), // 경로 수정
            @Result(property = "images", column = "id", javaType = List.class,
                    many = @Many(select = "selectProductImagesByProductId"))
    })
    List<ProductDTO> findByUserId(Long userId);
}
