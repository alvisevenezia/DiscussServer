package fr.alvisevenezia;

import fr.alvisevenezia.Utils.VERSION;
import fr.alvisevenezia.Web.DiscussPacket.DiscussPacket;
import fr.alvisevenezia.Web.DiscussPacket.DiscussPacketHandler;
import fr.alvisevenezia.encryption.symmetrical.SymmetricalEncryptedMessage;

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

            System.out.print("BYTE MSG : ");

            for(byte b : incomingByte){

                System.out.print(b);

            }

            System.out.println('\n');

            System.out.println("INCOMING BYTE QUANTITY : "+incomingByte.length);

            DiscussPacketHandler discussPacketHandler = new DiscussPacketHandler(VERSION.BETA,null,0,0);
            DiscussPacket[] receivedDiscussPacket = discussPacketHandler.getDiscussPacketArrayFromByteArray(incomingByte);

            System.out.println("RECIEVED PACKET QUANTITY : "+receivedDiscussPacket.length);

            for(int i = 0;i< receivedDiscussPacket.length;i++) {

                System.out.println(i);

                System.out.println("PACKET VERSION : " +receivedDiscussPacket[i].getVersion());
                System.out.println("PACKET ID : " +receivedDiscussPacket[i].getPacketId());
                System.out.println("DATA TYPE: " +receivedDiscussPacket[i].getDataType());
                System.out.println("PACKET SIZE : " +receivedDiscussPacket[i].getSize());
                System.out.println("ACTUAL PACKET SIZE : " +receivedDiscussPacket[i].getActualSize());

                System.out.print("PACKET DATA : ");

                int id = 0;

                for(byte b : receivedDiscussPacket[i].getData()){

                    System.out.print(b);
                    id++;
                }

                System.out.println('\n');

                byte[] array = discussPacketHandler.getMergedByteArray(receivedDiscussPacket);

                System.out.print("MERGED PACKET DATA : ");

                id = 0;

                for(byte b : array){

                    System.out.print(b);
                    id++;

                }

                System.out.println('\n');



            }

            System.out.println('\n');

            System.out.println("MSG : " +SymmetricalEncryptedMessage.getDecryptedMessage(discussPacketHandler.getMergedByteArray(receivedDiscussPacket),"caca"));


            socket.close();

        }catch(Exception e){

            e.printStackTrace();

        }
}
}
