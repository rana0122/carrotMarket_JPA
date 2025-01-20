package miniproject.carrotmarket1.repository.MySQL;


import miniproject.carrotmarket1.mapper.ProductImageDAO;
import miniproject.carrotmarket1.entity.ProductImage;
import miniproject.carrotmarket1.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/*

@Repository

public class MySQLProductImageRepository implements ProductImageRepository {
    private final ProductImageDAO productImageDAO ;

    @Autowired
    public MySQLProductImageRepository(ProductImageDAO productImageDAO) {
        this.productImageDAO = productImageDAO;
    }

    //게시글 이미지 생성
    public void insertProductImage(ProductImage productImage){
       productImageDAO.insertProductImage(productImage);
    }

    //게시글 이미지 수정
    public void updateProductImage(ProductImage productImage){
        productImageDAO.updateProductImage(productImage);
    }

    //id로 게시글 이미지 조회
    @Override
    public Optional<ProductImage> findById(Long id) {
        return productImageDAO.findById(id);
    }

    //게시글 이미지 삭제
    @Override
    public void deleteById(Long id) {
        productImageDAO.deleteById(id);
    }

    @Override
    public void deleteByProductId(Long productId) {
        productImageDAO.deleteByProductId(productId);
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return productImageDAO.findByProductId(productId);
    }

}
*/
