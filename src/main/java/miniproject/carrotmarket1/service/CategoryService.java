package miniproject.carrotmarket1.service;

import lombok.AllArgsConstructor;
import miniproject.carrotmarket1.dto.CategoryDTO;
import miniproject.carrotmarket1.repository.CategoryRepository;
import miniproject.carrotmarket1.util.CategoryMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public Optional<CategoryDTO> findById(Long categoryId) {
        return categoryId == null
                ? Optional.empty()
                : categoryRepository.findById(categoryId).map(categoryMapper::toDto);
    }

    public List<CategoryDTO> findAll() {
        return categoryMapper.toDtoList(categoryRepository.findByType("PRODUCT"));
    }

/*    public List<CategoryDTO> selectByCategoryId(Long id) {
        return categoryMapper.toDtoList(categoryRepository.selectByCategoryId(id));
    }*/
}
