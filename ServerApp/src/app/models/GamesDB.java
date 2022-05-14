package app.models;

import app.Constants;
import app.models.match.Difficulty;
import app.models.match.Game;
import util.RandomNumber;
import util.stack.Stack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class GamesDB {

    private final ArrayList<Game> games;

    /**
     * crea el objeto con una lista de partidas vaciás
     */
    public GamesDB() {
        games = new ArrayList<>();
    }

    public void addGame(Game game){
        games.add(game);
    }

    /**
     * Busca juegos aleatoriamente con la dificultad especificada y
     * crea una pila con los mismos. La cantidad de juegos que se asignan
     * a la pila depende del valor de juegos por partida en el sistema
     * @param difficulty dificultad de las partidas
     * @return pila de juegos con la respectiva dificultad
     * @see Constants
     * @see RandomNumber
     */
    public Stack<Game> getMatchGames(Difficulty difficulty){
        List<Game> gameList = getGamesByDifficulty(difficulty);
        Stack<Game> matchGames = new Stack<>(Game::compareTo);
        RandomNumber
                .generateWithoutRepeat(gameList.size(), Constants.GAMES_PER_MATCH)
                .forEachRemaining(index -> matchGames.push(gameList.get(index)));
        return matchGames;
    }

    /**
     * Filtra los juegos de la base de datos con la respectiva dificultad y crea una lista
     * de todos los juegos con dicha dificultad
     * @param difficulty  dificultad filtro
     * @return lista con todos los juegos de la misma dificultad
     */
    private List<Game> getGamesByDifficulty(Difficulty difficulty){
        return games
                .stream()
                .filter(game -> game.getDifficulty().equals(difficulty))
                .collect(Collectors.toList());
    }
}
