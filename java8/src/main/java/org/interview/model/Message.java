package org.interview.model;

public class Message {
    private String id;
    private String data;

    public Message(){

    }

    public Message(String id, String data){
        this.id = id;
        this.data = data;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setData(String data){
        this.data = data;
    }

    public String getData(){
        return this.data;
    }

    public static class MessageBuilder{
        private String id;
        private String data;

        public MessageBuilder(String id, String data){
            this.id = id;
            this.data = data;
        }


    }

}
