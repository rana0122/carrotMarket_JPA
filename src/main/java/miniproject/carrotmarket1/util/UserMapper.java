package miniproject.carrotmarket1.util;

import miniproject.carrotmarket1.dto.UserDTO;
import miniproject.carrotmarket1.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Entity -> DTO 변환
    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    // DTO -> Entity 변환
    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    // List<Entity> -> List<DTO> 변환
    public List<UserDTO> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity> 변환
    public List<User> toEntityList(List<UserDTO> userDTOs) {
        return userDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

