package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.Product;
import miniproject.carrotmarket1.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    @Query("SELECT p FROM Trade t JOIN t.product p WHERE t.buyer.id = :buyerId")
    List<Product> findProductsByBuyerId(@Param("buyerId") Long buyerId);
    Trade findByProductId(Long productId);
    void deleteById(Long id);
}


