package miniproject.carrotmarket1.repository.MySQL;

import miniproject.carrotmarket1.mapper.TradeDAO;
import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.dto.TradeDTO;
import miniproject.carrotmarket1.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
/*

@Repository
public class MySQLTradeRepository implements TradeRepository {
    private final TradeDAO tradeDAO;

    @Autowired
    public MySQLTradeRepository(TradeDAO tradeDAO) {
        this.tradeDAO = tradeDAO;
    }

    @Override
    public List<ProductDTO> findByBuyerId(Long buyerId) {
        return tradeDAO.findByBuyerId(buyerId);
    }

    @Override
    public TradeDTO findByProductId(Long productId) {
        return tradeDAO.findByProductId(productId);
    }

    @Override
    public void createTrade(TradeDTO trade) {
        tradeDAO.createTrade(trade);
    }

    @Override
    public void updateTrade(TradeDTO trade) {
        tradeDAO.updateTrade(trade);
    }

    @Override
    public void deleteTrade(Long id) {
        tradeDAO.deleteTrade(id);
    }
}
*/
