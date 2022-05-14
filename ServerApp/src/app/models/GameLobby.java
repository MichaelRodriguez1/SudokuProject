package app.models;

import app.models.match.Difficulty;
import app.models.match.Game;
import app.models.match.Match;
import app.models.match.Player;
import util.stack.Stack;

import java.util.ArrayList;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class GameLobby {

    private GamesDB dataBase;
    private final ArrayList<Match> matches;

    public GameLobby() {
        matches = new ArrayList<>();
    }

    public void setDataBase(GamesDB dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Busca una partida para el jugador, la cual debe ser del la misma dificultad
     * y en la que no haya un jugador con el mismo nombre si la encuentra asigna el
     * jugador especificado a la partida encontrada, si no encuetra tal partida crea
     * una, aigna al jugador como su host y la agrega al lobby
     * @param player jugador a emparejar
     * @param difficulty dificultad de la partida buscada
     * @return la partida en la que se alojó el jugador
     */
    public synchronized Match pairOff(Player player, Difficulty difficulty){
        Match match = matches.stream()
                .filter(m -> m.getDifficulty().equals(difficulty))
                .filter(Match::isAvailable)
                .filter(match1 -> !match1.isInThisMatch(player))
                .findFirst().orElse(null);
        if (match == null) {
            Stack<Game> games = dataBase.getMatchGames(difficulty);
            match = new Match(games, difficulty, player);
            matches.add(match);
        }else match.setPlayer2(player);
        return match;
    }

    /**
     * remueve la partida especificada del lobby
     * @param match partida a eliminar
     */
    public synchronized void remove(Match match) {
        this.matches.remove(match);
    }
}
