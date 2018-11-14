package me.cardtable.serverapp;

import me.cardtable.serverapp.connectionHandling.Clients;
import me.cardtable.serverapp.connectionHandling.Message;
import me.cardtable.serverapp.connectionHandling.messageThreads.MessageReceiveThread;
import me.cardtable.serverapp.connectionHandling.messageThreads.MessageSendThread;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server{

    ServerSocket server;
    boolean ServerOn=true;

    public Server(int port) {
        try {
            server=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        while(ServerOn){
            try{
                Socket clientSocket =server.accept();
                MessageReceiveThread mrt=new MessageReceiveThread(clientSocket);
                MessageSendThread mst=new MessageSendThread(clientSocket);
                Client client=new Client(clientSocket,mst,mrt);
                Clients.addClient(client);
                mst.start();
                mrt.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            for(Client client:Clients.getClients()){
                Clients.removeClient(client);
            }
            server.close();
            System.out.println("Server Stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Server(1234);
    }

}
