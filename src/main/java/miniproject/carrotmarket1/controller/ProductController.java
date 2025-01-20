package miniproject.carrotmarket1.controller;

import jakarta.servlet.http.HttpSession;
import miniproject.carrotmarket1.dto.ProductDTO;
import miniproject.carrotmarket1.dto.UserDTO;
import miniproject.carrotmarket1.dto.CategoryDTO;
import miniproject.carrotmarket1.service.CategoryService;
import miniproject.carrotmarket1.service.ProductService;
import miniproject.carrotmarket1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;
    private LocationController locationController;

    @Autowired
    public ProductController(ProductService productService, UserService userService,
                             CategoryService categoryService, LocationController locationController) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.locationController = locationController;
    }

    //상품 목록 페이지
    @GetMapping
    public String listAllProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "ALL") String status,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page, // 페이지 번호 추가
            @RequestParam(defaultValue = "8") int size, // 한 페이지에 보여줄 아이템 수
            HttpSession session,
            Model model) {

        // 모든 카테고리 로드
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("status", status);
        model.addAttribute("keyword", keyword);

        Page<ProductDTO> products = null;
        Pageable pageable = PageRequest.of(page, size);
        Optional<CategoryDTO> selectedCategory = categoryService.findById(categoryId);
        model.addAttribute("selectedCategory", selectedCategory);
        model.addAttribute("selectedCategoryId", categoryId);

        // 세션에서 사용자 위치 정보 가져오기
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getLatitude() != null && loggedInUser.getLongitude() != null) { //반경내 게시글 조회
            products = productService.findProductsWithinRadius(
                    loggedInUser.getLatitude(), loggedInUser.getLongitude(), loggedInUser.getRadiusKm(),
                    categoryId, status, keyword, pageable);
            for (ProductDTO product : products) {
                String drivingTime = locationController.calculateDistanceKakao(loggedInUser, product, "CAR");
                product.setDrivingTime(drivingTime);
            }

        } else {//게시글 조회(로그인 안한 경우)
            products = productService.findProductsByConditions(categoryId, status, keyword, pageable);
        }

        model.addAttribute("products", products);
        return "products/list";
    }



    //상품 목록 상세조회
    @GetMapping("/detail/{id}")
    public String showProduct(Model model, @PathVariable Long id, HttpSession session) {
        //게시글 조회
        ProductDTO product = productService.findItemById(id);
        //로그인 user  정보 조회
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        //판매자(게시글소유자) 정보 조회
        UserDTO user = userService.findById(product.getUserId());

        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("loggedInUser", loggedInUser);  // 로그인한 사용자 정보

        return "products/detail";
    }
    //========================게시글 생성==========================//
    //게시글 생성페이지
    @GetMapping("/write")
    public String showWritePage(HttpSession session, Model model) {
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("category", categories); // 뷰에 전달
        model.addAttribute("product", new ProductDTO());
        return "products/write";

    }
    //게시글 생성 저장
    @PostMapping("/save")
    public String createProduct(@ModelAttribute ProductDTO product,
                                @RequestParam("productImages") List<MultipartFile> productImages,
                                HttpSession session) throws IOException {

        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        product.setUserId(loggedInUser.getId());

        // 로그인한 사용자의 위치 정보를 상품에 설정
        product.setLocation(loggedInUser.getLocation());
        product.setLatitude(loggedInUser.getLatitude());
        product.setLongitude(loggedInUser.getLongitude());

        //게시글 저장
        productService.saveProductWithImages(product, productImages);

        return "redirect:/products";
    }

    //게시글 수정 페이지
    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, Model model, HttpSession session) {
        ProductDTO product = productService.findItemById(id);
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");

        if (!product.getUserId().equals(loggedInUser.getId())) {
            return "redirect:/products";
        }

        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("category", categories);
        model.addAttribute("product", product);
        return "products/edit";
    }

    //게시글 수정 저장
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute ProductDTO product,
                                @RequestParam(value = "productImages", required = false) List<MultipartFile> newImages,
                                @RequestParam(value = "deleteImageIds", required = false) List<Long> deleteImageIds,
                                HttpSession session) throws IOException {

        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        ProductDTO existingProduct = productService.findItemById(id);

        // 기존 상품이 없거나 로그인한 사용자가 상품 소유자가 아닌 경우
        if (existingProduct == null || !existingProduct.getUserId().equals(loggedInUser.getId())) {
            return "redirect:/products";
        }

        // 기존 상품의 userId를 유지
        product.setUserId(existingProduct.getUserId());

        productService.updateProductWithImages(id, product, newImages, deleteImageIds);
        return "redirect:/products/detail/" + id;
    }

    /* 게시글 삭제*/
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        UserDTO loggedInUser = (UserDTO) session.getAttribute("loggedInUser");
        boolean isDeleted = productService.deleteProductById(id, loggedInUser.getId());
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("success", "상품이 삭제되었습니다.");
        }
        return "redirect:/myproduct/sell/" + loggedInUser.getId();
    }



}
