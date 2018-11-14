package me.cardtable.serverapp.connectionHandling;


import me.cardtable.serverapp.Client;
import me.cardtable.serverapp.connectionHandling.messageThreads.MessageReceiveThread;
import me.cardtable.serverapp.connectionHandling.messageThreads.MessageSendThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clients {

    private static List<Client> clients=new ArrayList<>();

    public static boolean addClient(Client client){
        if(!clients.contains(client)){
            clients.add(client);
            return true;
        }else{
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static boolean removeClient(Client client){
        if(clients.contains(client)){
            try {
                client.disconnect();
                clients.remove(client);
            } catch (IOException e) {
                System.err.println("The client could not be removed!");
                e.printStackTrace();
            }
            return true;
        }else{
            return false;
        }
    }

    public static List<Client> getClients() {
        return clients;
    }

}
