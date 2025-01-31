package miniproject.carrotmarket1.service;

import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.dto.TradeDTO;
import miniproject.carrotmarket1.entity.Trade;
import miniproject.carrotmarket1.repository.ProductRepository;
import miniproject.carrotmarket1.repository.TradeRepository;
import miniproject.carrotmarket1.util.ProductMapper;
import miniproject.carrotmarket1.util.TradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;
    private final ProductMapper productMapper;
    @Autowired
    public TradeService(TradeRepository tradeRepository,
                        TradeMapper tradeMapper, ProductMapper productMapper) {
        this.tradeRepository = tradeRepository;
        this.tradeMapper = tradeMapper;
        this.productMapper = productMapper;
    }

    // 기존 메서드 유지
    public List<ProductDTO> findProductsByBuyerId(Long buyerId) {
        return productMapper.toDtoList(tradeRepository.findProductsByBuyerId(buyerId));
    }

    // 새로운 메서드 추가
    public Optional<TradeDTO> findByProductId(Long productId) {
        Trade trade = tradeRepository.findByProductId(productId);
        return Optional.ofNullable(tradeMapper.toDto(trade));
    }

    public void createTrade(TradeDTO trade) {
        tradeRepository.save(tradeMapper.toEntity(trade));
    }

    public void updateTrade(TradeDTO trade) {
        tradeRepository.save(tradeMapper.toEntity(trade));
    }

    public void deleteTrade(Long id) {
        tradeRepository.deleteById(id);
    }
}