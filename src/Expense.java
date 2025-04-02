import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Expense {
    private Map<String, Double> expenses;

    Expense(Map <String, Double> expenses){
        this.expenses = new HashMap<>(expenses);
    }
    
    public void setExpense(String category, double amount){
        expenses.replace(category, amount);
    }

    public double getExpense(String category){
        double amount = expenses.get(category);
        return amount;
    }

    public void resetExpenses(){
        for(String key: expenses.keySet()){
            expenses.replace(key, 0.00);
        }
    }

    public void displayExpenses(){
        DecimalFormat df = new DecimalFormat("0.00");
        double total = 0;
        System.out.println("\n\n\u001B[34m"+
                           "╔════════════════════════════════════════════════════╗\n" +
                           "║     expense Category        ║      Amount          ║\n" +
                           "╠════════════════════════════════════════════════════╣");
        for(String key: expenses.keySet()){
            System.out.printf("║ %-27s ║ %18s $ ║\n", key, df.format(expenses.get(key)));
            total += expenses.get(key);
        }
        System.out.printf("╠════════════════════════════════════════════════════╣\n" +
                          "║    Total                    ║ %18s $ ║\n" +
                          "╚════════════════════════════════════════════════════╝\n" + "\u001B[0m", df.format(total));
    }
}
