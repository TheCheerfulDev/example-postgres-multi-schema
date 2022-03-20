package nl.thecheerfuldev.multitenancy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.thecheerfuldev.multitenancy.api.dto.BoardGameDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer minPlayers;
    private Integer maxPlayers;

    public BoardGame(String name, Integer minPlayers, Integer maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public static BoardGame fromDto(final BoardGameDto boardGameDto) {
        return new BoardGame(boardGameDto.getName(), boardGameDto.getMinPlayers(), boardGameDto.getMaxPlayers());
    }

}
