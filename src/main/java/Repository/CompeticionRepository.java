package Repository;

import Models.Competicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompeticionRepository extends JpaRepository<Competicion, Integer> {
}