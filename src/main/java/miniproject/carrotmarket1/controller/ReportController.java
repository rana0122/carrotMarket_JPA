package miniproject.carrotmarket1.controller;

import lombok.RequiredArgsConstructor;
import miniproject.carrotmarket1.dto.*;
import miniproject.carrotmarket1.entity.Category;
import miniproject.carrotmarket1.entity.ReportStatus;
import miniproject.carrotmarket1.service.CategoryService;
import miniproject.carrotmarket1.service.ProductService;
import miniproject.carrotmarket1.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final ProductService productService;

    //신고 목록 조회 (필터 기능, 페이징 기능)
    @GetMapping("/reports")
    public String getReportListPagination(Model model,
                                          @RequestParam(required = false) String startDate,
                                          @RequestParam(required = false) String endDate,
                                          @RequestParam(required = false) String status,
                                          @RequestParam(required = false) String tag,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size
    ) {
        Page<ReportDTO> reports = reportService.getReportListPagination(startDate, endDate, status,
                page, size, tag, search);
        model.addAttribute("reports", reports); // 보고서 데이터
        model.addAttribute("page",reports); // 페이지 정보
        //<select>에 매핑할 ReportStatus(Enum) 상수 값 전달
        model.addAttribute("statusList", ReportStatus.values());

        // 페이지 번호 그룹 계산 (한 그룹에 5개씩)
        int totalPages = reports.getTotalPages();
        int currentGroup = page / 5; // 5개씩 묶인 그룹의 인덱스
        int startPage = currentGroup * 5; // 그룹의 시작 페이지 번호
        int endPage = Math.min(startPage + 4, totalPages - 1); // 그룹의 끝 페이지 번호 (총 페이지를 넘지 않도록 제한)

        // 현재 그룹에 표시할 페이지 번호 리스트 생성
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("pageNumbers", pageNumbers);

        //NULL값이 포함될 수 있으므로 필터를 조건부로 추가
        Map<String, String> currentFilters = new HashMap<>();
        currentFilters.put("startDate", startDate != null ? startDate : "");
        currentFilters.put("endDate", endDate != null ? endDate : "");
        currentFilters.put("status", status != null ? status : "");
        //검색 기능
        currentFilters.put("tag", tag != null ? tag : "");
        currentFilters.put("search", search != null ? search : "");
        model.addAttribute("currentFilters", currentFilters);

        return "reports/report-list";
    }

    //신고 상세 조회
    @GetMapping("/reports/{id}")
    public String getReportDetails(@PathVariable Long id, Model model) {
        ReportDTO report = reportService.getReportById(id);
        List<CategoryDTO> categories = reportService.getCategoriesByRange();
        model.addAttribute("categories", categories);

        model.addAttribute("report", report);
        //<select>에 매핑할 ReportStatus(Enum) 상수 값 전달
        model.addAttribute("statusList", ReportStatus.values());
        return "/reports/report-edit";
    }

    //신고 처리 상태 변경
    @PostMapping("/reports/update/{id}")
    public String updateReportStatus(@PathVariable Long id, @RequestParam ReportStatus status) {
        reportService.updateReportStatus(id, status);
        return "redirect:/admin/reports";
    }


    //신고글 숨김 처리 기능
    @PostMapping("/reports/hide-product/{productId}")
    public String updateProductLock(@PathVariable Long productId, @RequestParam String usedYn) {
        String lockYn = "HIDE".equals(usedYn) ? "N" : "Y";
        reportService.updateProductLock(productId, lockYn);
        return "redirect:/admin/reports";
    }

    //계정잠금 기능
    @PostMapping("/reports/lock-user/{userId}")
    public String UpdateUserLock(@PathVariable Long userId, @RequestParam String lockUser) {
        String lockYn = "LOCK_ACCOUNT".equals(lockUser) ? "Y" : "N";
        reportService.updateUserLock(userId, lockYn);
        return "redirect:/admin/reports";
    }


    //신고 페이지
//    @GetMapping("/report/{id}")
//    public String reportChoice(@PathVariable Long id, Model model) {
//        List<Category> categories = reportService.getCategoriesByRange();
//        Report report = reportService.getReportById(id);
//        model.addAttribute("report", report);
//        model.addAttribute("categories", categories);
//        return "reports/report";
//    }
    //신고 페이지
    @GetMapping("/report/{id}")
    public String reportChoice(@PathVariable Long id, Model model,
                               @SessionAttribute("loggedInUser") UserDTO loggedInUser) {
        ProductDTO product = productService.findItemById(id); // 상품 정보 조회
        List<CategoryDTO> categories = reportService.getCategoriesByRange(); // 신고 카테고리 조회

        // 신고 객체 초기화
        ReportDTO report = new ReportDTO();
        report.setProduct(product);
        report.setReporter(loggedInUser);
        report.setStatus(ReportStatus.PENDING); // 상태 초기화

        model.addAttribute("report", report);
        model.addAttribute("categories", categories);
        model.addAttribute("statusList", ReportStatus.values());
        return "reports/report";
    }

    //신고 내용 insert 기능
//    @PostMapping("/report/{id}")
//    public String insertReport(@PathVariable("id") Long id, Report report) {
//        reportService.getReportById(id);
//        reportService.insertReport(report);
//        System.out.println(report);
//        return "redirect:/products";
//    }

    //신고 내용 insert 기능
    @PostMapping("/report/{id}")
    public String insertReport(@PathVariable("id") Long id, @ModelAttribute ReportDTO report,
                               @SessionAttribute("loggedInUser") UserDTO loggedInUser) {
        ProductDTO product = productService.findItemById(id);
        List<CategoryDTO> categories = reportService.getCategoriesByRange(); // 신고 카테고리 조회
        for (CategoryDTO category : categories) {
            if(category.getId().equals(report.getCategory().getId())) {
                report.setCategory(category);
            }
        }
        report.setProduct(product);
        report.setReporter(loggedInUser);
        report.setStatus(ReportStatus.PENDING); // 초기 상태 설정

        reportService.insertReport(report); // 신고 저장
        return "redirect:/products";
    }

}









