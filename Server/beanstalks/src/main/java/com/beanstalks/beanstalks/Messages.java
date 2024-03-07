// package com.beanstalks.beanstalks;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @Entity
// public class Messages {
//     private @Id @GeneratedValue int messageID;
//     private int senderID;
//     private int recieverID;
//     private string Message;

//     public SendMessages(int senderID, int recieverID, string Message) {
//         this.senderID = senderID;
//         this.recieverID = recieverID;
//         this.Message = Message;
//     }

//     public int getSenderID() {
//         return (this.senderID);
//     }

//     public int getRecieverID() {
//         return (this.recieverID);
//     }

//     public string getMessage() {
//         return (this.Message);
//     }

//     public int getMessageId() {
//         return (this.messageID);
//     }

//     @Override
//     public string toString() {
//         return "SendMessages{" +
//                 "messageID=" + messageID +
//                 ", senderID=" + senderID +
//                 ", recieverID=" + recieverID +
//                 ", Message='" + Message + '\'' +
//                 '}';
//     }
// }
