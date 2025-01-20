package miniproject.carrotmarket1.repository.MySQL;

import lombok.RequiredArgsConstructor;
import miniproject.carrotmarket1.mapper.ReportDAO;
import miniproject.carrotmarket1.dto.CategoryDTO;
import miniproject.carrotmarket1.dto.ReportDTO;
import miniproject.carrotmarket1.repository.ReportRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
@Repository
@RequiredArgsConstructor
public class MySQLReportRepository implements ReportRepository {
    private final ReportDAO reportDAO;

    //신고 목록 조회(페이징 기능)
    @Override
    public List<ReportDTO> getReportListPagination(String startDate, String endDate, String status, int size, int offset, String tag, String search) {
        return reportDAO.getReportListPagination(startDate,endDate,status,size,offset,tag,search);
    }
    //신고 목록 조회(필터 기능)
    @Override
    public long countFilterReports(String startDate, String endDate, String status, String tag, String search) {
        return reportDAO.countFilterReports(startDate,endDate,status,tag,search);
    }

    //신고 상세 조회
    @Override
    public ReportDTO getReportById(Long id) {
        return reportDAO.getReportById(id);
    }
    //신고 처리 상태 변경
    @Override
    public void updateReportStatus(ReportDTO report){
        reportDAO.updateReportStatus(report);
    }


    //게시글 숨김 관리
    @Override
    public void updateProductLock(Long productId, String lock) {
        reportDAO.updateProductLock(productId,lock);
    }

    //계정 잠금 기능
    @Override
    public void updateUserLock(Long userId, String lockYn) {
        reportDAO.updateUserLock(userId,lockYn);
    }
    //신고 페이지 카테고리
    @Override
    public List<CategoryDTO> getCategoriesByRange() {
        return reportDAO.getCategoriesByRange();
    }

    //신고 내용 insert 기능
    @Override
    public void insertReport(ReportDTO report) {
        reportDAO.insertReport(report);
    }

}*/
