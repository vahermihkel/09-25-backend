package ee.mihkel.backend.repository;

import ee.mihkel.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

// CrudRepository    .findAll()   .save()
// PagingAndSortingRepository
// JpaRepository     .flush()

public interface CategoryRepository extends JpaRepository<Category, String> {
}
