package nl.thecheerfuldev.multitenancy.repository;

import nl.thecheerfuldev.multitenancy.domain.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
}
