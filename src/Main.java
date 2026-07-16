import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CandidateDAO dao = new CandidateDAO();

        int choice;

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

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1: {

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter College: ");
                    String college = sc.nextLine();

                    System.out.print("Enter Branch: ");
                    String branch = sc.nextLine();

                    System.out.print("Enter Graduation Year: ");
                    int graduationYear = sc.nextInt();

                    System.out.print("Enter CGPA: ");
                    double cgpa = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter Skills: ");
                    String skills = sc.nextLine();

                    System.out.print("Enter Experience Years: ");
                    int experienceYears = sc.nextInt();

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

                    if (dao.addCandidate(candidate))
                        System.out.println("Candidate Added Successfully!");
                    else
                        System.out.println("Failed to Add Candidate!");

                    break;
                }

                case 2: {

                    dao.viewAllCandidates();
                    break;
                }

                case 3: {

                    System.out.print("Enter Candidate ID: ");
                    int id = sc.nextInt();

                    dao.searchCandidate(id);

                    break;
                }

                case 4: {

                    System.out.print("Enter Candidate Name: ");
                    String name = sc.nextLine();

                    dao.searchCandidateByName(name);

                    break;
                }

                case 5: {

                    System.out.print("Enter Candidate ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter College: ");
                    String college = sc.nextLine();

                    System.out.print("Enter Branch: ");
                    String branch = sc.nextLine();

                    System.out.print("Enter Graduation Year: ");
                    int graduationYear = sc.nextInt();

                    System.out.print("Enter CGPA: ");
                    double cgpa = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter Skills: ");
                    String skills = sc.nextLine();

                    System.out.print("Enter Experience Years: ");
                    int experienceYears = sc.nextInt();

                    Candidate candidate = new Candidate(
                            id,
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

                    if (dao.updateCandidate(candidate))
                        System.out.println("Candidate Updated Successfully!");
                    else
                        System.out.println("Candidate Not Found!");

                    break;
                }

                case 6: {

                    System.out.print("Enter Candidate ID: ");
                    int id = sc.nextInt();

                    if (dao.deleteCandidate(id))
                        System.out.println("Candidate Deleted Successfully!");
                    else
                        System.out.println("Candidate Not Found!");

                    break;
                }

                case 7: {

                    System.out.print("Enter Minimum CGPA: ");
                    double cgpa = sc.nextDouble();

                    dao.filterByCGPA(cgpa);

                    break;
                }

                case 8: {

                    dao.sortByCGPA();

                    break;
                }

                case 9: {

                    System.out.println("Total Candidates : " + dao.getTotalCandidates());

                    break;
                }

                case 10: {

                    System.out.print("Enter Skill: ");
                    String skill = sc.nextLine();

                    dao.searchCandidateBySkill(skill);

                    break;
                }

                case 11: {

                    System.out.println("Thank You!");
                    break;
                }

                default:

                    System.out.println("Invalid Choice!");

            }

        } while (choice != 11);

        sc.close();
    }
}