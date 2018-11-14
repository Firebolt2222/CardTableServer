package me.cardtable.serverapp.connectionHandling;

public class Message {

    public byte[] message;
    final static int messageLength=4;

    public Message(byte[] message) {
        if(message.length==messageLength){
            this.message = message;
        }else{
            System.out.println("Illegal Message Lengths!");
            throw new IllegalArgumentException();
        }
    }

    public void readMessage(){
        for(int i=0;i<messageLength;i++){
            System.out.print("The Argument at message["+i+"] is ");
            switch (message[i]){
                case 1:
                    System.out.print(" m1");
                    break;
                case 2:
                    System.out.print(" anything");
                    break;
                default:
                    System.out.print(message[i]);
            }
            System.out.println();
        }
    }

}
