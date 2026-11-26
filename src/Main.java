import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Using DAO interface so Main doesn't need to know the database logic
        CandidateDAO dao = new CandidateDAOImpl();

        int choice;

        // Display the menu repeatedly until the user chooses exit
        do {
            System.out.println("\n=========================================");
            System.out.println(" Smart Candidate Management System");
            System.out.println("=========================================");
            System.out.println("1. Add Candidate");
            System.out.println("2. View All Candidates");
            System.out.println("3. Search Candidate by ID");
            System.out.println("4. Search Candidate by Name");
            System.out.println("5. Update Candidate");
            System.out.println("6. Delete Candidate");
            System.out.println("7. Filter Candidates by CGPA");
            System.out.println("8. Sort Candidates by CGPA");
            System.out.println("9. Total Candidates");
            System.out.println("10. Search Candidate by Skill");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt(sc);

            try {
                switch (choice) {
                    case 1: {
                        String name = readRequiredText(sc, "Enter Name: ");
                        String email = readValidEmail(sc);

                        String phone = readValidPhone(sc);

                        System.out.print("Enter College: ");
                        String college = sc.nextLine().trim();

                        System.out.print("Enter Branch: ");
                        String branch = sc.nextLine().trim();

                        int graduationYear = readValidGraduationYear(sc);
                        double cgpa = readValidCGPA(sc);

                        System.out.print("Enter Skills: ");
                        String skills = sc.nextLine().trim();

                        int experienceYears = readValidExperience(sc);

                        // Using constructor without ID as ID is generated in the database
                        Candidate candidate = new Candidate(
                                name,
                                email,
                                phone,
                                college,
                                branch,
                                graduationYear,
                                cgpa,
                                skills,
                                experienceYears
                        );

                        if (dao.addCandidate(candidate)) {
                            System.out.println("Candidate Added Successfully!");
                        } else {
                            System.out.println("Failed to Add Candidate!");
                        }

                        break;
                    }

                    case 2: {
                        printCandidates(dao.getAllCandidates());

                        break;
                    }

                    case 3: {
                        System.out.print("Enter Candidate ID: ");
                        int id = readInt(sc);

                        Candidate candidate = dao.searchCandidate(id);

                        if (candidate != null) {
                            System.out.println(candidate);
                        } else {
                            System.out.println("Candidate Not Found!");
                        }

                        break;
                    }

                    case 4: {
                        String name = readRequiredText(sc, "Enter Candidate Name: ");

                        printCandidates(dao.searchCandidateByName(name));
                        break;
                    }

                    case 5: {
                        System.out.print("Enter Candidate ID: ");
                        int id = readInt(sc);

                        // First check if the candidate exists before updating
                        Candidate existingCandidate = dao.searchCandidate(id);

                        if (existingCandidate == null) {
                            System.out.println("Candidate Not Found!");
                            break;
                        }

                        System.out.println("Which field do you want to update?");
                        System.out.println("1. Name");
                        System.out.println("2. Email");
                        System.out.println("3. Phone");
                        System.out.println("4. College");
                        System.out.println("5. Branch");
                        System.out.println("6. Graduation Year");
                        System.out.println("7. CGPA");
                        System.out.println("8. Skills");
                        System.out.println("9. Experience Years");
                        System.out.println("10. Update All Details");
                        System.out.print("Enter your choice: ");

                        int fieldChoice = readInt(sc);

                        if (fieldChoice == 10) {

                            // Read all the new candidate details
                            String name = readRequiredText(sc, "Enter Name: ");
                            String email = readValidEmail(sc);
                            String phone = readValidPhone(sc);
                            String college = readRequiredText(sc, "Enter College: ");
                            String branch = readRequiredText(sc, "Enter Branch: ");

                            int graduationYear = readValidGraduationYear(sc);
                            double cgpa = readValidCGPA(sc);

                            String skills = readRequiredText(sc, "Enter Skills: ");
                            int experienceYears = readValidExperience(sc);

                            Candidate candidate = new Candidate(
                                    id, name, email, phone, college, branch,
                                    graduationYear, cgpa, skills, experienceYears
                            );

                            if (dao.updateCandidate(candidate)) {
                                System.out.println("Candidate details updated successfully!");
                            } else {
                                System.out.println("Candidate not found or update failed.");
                            }

                            break;
                        }

                        if (fieldChoice < 1 || fieldChoice > 9) {
                            System.out.println("Invalid field choice!");
                            break;
                        }

                        // Object is used because the new value can be String, int or double
                        Object newValue = null;

                        switch (fieldChoice) {
                            case 1:
                                newValue = readRequiredText(sc, "Enter new name: ");
                                break;

                            case 2:
                                newValue = readValidEmail(sc);
                                break;

                            case 3:
                                newValue = readValidPhone(sc);
                                break;

                            case 4:
                                System.out.print("Enter new college: ");
                                newValue = sc.nextLine().trim();
                                break;

                            case 5:
                                System.out.print("Enter new branch: ");
                                newValue = sc.nextLine().trim();
                                break;

                            case 6:
                                newValue = readValidGraduationYear(sc);
                                break;

                            case 7:
                                newValue = readValidCGPA(sc);
                                break;

                            case 8:
                                System.out.print("Enter new skills: ");
                                newValue = sc.nextLine().trim();
                                break;

                            case 9:
                                newValue = readValidExperience(sc);
                                break;

                            default:
                                System.out.println("Invalid field choice!");
                                break;
                        }

                        if (newValue == null) {
                            System.out.println("Invalid field choice!");
                            break;
                        }

                        if (dao.updateCandidateField(id, fieldChoice, newValue)) {
                            System.out.println("Candidate Updated Successfully!");
                        } else {
                            System.out.println("Candidate Not Found!");
                        }

                        break;
                    }

                    case 6: {
                        System.out.print("Enter Candidate ID: ");
                        int id = readInt(sc);

                        if (dao.deleteCandidate(id)) {
                            System.out.println("Candidate Deleted Successfully!");
                        } else {
                            System.out.println("Candidate Not Found!");
                        }

                        break;
                    }

                    case 7: {
                        double cgpa = readValidCGPA(sc);
                        printCandidates(dao.filterByCGPA(cgpa));
                        break;
                    }

                    case 8: {
                        printCandidates(dao.sortByCGPA());
                        break;
                    }

                    case 9: {
                        System.out.println(
                                "Total Candidates: " + dao.getTotalCandidates()
                        );
                        break;
                    }

                    case 10: {
                        System.out.print(
                                "Enter skill(s), separated by commas (example: Java, Python): "
                        );
                        String skills = sc.nextLine().trim();

                        printCandidates(dao.searchCandidateBySkill(skills));
                        break;
                    }

                    case 11: {
                        System.out.println("Thank You!");
                        break;
                    }

                    default: {
                        System.out.println("Invalid Choice!");
                    }
                }

            // Handles constraint violations like duplicate email
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(
                        "A candidate with this email already exists!"
                );

            // Handles other database errors without stopping the program
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        } while (choice != 11);

        sc.close();
    }

    // Common method to display candidates instead of repeating the loop in every case
    private static void printCandidates(List<Candidate> candidates) {
        if (candidates.isEmpty()) {
            System.out.println("No Candidates Found!");
            return;
        }

        for (Candidate candidate : candidates) {
            // This internally calls toString() from Candidate class
            System.out.println(candidate);
            System.out.println("------------------------------");
        }
    }

    // Used for fields that should not be empty
    private static String readRequiredText(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field is required.");
        }
    }

    private static String readValidEmail(Scanner sc) {
        while (true) {
            String email = readRequiredText(sc, "Enter Email: ");

            // Regex is used to check the email format
            if (email.matches(
                    "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
            )) {
                return email;
            }

            System.out.println("Invalid email format. Please try again.");
        }
    }

    private static String readValidPhone(Scanner sc) {
        while (true) {
            String phone = readRequiredText(sc, "Enter Phone: ");

            // Check if the phone number has 10 digits and starts with 6-9
            if (phone.matches("[6-9][0-9]{9}")) {
                return phone;
            }

            System.out.println("Invalid phone number! Enter a valid 10-digit mobile number.");
        }
    }

    // Keep asking for input until the user enters a valid integer
    private static int readInt(Scanner sc) {
        while (true) {
            // Using nextLine instead of nextInt to avoid newline issues
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print(
                        "Invalid input! Please enter a whole number: "
                );
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                // isFinite is used to avoid values like NaN and Infinity
                if (Double.isFinite(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Ask for input again
            }

            System.out.print("Invalid input! Please enter a number: ");
        }
    }

    private static double readValidCGPA(Scanner sc) {
        while (true) {
            System.out.print("Enter CGPA (0-10): ");
            double cgpa = readDouble(sc);

            if (cgpa >= 0 && cgpa <= 10) {
                return cgpa;
            }

            System.out.println(
                    "Invalid CGPA! Please enter a value between 0 and 10."
            );
        }
    }

    private static int readValidExperience(Scanner sc) {
        while (true) {
            System.out.print("Enter Experience Years: ");
            int experience = readInt(sc);

            if (experience >= 0) {
                return experience;
            }

            System.out.println("Experience cannot be negative!");
        }
    }

    private static int readValidGraduationYear(Scanner sc) {
        while (true) {
            System.out.print("Enter Graduation Year: ");
            int year = readInt(sc);

            if (year >= 2021 && year <= 2027) {
                return year;
            }

            System.out.println("Invalid graduation year!");
        }
    }
}
