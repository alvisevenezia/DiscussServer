package fr.alvisevenezia;

import fr.alvisevenezia.server.ServerThreadTCP;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    final static int port = 9632;
    final static String IDUserListFilePath = "E:\\Discuss\\Test\\IDUserListFile.txt";

    public static void main(String[] args) {

        try{

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Lancement du serveur");

            while(true){

                Socket socketClient = serverSocket.accept();
                ServerThreadTCP t = new ServerThreadTCP(socketClient,IDUserListFilePath);
                t.start();

            }

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}



