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
            Formatter.formatTextColor("Register failed: Password and Confirm password isn't the same!", "yellow");
            return false;
        }
        else{
            if(users.containsKey(name)){
                Formatter.formatTextColor("Register failed: User already exist!", "yellow"); 
                return false;
            }
            String hashName = hashSHA256(name);
            String hashPassword = hashSHA256(password);
            users.put(hashName, new User(hashName, hashPassword));
            if(DataManagement.addUserDetail(hashName, hashPassword) && DataManagement.initialBudget(name) && DataManagement.initialExpense(name)){
                DataManagement.addUserDetail(hashName, hashPassword);
                return true;
            }
            return false;
        }
    }

    public boolean login(String name, String password){
        String hashName = hashSHA256(name);
        String hashPassword = hashSHA256(password);
        if(!users.containsKey(hashName)){
            Formatter.formatTextColor("Login failed: Incorrect username!", "yellow"); 
            return false;
        }
        else{
            if(!users.get(hashName).getPassword().equals(hashPassword)){
                Formatter.formatTextColor("Login failed: Incorrect password!", "yellow"); 
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
            Formatter.formatTextColor("Error: encryption failed!", "red");
            return null;
        }
    }

}
