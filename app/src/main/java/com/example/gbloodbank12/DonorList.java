package com.example.gbloodbank12;


public class DonorList {

    //public String sender;
    public  String message;
    public  String date;


    public DonorList( String message, String date){
       // this.sender = sender;
        this.message = message;
        this.date = date;

    }

//    public String getSender(){
//        return  sender;
//    }

    public String getMessage(){
        return  message;
    }

    public String getDate(){
        return  date;
    }
}
