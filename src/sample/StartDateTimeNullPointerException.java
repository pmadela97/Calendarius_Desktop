package sample;
public class StartDateTimeNullPointerException extends Exception {
        String message;
        StartDateTimeNullPointerException(String message){

            this.message = message;
        }
        public String getMessage(){
            return message;
        }

    }


