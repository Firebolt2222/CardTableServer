package me.cardtable.serverapp.connectionHandling;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CommunicationSystem {

    public final static byte GIVE_CARD=0;
    public final static byte RECEIVE_CARD=1;
    public final static byte PLAY_CARD=2;

    public static void sendMessage(Socket client, byte[] bytes, byte actionByte) throws IOException{
        DataInputStream dataInputStream=new DataInputStream(Clients.getClientInputStream(client));
        DataOutputStream dataOutputStream=new DataOutputStream(Clients.getClientOutputStream(client));
        int n=bytes.length;
        dataOutputStream.writeByte(n);
        dataOutputStream.writeByte(actionByte);
        byte sum=0;
        for(byte b:bytes){
            dataOutputStream.write(b);
            sum+=b;
        }
        dataOutputStream.writeByte(sum);
        dataOutputStream.writeByte(n);
        dataInputStream.close();
        dataOutputStream.close();
    }

    public static byte[] receiveMessage(Socket client) throws IOException{
        DataInputStream dataInputStream=new DataInputStream(Clients.getClientInputStream(client));
        DataOutputStream dataOutputStream=new DataOutputStream(Clients.getClientOutputStream(client));
        byte b=dataInputStream.readByte();
        byte actionByte=dataInputStream.readByte();
        byte c=0;
        byte controlSum=0;
        byte[] message=new byte[b];
        while(c<b){
            message[c]=dataInputStream.readByte();
            controlSum+=message[c];
            c++;
        }
        if(controlSum==dataInputStream.readByte()){
            dataInputStream.close();
            dataOutputStream.close();
            return message;
        }else{
            dataInputStream.close();
            dataOutputStream.close();
            System.err.println("Wrong message received!");
            return null;
        }

    }

}
