package miniproject.carrotmarket1.service;

import lombok.RequiredArgsConstructor;
import miniproject.carrotmarket1.dto.CategoryDTO;
import miniproject.carrotmarket1.dto.ReportDTO;
import miniproject.carrotmarket1.entity.ReportStatus;
import miniproject.carrotmarket1.entity.Report;
import miniproject.carrotmarket1.repository.CategoryRepository;
import miniproject.carrotmarket1.repository.ReportRepository;
import miniproject.carrotmarket1.util.CategoryMapper;
import miniproject.carrotmarket1.util.ReportMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final CategoryRepository categoryRepository;
    private final ReportMapper reportMapper;
    private final CategoryMapper categoryMapper;

    //신고 목록 조회(필터 기능, 페이징 기능)
    public Page<ReportDTO> getReportListPagination(String startDate,
                                                   String endDate,
                                                   String status,
                                                   int page,
                                                   int size,
                                                   String tag,
                                                   String search) {
        Pageable pageable = PageRequest.of(page, size);
        // 문자열 날짜를 Timestamp로 변환 (null 체크)
        Timestamp startTimestamp = parseTimestamp(startDate);
        Timestamp endTimestamp = parseTimestamp(endDate);
        // String을 ReportStatus Enum으로 변환
        ReportStatus reportStatus = parseReportStatus(status);

        //  Repository 호출 (변환된 값 사용)
        Page<Report> reportPage =
                reportRepository.getReportListPagination(startTimestamp, endTimestamp, reportStatus, tag, search, pageable);

        return reportPage.map(reportMapper::toDto);
    }

    // Enum 변환 메서드
    private ReportStatus parseReportStatus(String status) {
        if (status == null || status.isEmpty()) {
            return null;
        }
        try {
            return ReportStatus.valueOf(status.toUpperCase()); // Enum 변환
        } catch (IllegalArgumentException e) {
            return null; // 잘못된 값이면 null 반환 (오류 방지)
        }
    }

    // 날짜 변환 메서드
    private Timestamp parseTimestamp(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return Timestamp.valueOf(localDateTime);
    }

    //신고 상세 조회
    public ReportDTO getReportById(Long id) {
        return reportMapper.toDto(reportRepository.findById(id).get());
    }

    //신고 처리 상태 변경
    public void updateReportStatus(Long id, ReportStatus status) {
        //id로 기존 report 조회
        Report report = reportRepository.findById(id).get();
        if(report != null){
            // 엔티티 메서드 호출로 상태 변경
            report.changeStatus(status);
            reportRepository.save(report);
        }
        else{
            throw new IllegalArgumentException("신고 ID가 존재하지 않습니다: " + id);
        }

    }
    //신고글 숨김 처리 기능
    public void updateProductLock(Long productId, String lockYn) {
        reportRepository.updateProductLock(productId,lockYn);
    }

    //계정잠금 기능
    public void updateUserLock(Long userId, String lockYn) {
        reportRepository.updateUserLock(userId,lockYn);
    }

    //신고 페이지 카테고리
    public List<CategoryDTO> getCategoriesByRange() {
        return categoryMapper.toDtoList(categoryRepository.findByType("REPORT"));
    }

    //신고 내용 insert 기능
    public void insertReport(ReportDTO report) {
        reportRepository.save(reportMapper.toEntity(report));
    }

}

