import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map <String, User> users = new HashMap<>();
    AuthService(){
        users = DataManagement.getUserDetail();
    }
    
    public boolean register(String name, String password, String confirmPassword){
        if(!password.equals(confirmPassword)){
            System.out.println("Password and Confirm password isn't the same! ");
            return false;
        }
        else{
            if(users.containsKey(name)){
                System.out.println("User already exist!");
                return false;
            }
            users.put(name, new User(name, password));
            String hashName = hashSHA256(name);
            String hashPassword = hashSHA256(password);
            DataManagement.addUserDetail(hashName, hashPassword);
            DataManagement.initialBudget(name);
            DataManagement.initialExpense(name);
            return true;
        }
    }

    public boolean login(String name, String password){
        String hashName = hashSHA256(name);
        String hashPassword = hashSHA256(password);
        if(!users.containsKey(hashName)){
            System.out.println("Invalid username!");
            return false;
        }
        else{
            if(!users.get(hashName).getPassword().equals(hashPassword)){
                System.out.println(users.get(hashName).getPassword());
                System.out.println("Invalid password!");
                return false;
            }
            return true;
        }
    }

    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: SHA-256 algorithm not found.");
            return null;
        }
    }

}
