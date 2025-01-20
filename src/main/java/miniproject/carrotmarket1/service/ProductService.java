package miniproject.carrotmarket1.service;

import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.entity.Category;
import miniproject.carrotmarket1.entity.Product;
import miniproject.carrotmarket1.entity.ProductImage;
import miniproject.carrotmarket1.repository.CategoryRepository;
import miniproject.carrotmarket1.repository.ProductImageRepository;
import miniproject.carrotmarket1.repository.ProductRepository;
import miniproject.carrotmarket1.util.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Value("${file.upload-dir-item}")
    private String uploadDir;

   @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper productMapper) {
       this.productRepository = productRepository;
       this.productImageRepository = productImageRepository;
       this.categoryRepository = categoryRepository;
       this.productMapper = productMapper;
   }


    //ID로 상품 상세 조회
    public ProductDTO findItemById(Long id){
        return productMapper.toDto(productRepository.findById(id).get());
    }

    // 게시글 저장 with 여러 사진 업로드
    @Transactional
    public void saveProductWithImages(ProductDTO product, List<MultipartFile> productImages) throws IOException {
        // 1. 기본 경로 설정
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // 2. 업로드 디렉토리가 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 3. 현재 시간 설정 및 상품 상태 설정
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        product.setStatus("SALE");
        //dto를 entity로 변환
        Product productEntity = productMapper.toEntity(product);

        // 4. 상품 정보 저장
        productRepository.save(productEntity);

        // 5. 이미지 파일 처리 및 저장
        if (productImages != null && !productImages.isEmpty()) {

            for (MultipartFile imageFile : productImages) {
                if (!imageFile.isEmpty()) {
                    // 먼저 ProductImage 엔티티를 생성하고 저장
                    String originalFilename = imageFile.getOriginalFilename();
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                    // 임시 이미지 URL 설정 (나중에 업데이트 됨)
                    ProductImage productImage = ProductImage.builder()
                            .product(productEntity)
                            .uploadedAt(new Timestamp(System.currentTimeMillis()))
                            .build();

                    // ProductImage 저장하여 ID 생성
                    productImageRepository.save(productImage);

                    // 생성된 ID를 사용하여 파일명 생성
                    String fileName = productImage.getId() + "_" + product.getId() + fileExtension;

                    // 파일 저장
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(imageFile.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                    // 실제 이미지 URL로 업데이트
                    productImage.setImageUrl("/itemimages/" + fileName);
                    productImageRepository.save(productImage);  // 이미지 URL 업데이트
                }
            }
        }
    }

    // 모든 카테고리 조회
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //게시글 수정
//    @Transactional
//    public void updateProductWithImages(Long id, Product updatedProduct,
//                                        List<MultipartFile> newImages,
//                                        List<Long> deleteImageIds) throws IOException {
//        Product existingProduct = findItemById(id);
//        if (existingProduct == null) {
//            throw new RuntimeException("Product not found");
//        }
//
//        // 기본 정보 업데이트
//        existingProduct.setTitle(updatedProduct.getTitle());
//        existingProduct.setDescription(updatedProduct.getDescription());
//        existingProduct.setPrice(updatedProduct.getPrice());
//        existingProduct.setCategoryId(updatedProduct.getCategoryId());
//        existingProduct.setLocation(updatedProduct.getLocation());
//        existingProduct.setLongitude(updatedProduct.getLongitude());
//        existingProduct.setLatitude(updatedProduct.getLatitude());
//
//        productRepository.updateProduct(existingProduct);
//
//        // 이미지 삭제 처리
//        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
//            for (Long imageId : deleteImageIds) {
//                ProductImage image = productImageRepository.findById(imageId);
//                if (image != null && image.getProductId().equals(id)) {  // 상품 ID 확인 추가
//                    // 실제 파일 삭제
//                    if (image.getImageUrl() != null) {
//                        String fileName = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
//                        Path filePath = Paths.get(uploadDir).resolve(fileName);
//                        try {
//                            Files.deleteIfExists(filePath);
//                        } catch (IOException e) {
//                            // 파일 삭제 실패 로깅
//                            e.printStackTrace();
//                        }
//                    }
//
//                    // DB에서 삭제
//                    productImageRepository.deleteById(imageId);
//                }
//            }
//        }
//
//        // 새 이미지 추가
//        if (newImages != null && !newImages.isEmpty()) {
//            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
//
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            for (MultipartFile imageFile : newImages) {
//                if (!imageFile.isEmpty()) {
//                    // 새 이미지 엔티티 생성
//                    ProductImage productImage = ProductImage.builder()
//                            .productId(id)
//                            .uploadedAt(new Timestamp(System.currentTimeMillis()))
//                            .build();
//
//                    // DB에 이미지 정보 저장
//                    productImageRepository.insertProductImage(productImage);
//
//                    // 파일 이름 생성 및 저장
//                    String originalFilename = imageFile.getOriginalFilename();
//                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                    String fileName = productImage.getId() + "_" + id + fileExtension;
//
//                    // 파일 저장
//                    Path filePath = uploadPath.resolve(fileName);
//                    Files.copy(imageFile.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
//
//                    // 이미지 URL 업데이트
//                    productImage.setImageUrl("/itemimages/" + fileName);
//                    productImageRepository.updateProductImage(productImage);
//                }
//            }
//        }
//    }
    //게시글 수정
    @Transactional
    public void updateProductWithImages(Long id, ProductDTO updatedProduct,
                                        List<MultipartFile> newImages,
                                        List<Long> deleteImageIds) throws IOException {
        // 기존 상품 조회
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product updatedProductEntity= productMapper.toEntity(updatedProduct);

        // 기존 상품 정보 업데이트
        existingProduct.update(updatedProductEntity);

        // 이미지 삭제 처리
        if (deleteImageIds != null && !deleteImageIds.isEmpty()) {
            for (Long imageId : deleteImageIds) {
                ProductImage image = productImageRepository.findById(imageId)
                        .orElseThrow(() -> new RuntimeException("Image not found"));
                if (image.getProduct().getId().equals(id)) {  // 상품 ID 확인
                    // 파일 삭제
                    deleteImageFile(image);
                    productImageRepository.delete(image); // 이미지 삭제
                }
            }
        }

        // 새 이미지 추가
        if (newImages != null && !newImages.isEmpty()) {
            for (MultipartFile imageFile : newImages) {
                if (!imageFile.isEmpty()) {
                    // 새 이미지 저장
                    ProductImage productImage = saveNewImage(imageFile, existingProduct);
                    productImageRepository.save(productImage);
                }
            }
        }
    }

    private void deleteImageFile(ProductImage image) throws IOException {
        if (image.getImageUrl() != null) {
            String fileName = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }
    private ProductImage saveNewImage(MultipartFile imageFile, Product product) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 이미지 파일 저장
        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + "_" + product.getId() + fileExtension;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // 이미지 엔티티 생성
        return ProductImage.builder()
                .product(product)
                .imageUrl("/itemimages/" + fileName)
                .uploadedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    //반경내 게시글 조회
    public Page<ProductDTO> findProductsWithinRadius(Double latitude, Double longitude, Double radiusKm,
                                                     Long categoryId, String status, String keyword, Pageable pageable) {

        // Product 엔티티를 가져옴
        Page<Product> productPage = productRepository.findProductsWithinRadius(latitude, longitude, radiusKm, categoryId, status, keyword, pageable);

        // Product -> ProductDTO 변환
        List<ProductDTO> productDTOs = productPage.getContent().stream()
                .map(productMapper::toDto) // ProductMapper를 사용하여 변환
                .collect(Collectors.toList());

        // DTO 리스트를 새로운 Page로 반환
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    //게시글 조회(로그인 안한 경우)
    public Page<ProductDTO> findProductsByConditions(Long categoryId, String status, String keyword, Pageable pageable) {
        // Product 엔티티를 가져옴
        Page<Product> productPage = productRepository.findProductsByConditions(categoryId, status, keyword, pageable);

        // Product -> ProductDTO 변환
        List<ProductDTO> productDTOs = productMapper.toDtoList(productPage.getContent());

        // DTO 리스트를 새로운 Page로 반환
        return new PageImpl<>(productDTOs, pageable, productPage.getTotalElements());
    }

    public void updateReservationStatus(Long productId, String status) {
       productRepository.updateReservationStatus(productId, status);
    }

    //게시글 삭제
    @Transactional
    public boolean deleteProductById(Long id, Long userId) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isPresent() && productOpt.get().getUser().getId().equals(userId)) {
            Product product = productOpt.get();

            // 1. 이미지 파일 삭제
            List<ProductImage> images = product.getImages();
            for (ProductImage image : images) {
                if (image.getImageUrl() != null) {
                    try {
                        String fileName = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
                        Path filePath = Paths.get(uploadDir).resolve(fileName);
                        Files.deleteIfExists(filePath);
                    } catch (IOException e) {
                        e.printStackTrace(); // 파일 삭제 실패 시 로그 기록
                    }
                }
            }

            // 2. 상품 삭제 (연관된 ProductImage도 자동 삭제) cascade 옵션
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    //사용자id로 작성한 게시글을 조회
    public List<ProductDTO> findByUserId(Long userId) {
       return productMapper.toDtoList(productRepository.findByUserId(userId));
    }
}

