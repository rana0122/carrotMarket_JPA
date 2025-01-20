package miniproject.carrotmarket1.mapper;

import miniproject.carrotmarket1.dto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReportDAO {

    //신고 목록 조회(페이징 기능)-- xml의 동적 쿼리로 구현
    List<ReportDTO> getReportListPagination(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            @Param("status") String status,
                                            @Param("size") int size,
                                            @Param("offset") int offset,
                                            @Param("tag") String tag,
                                            @Param("search") String search);

    //신고 목록 조회(필터 기능)-- xml의 동적 쿼리로 구현
    long countFilterReports(@Param("startDate") String startDate,
                            @Param("endDate") String endDate,
                            @Param("status") String status,
                            @Param("tag") String tag,
                            @Param("search") String search);

    //property = "reporter" -> Report엔티티에 있는 User reporter 가져온것
    //column = "reporter_id" -> MySQL root계정 report테이블에 있는 컬럼을 가져온것
    //javaType = User.class -> User클래스에서 가져온 것(User클래스안에 있는 id,name 등등 사용)
    //one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById"))
    //          -> one = @One() : one은 하나만 출력해줄 때 사용
    //          -> select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById
    //              ->
    //신고 상세 조회
    @Select("select * from report where id=#{id}")
    @Results({
            @Result(property = "reporter", column = "reporter_id", javaType = UserDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.UserDAO.selectById")),
            @Result(property = "category", column = "category_id", javaType = CategoryDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.CategoryDAO.selectById")),
            @Result(property = "product", column = "product_id", javaType = ProductDTO.class,
                    one = @One(select = "miniproject.carrotmarket1.dao.MySQL.ProductDAO.findById")),
    })
    ReportDTO getReportById(@Param("id") Long id);

    //신고 처리 상태 변경
    @Update("update report set status=#{status}, resolved_at=#{resolvedAt} where id=#{id}")
    void updateReportStatus(ReportDTO report);

    //게시글 숨김 관리
    @Update("update product set used_yn = #{lockYn} where id=#{productId}")
    void updateProductLock(@Param("productId") Long productId,@Param("lockYn") String lockYn);

    //계정 잠금 기능
    @Update("update user set locked_yn=#{lockYn} where id=#{userId}")
    void updateUserLock(@Param("userId") Long userId,@Param("lockYn") String lockYn);

    //신고 페이지 카테고리
    List<CategoryDTO> getCategoriesByRange();

    //신고 내용 insert 기능
    @Insert("insert into report (product_id, reporter_id,category_id,details,status,created_at) " +
            "values (#{productId},#{reporterId},#{categoryId},#{details},#{status},NOW())")
    void insertReport(ReportDTO report);
}
