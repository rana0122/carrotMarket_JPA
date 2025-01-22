package miniproject.carrotmarket1.controller;

import jakarta.servlet.http.HttpSession;
import miniproject.carrotmarket1.dto.ChatRoomDTO;
import miniproject.carrotmarket1.dto.TradeDTO;
import miniproject.carrotmarket1.dto.UserDTO;
import miniproject.carrotmarket1.service.ChatRoomService;
import miniproject.carrotmarket1.service.ProductService;
import miniproject.carrotmarket1.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ProductService productService;
    private final TradeService tradeService;

    @Autowired
    public ChatController(ChatRoomService chatRoomService, ProductService productService,TradeService tradeService) {

        this.chatRoomService = chatRoomService;
        this.productService = productService;
        this.tradeService = tradeService;
    }

    // 채팅룸 목록 보기
    @GetMapping("/chatroom")
    public String listChatRooms(Model model, HttpSession session) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        List<ChatRoomDTO> chatRooms = chatRoomService.findAllByUser(loggedInUser.getId());
        model.addAttribute("chatRooms", chatRooms);
        return "chat/chatroom";  // chatroom.html로 이동
    }

    // 특정 상품에 대한 채팅룸 생성
    @PostMapping("/chatroom/create")
    public String createChatRoom(@RequestParam Long productId,
                                 @RequestParam Long sellerId,
                                 HttpSession session,
                                 Model model) {
        // 로그인한 사용자 확인
        UserDTO buyer = (UserDTO) session.getAttribute("loggedInUser");
        if (buyer == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/userlogin"; // 로그인 페이지로 리다이렉트
        }

        // 채팅방 생성
        ChatRoomDTO chatRoom = chatRoomService.createChatRoom(productId, sellerId, buyer.getId());
        // 생성된 채팅방으로 리다이렉트
        return "redirect:/chatroom/" + chatRoom.getId().toString();
    }

    //채팅룸 입장
    @GetMapping("/chatroom/{id}")
    public String enterChatRoom(@PathVariable Long id, HttpSession session, Model model) {
        Optional<ChatRoomDTO> chatRoomOptional = chatRoomService.findById(id);
        if (chatRoomOptional.isPresent()) {
            ChatRoomDTO chatRoom = chatRoomOptional.get();
            UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
            if (loggedInUser == null) {
                return "redirect:/userlogin";  // 로그인 정보가 없으면 로그인 페이지로 리디렉션
            }
            model.addAttribute("chatRoom", chatRoom);
            model.addAttribute("loggedInUser", loggedInUser);
            return "chat/chat";  // chat.html로 이동하여 채팅 진행
        } else {
            return "redirect:/error";  // 채팅방을 찾을 수 없을 때
        }
    }

    //채팅에서 상품 거래 상태 변경 처리
    @PostMapping("/update-reservation-status")
    @ResponseBody
    public ResponseEntity<?> updateReservationStatus(@RequestBody Map<String, String> request) {
        String status = request.get("status");
        Long chatRoomId = Long.valueOf(request.get("chatRoomId"));
        Optional<ChatRoomDTO> chatRoomOptional = chatRoomService.findById(chatRoomId);

        if (chatRoomOptional.isPresent()) {
            ChatRoomDTO chatRoom = chatRoomOptional.get();
            Long productId = chatRoom.getProductId();

            // 상품 상태 업데이트
            productService.updateReservationStatus(productId, status);

            // Trade 테이블 관리 로직
            Optional<TradeDTO> tradeOptional = tradeService.findByProductId(productId);

            if (tradeOptional.isPresent()) {
                TradeDTO trade = tradeOptional.get();
                if (status.equals("RESERVED") || status.equals("SOLD")) {
                    // 기존 trade 레코드 업데이트
                    trade.setStatus(status);
                    tradeService.updateTrade(trade);
                } else if (status.equals("SALE")) {
                    // trade 레코드 삭제
                    tradeService.deleteTrade(trade.getId());
                }
            } else {
                if (status.equals("RESERVED") || status.equals("SOLD")) {
                    // 새로운 trade 레코드 생성
                    TradeDTO newTrade = TradeDTO.builder()
                            .productId(productId)
                            .buyerId(chatRoom.getBuyerId())
                            .status(status)
                            .tradeDate(new Timestamp(System.currentTimeMillis()))
                            .build();
                    tradeService.createTrade(newTrade);
                }
            }
            return ResponseEntity.ok(Map.of("success", true));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("success", false, "message", "Chat room not found"));
    }
//    @PostMapping("/update-reservation-status")
//    @ResponseBody
//    public ResponseEntity<?> updateReservationStatus(@RequestBody Map<String, String> request) {
//        String status = request.get("status");
//        Long chatRoomId = Long.valueOf(request.get("chatRoomId"));
//        Optional<ChatRoom> chatRoomOptional = chatRoomService.findById(chatRoomId);
//        if (chatRoomOptional.isPresent()) {
//            ChatRoom chatRoom = chatRoomOptional.get();
//
//            // 예약 상태 업데이트 로직 (예: 데이터베이스 업데이트)
//            productService.updateReservationStatus(chatRoom.getProductId(), status);
//           // chatroom이 들고 있는 product_id로 trade 테이블을 가져옴
//
//            Optional<Trade> TradeOptional = tradeService.findById(chatRoom.getProductId());
//                //trade table 이 존재하고 status가  reserved 나 sold이면 trade객체를 update 해줌
//
//                //trade table이 존재하는데 status가 sale이면 table을 삭제해줌(판매중으로 바꿨다는건 거래가 파토났다는 뜻이기 떄문.)
//                //trade table이 존재하지 않고 status가 reserved나 sold면 table 객체를 생성함 (insert 쿼리 생성)
//            if (TradeOptional.isPresent()) {
//                Trade Trade = TradeOptional.get();
//                if(status.equals("RESERVED") || status.equals("SOLD")) {
//                    //update status
//
//
//                }else if(status.equals("SALE")) {
//                    //delete
//
//
//                }
//
//            }else{
//                if(status.equals("RESERVED")  status.equals("SOLD")){
//                    //insert status
//
//
//                }
//            }
//
//            //프로덕트 status 조회를 해
//            //optional로 가져와
//            //status를 확인을 해서 판매중은, trade테이블을 지워
//            //status reserved, sold면 업데이트를 치고
//
//
//
//
//
//            //tradeService.updateReservationStatus(chatRoom.getProductId(), status);
//        }
//        return ResponseEntity.ok(Map.of("success", true));
//    }
}