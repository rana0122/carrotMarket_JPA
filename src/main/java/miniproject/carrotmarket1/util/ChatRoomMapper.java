package miniproject.carrotmarket1.util;

import miniproject.carrotmarket1.dto.ChatRoomDTO;
import miniproject.carrotmarket1.entity.ChatRoom;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatRoomMapper {

    private final ModelMapper modelMapper;

    public ChatRoomMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // 커스텀 매핑 설정
        modelMapper.typeMap(ChatRoom.class, ChatRoomDTO.class).addMappings(mapper -> {
            mapper.map(ChatRoom::getProduct, ChatRoomDTO::setProduct);
            mapper.map(ChatRoom::getBuyer, ChatRoomDTO::setBuyer);
            mapper.map(ChatRoom::getSeller, ChatRoomDTO::setSeller);
        });
    }

    // Entity -> DTO 변환
    public ChatRoomDTO toDto(ChatRoom chatRoom) {
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }

    // DTO -> Entity 변환
    public ChatRoom toEntity(ChatRoomDTO chatRoomDTO) {
        return modelMapper.map(chatRoomDTO, ChatRoom.class);
    }

    // List<Entity> -> List<DTO> 변환
    public List<ChatRoomDTO> toDtoList(List<ChatRoom> chatRooms) {
        return chatRooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity> 변환
    public List<ChatRoom> toEntityList(List<ChatRoomDTO> chatRoomDTOs) {
        return chatRoomDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

