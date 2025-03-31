import java.util.Map;

public class ExpenseService {
    Expense expense;
    private String currentUser;

    ExpenseService(String username){
        this.currentUser = username;
        Map<String, Double> dbExpenses = DataManagement.getUserExpense(username); 
        this.expense = new Expense(dbExpenses);
    }

    public void addExpense(String category, double newAmount){
        double updatedAmount = newAmount + expense.getExpense(category);
        double roundedAmount = Formatter.formatCurrency(updatedAmount);
        expense.setExpense(category, roundedAmount);
        DataManagement.updateExpense(currentUser, category, roundedAmount);
    }

    public void editExpense(String category, double newAmount){
        double roundedAmount = Formatter.formatCurrency(newAmount);
        expense.setExpense(category, roundedAmount);
        DataManagement.updateExpense(currentUser, category, roundedAmount);
    }

    public void deleteAllExpenses(){
        DataManagement.resetAllExpenses(currentUser);
        expense.resetExpenses();
    }

    public void viewExpenseSummary() {
        System.out.println("Expense summary: ");
        expense.displayExpenses();
    }
}
