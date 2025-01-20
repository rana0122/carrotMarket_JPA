package miniproject.carrotmarket1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "redirect:/products"; // 상품 리스트 페이지로 리다이렉트
    }
}
