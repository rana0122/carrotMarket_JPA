package miniproject.carrotmarket1.util;

import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.dto.ProductImageDTO;
import miniproject.carrotmarket1.entity.Product;
import miniproject.carrotmarket1.entity.ProductImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;
    private final ProductImageMapper productImageMapper;

    public ProductMapper(ModelMapper modelMapper, ProductImageMapper productImageMapper) {
        this.modelMapper = new ModelMapper();
        this.productImageMapper = productImageMapper;
        configureMapper();
    }
    private void configureMapper() {
        modelMapper.typeMap(Product.class, ProductDTO.class).addMappings(mapper -> {
            mapper.skip(ProductDTO::setUser); // 필요 시 특정 필드 제외
            mapper.map(Product::getImages, ProductDTO::setImages);
        });
    }

    public ProductDTO toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        // ProductImage 리스트 매핑 추가
        List<ProductImageDTO> imageDTOs = product.getImages() != null ?
                product.getImages().stream()
                        .map(productImageMapper::toDto)
                        .collect(Collectors.toList())
                : Collections.emptyList();
        productDTO.setImages(imageDTOs);
        return productDTO;
    }

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = modelMapper.map(productDTO, Product.class);
        // ProductImage 리스트 매핑 추가
        List<ProductImage> images = productDTO.getImages() != null ?
                productDTO.getImages().stream()
                        .map(productImageMapper::toEntity)
                        .collect(Collectors.toList())
                : Collections.emptyList();
        product.setImages(images);
        return product;
    }

    public List<ProductDTO> toDtoList(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Product> toEntityList(List<ProductDTO> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }
        return products.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
