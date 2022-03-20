package nl.thecheerfuldev.multitenancy.api;

import lombok.extern.slf4j.Slf4j;
import nl.thecheerfuldev.multitenancy.domain.BoardGame;
import nl.thecheerfuldev.multitenancy.repository.BoardGameRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class BoardGameController {

    private final BoardGameRepository boardGameRepository;

    public BoardGameController(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

    @PostMapping("/boardgames")
    public BoardGame post(@RequestBody BoardGame boardGame) {
        log.info("Received Boardgame: {}", boardGame);
        return this.boardGameRepository.save(boardGame);
    }

    @GetMapping("/boardgames/{id}")
    public ResponseEntity<BoardGame> get(@PathVariable("id") long id) {
        Optional<BoardGame> byId = this.boardGameRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(byId.get());
    }

    @GetMapping("/boardgames")
    public List<BoardGame> get() {
        return this.boardGameRepository.findAll();
    }
}
