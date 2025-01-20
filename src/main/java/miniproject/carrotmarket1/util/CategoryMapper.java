package miniproject.carrotmarket1.util;

import miniproject.carrotmarket1.dto.CategoryDTO;
import miniproject.carrotmarket1.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Entity -> DTO 변환
    public CategoryDTO toDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    // DTO -> Entity 변환
    public Category toEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

    // List<Entity> -> List<DTO> 변환
    public List<CategoryDTO> toDtoList(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity> 변환
    public List<Category> toEntityList(List<CategoryDTO> categoryDTOs) {
        return categoryDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
