package app.runner;

import app.Constants;
import app.controllers.Controller;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class Runner {

    public static void main(String[] args) {
        try {
            new Controller(new ServerSocket(Constants.SERVER_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
