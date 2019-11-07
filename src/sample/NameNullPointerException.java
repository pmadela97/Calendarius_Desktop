package sample;

public class NameNullPointerException extends Exception {
    String message;
    NameNullPointerException(String message){

        this.message = message;
    }
    public String getMessage(){
        return message;
    }

}
