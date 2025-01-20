package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.Product;
import miniproject.carrotmarket1.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Product> findByBuyerId(Long buyerId);
    Trade findByProductId(Long productId);
//    Object save (Trade trade);
//    void updateTrade(Trade trade);
    void deleteById(Long id);
}


