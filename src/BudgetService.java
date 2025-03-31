import java.util.Map;

public class BudgetService {
    Budget budget;
    private String currentUser;

    BudgetService(String username){
        this.currentUser = username;
        Map<String, Double> dbBudgets = DataManagement.getUserBudget(username); 
        this.budget = new Budget(dbBudgets);
    }

    public void addBudget(String category, double newAmount){
        double updatedAmount = newAmount + budget.getBudget(category);
        double roundedAmount = Formatter.formatCurrency(updatedAmount);
        budget.setBudget(category, roundedAmount);
        DataManagement.updateBudget(currentUser, category, roundedAmount);
    }

    public void editBudget(String category, double newAmount){
        double roundedAmount = Formatter.formatCurrency(newAmount);
        budget.setBudget(category, roundedAmount);
        DataManagement.updateBudget(currentUser, category, roundedAmount);
    }

    public void deleteAllBudgets(){
        DataManagement.resetAllBudgets(currentUser);
        budget.resetBudgets();
    }

    public void viewBudgetSummary() {
        budget.displayBudgets();
    }
}
