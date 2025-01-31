package miniproject.carrotmarket1.controller;

import jakarta.servlet.http.HttpSession;
import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.dto.UserDTO;
import miniproject.carrotmarket1.service.ProductService;
import miniproject.carrotmarket1.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/myproduct")
public class TradeController {
    private TradeService tradeService;
    private ProductService productService;

    @Autowired
    public TradeController(TradeService tradeService, ProductService productService) {
        this.tradeService = tradeService;
        this.productService = productService;
    }


    /* 로그인된 id가 작성한 글만 뷰에 올리기*/
    @GetMapping("/sell/{userId}")
    public String showMyProduct(@PathVariable Long userId, Model model, HttpSession session) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        List<ProductDTO> products = productService.findByUserId(userId); //판매물품 내역 조회
        model.addAttribute("products", products);
        model.addAttribute("user", loggedInUser);
        return "products/myproduct";
    }

    @GetMapping("/buy/{userId}")
    public String showBuyProduct(@PathVariable Long userId, Model model, HttpSession session) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        List<ProductDTO> products = tradeService.findProductsByBuyerId(userId); //구매물품 내역 조회
        model.addAttribute("products", products);
        model.addAttribute("user", loggedInUser);
        return "products/myproduct";
    }
}
