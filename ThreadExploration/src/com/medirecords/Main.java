import com.Logic;

public class Main {
    private static String token = "Bearer 6fe1c07c-4a48-4472-894d-511bfbb713df";
    public static void main(String[] args) {
        Logic logic = new Logic(token);
        logic.getFileContent();
    }
}