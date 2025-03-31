import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Budget {
    private Map<String, Double> budgets;

    Budget(Map <String, Double> budgets){
        this.budgets = new HashMap<>(budgets);
    }
    
    public void setBudget(String category, double amount){
        budgets.replace(category, amount);
    }

    public double getBudget(String category){
        double amount = budgets.get(category);
        return amount;
    }

    public void resetBudgets(){
        for(String key: budgets.keySet()){
            budgets.replace(key, 0.00);
        }
    }

    public void displayBudgets(){
        DecimalFormat df = new DecimalFormat("0.00");
        double total = 0;
        System.out.println("\u001B[34m"+
                           "╔════════════════════════════════════════════════════╗\n" +
                           "║     Budget Category         ║      Amount          ║\n" +
                           "╠════════════════════════════════════════════════════╣");
        for(String key: budgets.keySet()){
            System.out.printf("║ %-27s ║ %18s $ ║\n", key, df.format(budgets.get(key)));
            total += budgets.get(key);
        }
        System.out.printf("╠════════════════════════════════════════════════════╣\n" +
                           "║    Total                    ║ %18s $ ║\n" +
                           "╚════════════════════════════════════════════════════╝\n" + "\u001B[0m", df.format(total));
    }
}
