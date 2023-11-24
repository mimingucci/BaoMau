package mimingucci.baomau.repository;

import mimingucci.baomau.entity.BaoMau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaoMauRepository extends JpaRepository<BaoMau, Integer> {
}
