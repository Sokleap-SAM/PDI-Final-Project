import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static String category;

    private static String budgetCategory() {
        TextContentHandler.displayBudgetCategory("blue");
        System.out.print("Budget selection: ");
        int selection = getIntInput();
        input.nextLine();
        switch (selection) {
            case 1:
                category = "Salary";
                break;
            case 2:
                category = "Freelance_Income";
                break;
            case 3:
                category = "Investment_Income";
                break;
            case 4:
                category = "Rental_Income";
                break;
            case 5:
                category = "Bonuses";
                break;
            case 6:
                category = "Government_Benefits";
                break;
            case 7:
                category = "Saving";
                break;
            case 8:
                category = "Other_Incomes";
                break;

            default:
                System.out.println("Inavlid option");
                break;
        }
        return category;
    }

    private static String expenseCategory() {
        System.out.print("Expense selection: ");
        int selection = getIntInput();
        input.nextLine();
        switch (selection) {
            case 1:
                category = "Housing";
                break;
            case 2:
                category = "Utilities";
                break;
            case 3:
                category = "Groceries";
                break;
            case 4:
                category = "Transportation";
                break;
            case 5:
                category = "Health_Care";
                break;
            case 6:
                category = "Personal_Care";
                break;
            case 7:
                category = "Education";
                break;
            case 8:
                category = "Insurance";
                break;
            case 9:
                category = "Entertainment";
                break;
            case 10:
                category = "Shopping";
                break;
            case 11:
                category = "Other_Expenses";
                break;

            default:
                System.out.println("Inavlid option");
                break;
        }
        return category;
    }

    private static int getIntInput() {
        while (true) {
            try {
                int value = input.nextInt();
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Input must be a non-negative integer. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a integer.");
                input.nextLine(); 
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                double value = input.nextDouble();
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Input must be a non-negative number. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); 
            }
        }
    }

    public static void main(String[] args) {
        AuthService userAuth = new AuthService();
        BudgetService budgetService;
        ExpenseService expenseService;
        while (true) {
            TextContentHandler.displayWelcomeText("pink");
            TextContentHandler.displayStartedMenu("blue");
            System.out.print("Option: ");
            int option = getIntInput();
            input.nextLine();
            if (option == 1) {
                TextContentHandler.displayRegister("pink");
                System.out.print("Enter name: ");
                String name = input.nextLine();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                System.out.print("Confirm password: ");
                String confirmPassword = input.nextLine();
                if (userAuth.register(name, password, confirmPassword)) {
                    Formatter.formatTextColor("Register Successfully", "green");
                } else {
                    Formatter.formatTextColor("Register failed", "red");
                }
            } else if (option == 2) {
                TextContentHandler.displayLogin("pink");
                System.out.print("Enter name: ");
                String name = input.nextLine();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                if (!userAuth.login(name, password)) {
                    Formatter.formatTextColor("Login failed!", "red");
                } else {
                    System.out.println("Login successfully!");
                    budgetService = new BudgetService(name);
                    expenseService = new ExpenseService(name);
                    int choice = 0;
                    String category;
                    double amount;
                    while (choice != 10) {
                        TextContentHandler.displayFeatureMenu("blue");
                        System.out.print("Choice: ");
                        choice = getIntInput();
                        input.nextLine();
                        switch (choice) {
                            case 1:
                                category = budgetCategory();
                                System.out.print("Add amount: ");
                                amount = getDoubleInput();
                                input.nextLine();
                                budgetService.addBudget(category, amount);
                                break;
                            case 2:
                                category = budgetCategory();
                                System.out.print("New budget amount: ");
                                amount = getDoubleInput();

                                budgetService.editBudget(category, amount);
                                break;
                            case 3:
                                budgetService.deleteAllBudgets();
                                break;
                            case 4:
                                category = expenseCategory();
                                System.out.print("Add expense amount: ");
                                amount = getDoubleInput();
                                input.nextLine();
                                expenseService.addExpense(category, amount);
                                break;
                            case 5:
                                category = expenseCategory();
                                System.out.print("New Expense amount: ");
                                amount = getDoubleInput();
                                input.nextLine();
                                expenseService.editExpense(category, amount);
                                break;
                            case 6:
                                expenseService.deleteAllExpenses();
                                break;
                            case 7:
                                budgetService.viewBudgetSummary();
                                break;
                            case 8:
                                expenseService.viewExpenseSummary();
                                break;
                            case 9:
                                budgetService.viewBudgetSummary();
                                expenseService.viewExpenseSummary();
                                break;
                            case 10:
                                break;
                            default:
                                Formatter.formatTextColor("Invalid choice!", "red");
                                break;
                        }
                    }
                }
            } else if (option == 3) {
                TextContentHandler.displayThankYouText("pink");
                System.exit(0);
            } else {
                Formatter.formatTextColor("Invalid option", "red");
            }
        }
    }
}
