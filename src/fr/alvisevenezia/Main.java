package fr.alvisevenezia;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Main {

    final static int port = 9632;

    public static void main(String[] args) {
        try{

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Lancement du serveur");

            while(true){

                Socket socketClient = serverSocket.accept();
                ServerThreadTCP t = new ServerThreadTCP(socketClient);
                t.start();

            }

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}



