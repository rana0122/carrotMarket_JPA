package miniproject.carrotmarket1.repository;

import miniproject.carrotmarket1.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // type별 카테고리 조회
    List<Category> findByType(String type);
    Optional<Category> findById(Long categoryId);

}
