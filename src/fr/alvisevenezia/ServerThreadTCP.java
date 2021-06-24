package fr.alvisevenezia;

import fr.alvisevenezia.Utils.VERSION;
import fr.alvisevenezia.Web.DiscussPacket.DiscussPacket;
import fr.alvisevenezia.Web.DiscussPacket.DiscussPacketHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerThreadTCP extends Thread{

    private Socket socket;

    public ServerThreadTCP(Socket socket) {
        this.socket = socket;
    }

    public void run(){

        traitement();

    }

    public void traitement(){

        try {

            System.out.println("Connexion avec le client: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            byte[] incomingByte = socket.getInputStream().readAllBytes();

            System.out.println("INCOMING BYTE QUANTITY : "+incomingByte.length);

            DiscussPacketHandler discussPacketHandler = new DiscussPacketHandler(VERSION.BETA,null,0,0);
            DiscussPacket[] receivedDiscussPacket = discussPacketHandler.getDiscussPacketArrayFromByteArray(incomingByte);

            System.out.println(receivedDiscussPacket.length);

            for(int i = 0;i< receivedDiscussPacket.length;i++) {

                System.out.println(i);

                System.out.println("PACKET VERSION : " +receivedDiscussPacket[i].getVersion());
                System.out.println("PACKET ID : " +receivedDiscussPacket[i].getPacketId());
                System.out.println("DATA TYPE: " +receivedDiscussPacket[i].getDataType());
                System.out.println("PACKET SIZE : " +receivedDiscussPacket[i].getSize());
                System.out.println(new String(receivedDiscussPacket[i].getData(), StandardCharsets.UTF_16));
                System.out.println(" ");

            }

            System.out.println(new String(discussPacketHandler.getMergedByteArray(receivedDiscussPacket),StandardCharsets.UTF_16));

            socket.close();

        }catch(Exception e){

            e.printStackTrace();

        }
}
}
