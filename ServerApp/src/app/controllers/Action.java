package app.controllers;

public interface Action{

    String SEARCH_MATCH = "searchMatch";
    String CHECK_BOARD = "checkBoard";
    String EXIT = "exit";
    String EXIT_MATCH = "btnExitMatch";

    //Responses
    String END_MATCH = "endMatch";
    String CONTINUE_MATCH = "continueMatch";
    String LOST = "lost";
    String WON = "won";
    String OPPONENT_IS_GONE = "opponentIsGone";
    String START_MATCH = "startMatch";
}
