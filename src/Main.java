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
                    Formatter.formatTextColor("Input must be a non-negative integer. Please try again.", "yellow");
                }
            } catch (InputMismatchException e) {
                Formatter.formatTextColor("Invalid input. Please enter a integer!\nInput: ", "yellow");
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
                    Formatter.formatTextColor("Input must be a non-negative number. Please try again.", "yellow");
                }
            } catch (InputMismatchException e) {
                Formatter.formatTextColor("Invalid input. Please enter a number!", "yellow");
                input.nextLine(); 
            }
        }
    }

    public static void pressEnterToContinue(){
        System.out.println("\nPress enter to continue");
        input.nextLine();
        System.out.println("\033c");
    }

    public static void main(String[] args) {
        AuthService userAuth = new AuthService();
        BudgetService budgetService;
        ExpenseService expenseService;
        TextContentHandler.displayWelcomeText("pink");
        pressEnterToContinue();
        TextContentHandler.displayLoading("Loading to register/login page");
        while (true) {
            TextContentHandler.displayStartedMenu("blue");
            System.out.print("Option: ");
            int option = getIntInput();
            input.nextLine();
            if (option == 1) {
                TextContentHandler.displayLoading("Loading to register page");
                TextContentHandler.displayRegister("pink");
                System.out.print("Enter name: ");
                String name = input.nextLine();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                System.out.print("Confirm password: ");
                String confirmPassword = input.nextLine();
                if (userAuth.register(name, password, confirmPassword)) {
                    Formatter.formatTextColor("Register Successfully", "green");
                }
            } else if (option == 2) {
                TextContentHandler.displayLoading("Loading to login page");
                TextContentHandler.displayLogin("pink");
                System.out.print("Enter name: ");
                String name = input.nextLine();
                System.out.print("Enter password: ");
                String password = input.nextLine();
                if (userAuth.login(name, password)) {
                    System.out.println("Login successfully!");
                    TextContentHandler.displayLoading("Loading to home page");
                    int choice = 0;
                    String category;
                    double amount;
                    budgetService = new BudgetService(name);
                    expenseService = new ExpenseService(name);
                    while (choice != 10) {
                        TextContentHandler.displayFeatureMenu("blue");
                        System.out.print("Choice: ");
                        choice = getIntInput();
                        input.nextLine();
                        System.out.println("\033c");
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
                                TextContentHandler.displayLoading("Loading to edit expense page");
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
                                TextContentHandler.displayLoading("Logging out");
                                break;
                            default:
                                Formatter.formatTextColor("Invalid choice!", "red");
                                break;
                        }
                        pressEnterToContinue();
                        TextContentHandler.displayLoading("Loading back to homepage");
                    }
                }
            } else if (option == 3) {
                System.out.println("\033c");
                TextContentHandler.displayThankYouText("pink");
                System.exit(0);
            } else {
                Formatter.formatTextColor("Invalid option", "red");
            }
            pressEnterToContinue();
            TextContentHandler.displayLoading("Loading back to register/login page");
        }
    }
}
