package miniproject.carrotmarket1.util;

import miniproject.carrotmarket1.dto.TradeDTO;
import miniproject.carrotmarket1.entity.Trade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TradeMapper {

    private final ModelMapper modelMapper;

    public TradeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // 커스텀 매핑 설정
        modelMapper.typeMap(Trade.class, TradeDTO.class).addMappings(mapper -> {
            mapper.map(src -> src.getBuyer().getId(), TradeDTO::setBuyerId);
            mapper.map(src -> src.getProduct().getId(), TradeDTO::setProductId);
        });
    }

    // Entity -> DTO 변환
    public TradeDTO toDto(Trade trade) {
        return modelMapper.map(trade, TradeDTO.class);
    }

    // DTO -> Entity 변환
    public Trade toEntity(TradeDTO tradeDTO) {
        return modelMapper.map(tradeDTO, Trade.class);
    }

    // List<Entity> -> List<DTO> 변환
    public List<TradeDTO> toDtoList(List<Trade> trades) {
        return trades.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity> 변환
    public List<Trade> toEntityList(List<TradeDTO> tradeDTOs) {
        return tradeDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

