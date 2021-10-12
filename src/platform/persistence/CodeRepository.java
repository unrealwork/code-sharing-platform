package platform.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CodeRepository extends CrudRepository<Code, UUID> {
    List<Code> findByTimeLessThanEqualAndViewsLessThanEqualOrderByTsDesc(Integer time, Integer views, Pageable limit);
}
