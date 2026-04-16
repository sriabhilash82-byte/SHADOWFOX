import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    private String name;
    private String phone;
    private String email; // optional

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayContact() {
        System.out.println("Name  : " + name);
        System.out.println("Phone : " + phone);

        if (email == null || email.isEmpty()) {
            System.out.println("Email : Not Provided");
        } else {
            System.out.println("Email : " + email);
        }

        System.out.println("--------------------------");
    }
}

public class ContactManagerApp {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("      Contact Management System");
        System.out.println("==================================");

        boolean running = true;

        while (running) {
            System.out.println("\n---------- MENU ----------");
            System.out.println("1. Add Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    addContact();
                    break;

                case 2:
                    viewContacts();
                    break;

                case 3:
                    searchContact();
                    break;

                case 4:
                    updateContact();
                    break;

                case 5:
                    deleteContact();
                    break;

                case 0:
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }

        sc.close();
    }

    // ---------------- ADD CONTACT ----------------
    public static void addContact() {
        System.out.println("\n--- ADD CONTACT ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone (10 digits): ");
        String phone = sc.nextLine().trim();

        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number! Must be exactly 10 digits.");
            return;
        }

        if (isPhoneExists(phone)) {
            System.out.println("This phone number already exists!");
            return;
        }

        System.out.print("Enter Email (optional, press Enter to skip): ");
        String email = sc.nextLine();

        if (!email.isEmpty() && !isValidEmail(email)) {
            System.out.println("Invalid email format!");
            return;
        }

        Contact newContact = new Contact(name, phone, email);
        contacts.add(newContact);

        System.out.println("Contact added successfully!");
    }

    // ---------------- VIEW CONTACTS ----------------
    public static void viewContacts() {
        System.out.println("\n--- ALL CONTACTS ---");

        if (contacts.isEmpty()) {
            System.out.println("No contacts found!");
            return;
        }

        for (Contact c : contacts) {
            c.displayContact();
        }
    }

    // ---------------- SEARCH CONTACT (WITH NEAR NAME MATCHING) ----------------
    public static void searchContact() {
        System.out.println("\n--- SEARCH CONTACT ---");
        System.out.print("Enter name or phone: ");
        String search = sc.nextLine().trim();

        if (search.isEmpty()) {
            System.out.println("Search cannot be empty!");
            return;
        }

        // First check exact phone match
        for (Contact c : contacts) {
            if (c.getPhone().equals(search)) {
                System.out.println("\nExact Match Found:");
                c.displayContact();
                return;
            }
        }

        ArrayList<Contact> results = new ArrayList<>();
        String input = search.toLowerCase();

        for (Contact c : contacts) {
            String name = c.getName().toLowerCase();

            if (name.contains(input)) {
                results.add(c);
            } else {
                int distance = levenshteinDistance(name, input);

                if (distance <= 2) {
                    results.add(c);
                }
            }
        }

        if (results.isEmpty()) {
            System.out.println("No contact found with given input.");
        } else {
            System.out.println("\nSimilar Contacts Found:");
            for (Contact c : results) {
                c.displayContact();
            }
        }
    }

    // ---------------- UPDATE CONTACT ----------------
    public static void updateContact() {
        System.out.println("\n--- UPDATE CONTACT ---");

        System.out.print("Enter phone number of contact to update (spaces allowed): ");
        String phoneInput = sc.nextLine();

        // remove spaces
        String phone = phoneInput.replaceAll("\\s+", "");

        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number! Only 10 digits allowed (no alphabets/symbols).");
            return;
        }

        Contact contact = findContactByPhone(phone);

        if (contact == null) {
            System.out.println("Contact not found!");
            return;
        }

        System.out.print("Enter new name (leave blank to keep old): ");
        String newName = sc.nextLine();
        if (!newName.isEmpty()) {
            contact.setName(newName);
        }

        System.out.print("Enter new email (optional, press Enter to keep old, type '-' to remove email): ");
        String newEmail = sc.nextLine();

        if (!newEmail.isEmpty()) {
            if (newEmail.equals("-")) {
                contact.setEmail("");
            } else if (isValidEmail(newEmail)) {
                contact.setEmail(newEmail);
            } else {
                System.out.println("Invalid email format! Update cancelled.");
                return;
            }
        }

        System.out.println("Contact updated successfully!");
    }

    // ---------------- DELETE CONTACT ----------------
    public static void deleteContact() {
        System.out.println("\n--- DELETE CONTACT ---");

        System.out.print("Enter phone number of contact to delete (spaces allowed): ");
        String phoneInput = sc.nextLine();

        // remove spaces
        String phone = phoneInput.replaceAll("\\s+", "");

        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number! Only 10 digits allowed (no alphabets/symbols).");
            return;
        }

        Contact contact = findContactByPhone(phone);

        if (contact == null) {
            System.out.println("Contact not found!");
            return;
        }

        contacts.remove(contact);
        System.out.println("Contact deleted successfully!");
    }

    // ---------------- HELPER METHODS ----------------
    public static Contact findContactByPhone(String phone) {
        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    public static boolean isPhoneExists(String phone) {
        return findContactByPhone(phone) != null;
    }

    // phone must be exactly 10 digits only
    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Invalid input! Enter a number: ");
            }
        }
    }

    // ---------------- LEVENSHTEIN DISTANCE METHOD ----------------
    public static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[a.length()][b.length()];
    }
}