/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrabbleEGC.net;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Vector;
import scrabbleEGC.ScrabbleConstants;

public class ScrabbleMultiplayerServer implements Runnable {

    private final Vector<Connection> connections;
    ServerSocket serverSocket;
    private int MaxConexiones;
    private Socket clientSocket;

    /**
     * Constructor de un escucha en el servidor para hasta max jugadores
     * @param max
     * @throws IOException 
     */
    public ScrabbleMultiplayerServer(int max) throws IOException {
        connections = new Vector<Connection>();

        try {
            serverSocket = new ServerSocket(ScrabbleConstants.PUERTO);
        } catch (IOException ex) {
            System.out.println("error thread de server" + ex.getMessage());
            throw ex;

        }
        MaxConexiones = max;
        // System.out.println("cuarto para " + MaxConexiones);
        ConnectionListener connectionListener = new ConnectionListener(connections, MaxConexiones);

        //el hilo encargado de hacer el broadcast
        connectionListener.start();
        System.out.println("Servidor arriba");

    }

    @Override
    public void run() {

        while (true) {
            if (connections.size() < MaxConexiones) {
                try {
                    
                    clientSocket = serverSocket.accept();
                    // System.out.println("aceptado");
                } catch (IOException ex) {
                    System.out.println("error socket" + ex);
                }
                System.out.println("Cliente escuchado");

                // listen to client in a separate thread
                Connection connection = new Connection(clientSocket, connections.size());
                connections.add(connection);
                connection.start();
            }
        }

    }

    public void setMaxConnections(int maxConnections) {

        MaxConexiones = maxConnections;
    }

}
