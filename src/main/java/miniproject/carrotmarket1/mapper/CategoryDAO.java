package miniproject.carrotmarket1.mapper;

import miniproject.carrotmarket1.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryDAO {
    @Select("SELECT * FROM category WHERE id = #{id}")
    CategoryDTO selectById(Long id);

    @Select("SELECT * FROM category WHERE type = \"PRODUCT\" ")
    List<CategoryDTO> findAll();

    @Select("select * from product where category.id = #{id}")
    List<CategoryDTO> selectByCategoryId(Long id);
    // findById 메서드 추가 (이미 selectById가 있으므로 동일한 기능)
    @Select("SELECT * FROM category WHERE id = #{id}")
    CategoryDTO findById(Long id);
}
