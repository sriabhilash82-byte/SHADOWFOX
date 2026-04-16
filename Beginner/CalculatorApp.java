import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class CalculatorApp {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("     Enhanced Console Calculator");
        System.out.println("===================================");

        boolean running = true;
        while (running) {
            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Basic Calculator (+, -, *, /)");
            System.out.println("2. Scientific Calculator");
            System.out.println("3. Unit Conversion");
            System.out.println("4. View History");
            System.out.println("5. Clear History");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    basicCalculator();
                    break;

                case 2:
                    scientificCalculator();2
                    break;

                case 3:
                    unitConversion();
                    break;

                case 4:
                    showHistory();
                    break;

                case 5:
                    clearHistory();
                    break;

                case 0:
                    System.out.println("Thank you for using Calculator!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }

        sc.close();
    }

    // ---------------- BASIC CALCULATOR ----------------
    public static void basicCalculator() {
        System.out.println("\n--- BASIC CALCULATOR ---");

        System.out.print("Enter first number: ");
        BigDecimal num1 = getBigDecimalInput();

        System.out.print("Enter operator (+ - * /): ");
        String operator = sc.next();

        System.out.print("Enter second number: ");
        BigDecimal num2 = getBigDecimalInput();

        BigDecimal result;

        try {
            switch (operator) {
                case "+":
                    result = num1.add(num2);
                    break;

                case "-":
                    result = num1.subtract(num2);
                    break;

                case "*":
                    result = num1.multiply(num2);
                    break;

                case "/":
                    if (num2.compareTo(BigDecimal.ZERO) == 0) {
                        System.out.println("Error: Division by zero not allowed!");
                        return;
                    }
                    result = num1.divide(num2, 4, RoundingMode.HALF_UP);
                    break;

                default:
                    System.out.println("Invalid operator!");
                    return;
            }

            System.out.println("Result = " + result);
            history.add(num1 + " " + operator + " " + num2 + " = " + result);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    // ---------------- SCIENTIFIC CALCULATOR ----------------
    public static void scientificCalculator() {
        System.out.println("\n--- SCIENTIFIC CALCULATOR ---");
        System.out.println("1. Square Root");
        System.out.println("2. Power (Exponent)");
        System.out.println("3. Modulus (%)");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                System.out.print("Enter number: ");
                double num = getDoubleInput();

                if (num < 0) {
                    System.out.println("Error: Cannot find sqrt of negative number!");
                    return;
                }

                double sqrt = Math.sqrt(num);
                System.out.println("Square Root = " + sqrt);

                history.add("sqrt(" + num + ") = " + sqrt);
                break;

            case 2:
                System.out.print("Enter base: ");
                double base = getDoubleInput();

                System.out.print("Enter exponent: ");
                double exponent = getDoubleInput();

                double power = Math.pow(base, exponent);
                System.out.println("Result = " + power);

                history.add(base + " ^ " + exponent + " = " + power);
                break;

            case 3:
                System.out.print("Enter number 1: ");
                int n1 = getIntInput();

                System.out.print("Enter number 2: ");
                int n2 = getIntInput();

                if (n2 == 0) {
                    System.out.println("Error: Modulus by zero not allowed!");
                    return;
                }

                int mod = n1 % n2;
                System.out.println("Result = " + mod);

                history.add(n1 + " % " + n2 + " = " + mod);
                break;

            default:
                System.out.println("Invalid option!");
        }
    }

    // ---------------- UNIT CONVERSION ----------------
    public static void unitConversion() {
        System.out.println("\n--- UNIT CONVERSION ---");
        System.out.println("1. Temperature Conversion");
        System.out.println("2. Currency Conversion");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                temperatureConversion();
                break;

            case 2:
                currencyConversion();
                break;

            default:
                System.out.println("Invalid option!");
        }
    }

    public static void temperatureConversion() {
        System.out.println("\n--- TEMPERATURE CONVERSION ---");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        if (choice == 1) {
            System.out.print("Enter Celsius: ");
            double celsius = getDoubleInput();

            double fahrenheit = (celsius * 9 / 5) + 32;
            System.out.println("Fahrenheit = " + fahrenheit);

            history.add(celsius + " °C = " + fahrenheit + " °F");

        } else if (choice == 2) {
            System.out.print("Enter Fahrenheit: ");
            double fahrenheit = getDoubleInput();

            double celsius = (fahrenheit - 32) * 5 / 9;
            System.out.println("Celsius = " + celsius);

            history.add(fahrenheit + " °F = " + celsius + " °C");

        } else {
            System.out.println("Invalid option!");
        }
    }

    public static void currencyConversion() {
        System.out.println("\n--- CURRENCY CONVERSION ---");
        System.out.println("Using Fixed Rate for Demo");
        System.out.println("1 USD = 83 INR");
        System.out.println("1. USD to INR");
        System.out.println("2. INR to USD");
        System.out.print("Choose option: ");

        int choice = getIntInput();

        BigDecimal rate = new BigDecimal("83");

        if (choice == 1) {
            System.out.print("Enter USD amount: ");
            BigDecimal usd = getBigDecimalInput();

            BigDecimal inr = usd.multiply(rate);
            System.out.println("INR = " + inr);

            history.add(usd + " USD = " + inr + " INR");

        } else if (choice == 2) {
            System.out.print("Enter INR amount: ");
            BigDecimal inr = getBigDecimalInput();

            BigDecimal usd = inr.divide(rate, 4, RoundingMode.HALF_UP);
            System.out.println("USD = " + usd);

            history.add(inr + " INR = " + usd + " USD");

        } else {
            System.out.println("Invalid option!");
        }
    }

    // ---------------- HISTORY ----------------
    public static void showHistory() {
        System.out.println("\n--- HISTORY ---");

        if (history.isEmpty()) {
            System.out.println("No calculations done yet!");
            return;
        }

        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }

    public static void clearHistory() {
        history.clear();
        System.out.println("History cleared successfully!");
    }

    // ---------------- INPUT METHODS ----------------
    public static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.next());
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a valid integer: ");
            }
        }
    }

    public static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(sc.next());
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a valid number: ");
            }
        }
    }

    public static BigDecimal getBigDecimalInput() {
        while (true) {
            try {
                return new BigDecimal(sc.next());
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a valid number: ");
            }
        }
    }
}