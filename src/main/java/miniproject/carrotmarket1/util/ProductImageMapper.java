package miniproject.carrotmarket1.util;

import miniproject.carrotmarket1.dto.ProductImageDTO;
import miniproject.carrotmarket1.entity.ProductImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper {

    private final ModelMapper modelMapper;

    public ProductImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProductImageDTO toDto(ProductImage productImage) {
        return modelMapper.map(productImage, ProductImageDTO.class);
    }

    public ProductImage toEntity(ProductImageDTO productImageDTO) {
        return modelMapper.map(productImageDTO, ProductImage.class);
    }
}
