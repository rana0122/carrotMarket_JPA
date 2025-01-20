package miniproject.carrotmarket1.mapper;

import miniproject.carrotmarket1.dto.ChatRoomDTO;
import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.dto.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ChatRoomDAO {
    @Insert("INSERT INTO chat_room (product_id, buyer_id, seller_id, created_at) " +
            "VALUES (#{productId}, #{buyerId}, #{sellerId}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertChatRoom(ChatRoomDTO chatRoom);

    @Select("SELECT * FROM chat_room WHERE product_id = #{productId} AND buyer_id = #{buyerId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "sellerId", column = "seller_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "buyer", column = "buyer_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "seller", column = "seller_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "product", column = "product_id", javaType = ProductDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.ProductDAO.findById"))
    })
    Optional<ChatRoomDTO> findByProductAndUsers(@Param("productId") Long productId,
                                                @Param("buyerId") Long buyerId);

    @Select("SELECT * FROM chat_room WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "sellerId", column = "seller_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "buyer", column = "buyer_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "seller", column = "seller_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "product", column = "product_id", javaType = ProductDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.ProductDAO.findById"))
    })
    Optional<ChatRoomDTO> findById(@Param("id") Long id);

    @Select("SELECT * FROM chat_room WHERE buyer_id = #{userId} OR seller_id = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "sellerId", column = "seller_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "buyer", column = "buyer_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "seller", column = "seller_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "product", column = "product_id", javaType = ProductDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.ProductDAO.findById"))
    })
    List<ChatRoomDTO> findAllByUser(@Param("userId")Long id);



}
