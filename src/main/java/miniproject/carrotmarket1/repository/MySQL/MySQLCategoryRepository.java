package miniproject.carrotmarket1.repository.MySQL;

import miniproject.carrotmarket1.mapper.CategoryDAO;
import miniproject.carrotmarket1.dto.CategoryDTO;
import miniproject.carrotmarket1.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
/*

@Repository
public class MySQLCategoryRepository implements CategoryRepository {
    private final CategoryDAO categoryDAO;
    @Autowired
    public MySQLCategoryRepository(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public List<Category> selectByCategoryId(Long id) {
        return categoryDAO.selectByCategoryId(id);
    }
    public Category findById(Long categoryId){
        return categoryDAO.findById(categoryId);
    }

}
*/
