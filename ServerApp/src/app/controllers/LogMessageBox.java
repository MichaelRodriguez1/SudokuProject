package app.controllers;

public interface LogMessageBox {

    String LOADING_DATA = "Loading Data";
    String WAITING_CONNECTION = "Waiting connection";
    String CONNECTED_TO_SOCKET = "connected to: ";
    String CLOSED_TEXT = " closed: ";
    String THREAD_PROCESS_FINISHED = "Thread process finished";
    String ACTION_TEXT = "action = ";
    String WAITING_OPPONENT = "waiting opponent";
    String WAITING_PROCESS_DOT = ".";
    String STARTING_MATCH = "starting Match";
    String GETTING_MATCH = "getting match";
    String CHECKING_MATCH = "checking Match";
    String GETTING_STATUS = "getting status";
    String NEXT_GAME_SET = "next game set";
    String SENDING_NEW_BOARD = "sending new board";
    String ENDING_MATCH = "ending match";
    String OPPONENT_HAS_LEFT = "opponent has left the game";
    String SENDING_LAST_STATUS = "sending last status";
    String LEAVING_GAME = "exiting, leaving game";
    String REMOVING_MATCH = "removing match";
    String CLOSING_SOCKET = "closing socket";
}
