package guru.springframework.repositories;


import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    //Now it returns an Optional type object. Which is not so bad to prevent NullPointerException.
    Optional<Category> findByDescription(String description);
}
