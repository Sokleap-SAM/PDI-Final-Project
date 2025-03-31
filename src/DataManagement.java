import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataManagement {
    private static final String DB_URL = "jdbc:sqlite:src/budgets.db";
    private static final String FILEPATH = "src/UserData";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void insertBudget(PreparedStatement preparedStat, String username, String category) throws SQLException {
        preparedStat.setString(1, username);
        preparedStat.setString(2, category);
        preparedStat.executeUpdate();
    }

    public static void initialBudget(String username) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS budgets ("
                + "username TEXT NOT NULL,"
                + "category TEXT NOT NULL,"
                + "amount REAL NOT NULL DEFAULT 0.00,"
                + "CHECK(amount = ROUND(amount,2)))";
        String insertDataSQL = "INSERT INTO budgets (username, category) VALUES (?, ?)";
        try (Connection connection = getConnection();) {

            connection.createStatement().execute(createTableSQL);

            try (PreparedStatement preparedStat = connection.prepareStatement(insertDataSQL)) {
                insertBudget(preparedStat,username, "Salary");
                insertBudget(preparedStat, username, "Freelance_Income");
                insertBudget(preparedStat, username, "Investment_Income");
                insertBudget(preparedStat, username, "Rental_Income");
                insertBudget(preparedStat, username, "Bonuses");
                insertBudget(preparedStat, username, "Government_Benefits");
                insertBudget(preparedStat, username, "Saving");
                insertBudget(preparedStat, username, "Other_Incomes");
            }

            System.out.println("Budget Database initialized successfully!");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static void updateBudget(String username, String category, double amount) {
        String updateSQL = "UPDATE budgets SET amount = ROUND(?, 2) WHERE username = ? AND category = ?";
        try (Connection connection = getConnection();
                PreparedStatement updateStat = connection.prepareStatement(updateSQL);) {

            updateStat.setDouble(1, amount);
            updateStat.setString(2, username);
            updateStat.setString(3, category);
            updateStat.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Update budget failed: " + e.getMessage());
        }
    }

    public static Map<String, Double> getUserBudget(String username) {
        Map<String, Double> budgetMap = new HashMap<>();
        String query = "SELECT category, amount FROM budgets WHERE username = ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                budgetMap.put(rs.getString("category"), rs.getDouble("amount"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching budget: " + e.getMessage());
        }

        return budgetMap;
    }

    public static void resetAllBudgets(String username) {
        String sql = "UPDATE budgets SET amount = 0 WHERE username = ?";
        
        try (Connection connection = getConnection();
            PreparedStatement resetStat = connection.prepareStatement(sql)) {
            
            resetStat.setString(1, username);
            resetStat.executeUpdate();
            
            System.out.println("Delete all budgets successfully");
        } catch (SQLException e) {
            System.err.println("Deleting budgets failed: " + e.getMessage());
        }
    }

    private static void insertExpense(PreparedStatement preparedStat, String username, String category) throws SQLException {
        preparedStat.setString(1, username);
        preparedStat.setString(2, category);
        preparedStat.executeUpdate();
    }

    public static void initialExpense(String username) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS expenses ("
                + "username TEXT NOT NULL,"
                + "category TEXT NOT NULL,"
                + "amount REAL NOT NULL DEFAULT 0.00,"
                + "CHECK(amount = ROUND(amount,2)))";

        String insertDataSQL = "INSERT INTO expenses (username, category) VALUES (?, ?)";
        try (Connection connection = getConnection()) {

            connection.createStatement().execute(createTableSQL);

            try (PreparedStatement preparedStat = connection.prepareStatement(insertDataSQL)) {
                insertExpense(preparedStat, username, "Housing");
                insertExpense(preparedStat, username, "Utilities");
                insertExpense(preparedStat, username, "Groceries");
                insertExpense(preparedStat, username, "Transportation");
                insertExpense(preparedStat, username, "Health_Care");
                insertExpense(preparedStat, username, "Personal_Care");
                insertExpense(preparedStat, username, "Education");
                insertExpense(preparedStat, username, "Insurance");
                insertExpense(preparedStat, username, "Entertainment");
                insertExpense(preparedStat, username, "Shopping");
                insertExpense(preparedStat, username, "Other_Expenses");
            }

        } catch (SQLException e) {
            System.err.println("Initializing database failed: " + e.getMessage());
        }
    }

    public static void updateExpense(String username, String category, double amount) {
        String updateSQL = "UPDATE expenses SET amount = ROUND(?, 2) WHERE username = ? AND category = ?";
        try (Connection connection = getConnection();
                PreparedStatement updateStat = connection.prepareStatement(updateSQL);) {

            updateStat.setDouble(1, amount);
            updateStat.setString(2, username);
            updateStat.setString(3, category);
            updateStat.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Update expense failed: " + e.getMessage());
        }
    }

    public static Map<String, Double> getUserExpense(String username) {
        Map<String, Double> expenses = new HashMap<>();
        String query = "SELECT category, amount FROM expenses WHERE username = ?";

        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                expenses.put(rs.getString("category"), rs.getDouble("amount"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching expenses: " + e.getMessage());
        }

        return expenses;
    }

    public static void resetAllExpenses(String username) {
        String sql = "UPDATE expenses SET amount = 0 WHERE username = ?";
        
        try (Connection connection = getConnection();
            PreparedStatement resetStat = connection.prepareStatement(sql)) {
            
            resetStat.setString(1, username);
            resetStat.executeUpdate();
            
            System.out.println("Successfully delete expenses");
        } catch (SQLException e) {
            System.err.println("Error resetting expenses: " + e.getMessage());
        }
    }



    public static void addUserDetail(String username, String password){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILEPATH, true));
            bw.write(username + ":" + password + "\n");
            bw.close();
        } catch (IOException e) {
            System.err.println("Store user's data failed: " + e.getMessage());
        }
    }

    public static Map<String, User> getUserDetail(){
        File file = new File(FILEPATH);
        Map <String, User> user = new HashMap<>();
        if(file.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
                String line;
                while((line = br.readLine()) != null){
                    String[] parts = line.split(":");
                    user.put(parts[0], new User(parts[0], parts[1]));
                }
                br.close();
            } catch (IOException e) {
                System.err.println("Get user's data failed " + e.getMessage());
            }
            return user;
        }
        System.out.println("No user exist yet");
        return user;
    }

}
