package calculator;
public class ErrorHandler {
    public void handle(Exception e){
        System.err.println("Error occurred: " + e.getMessage());
    }
}
