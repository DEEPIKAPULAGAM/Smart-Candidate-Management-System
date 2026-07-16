public class Candidate{
    private int id;

    private String name;
    private String email;
    private String phone;

    private String college;
    private String branch;

    private int graduationYear;
    private double cgpa;

    private String skills;

    private int experienceYears;

    public Candidate() {
        //Default constructor
    }
    
    // Constructor for retrieving data from database
    public Candidate(int id,String name,String email,String phone,String college,String branch,int graduationYear,double cgpa,String skills,int experienceYears){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.college = college;
        this.branch = branch;
        this.graduationYear = graduationYear;
        this.cgpa = cgpa;
        this.skills = skills;
        this.experienceYears = experienceYears;
    }

    // Constructor for inserting new candidates
    public Candidate(String name, String email, String phone,
                     String college, String branch, int graduationYear,
                     double cgpa, String skills, int experienceYears) {

        this(0, name, email, phone, college, branch,
             graduationYear, cgpa, skills, experienceYears);
    }

    //getters and setters
    //id
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    //name
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    //email
    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //phone
    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //college
    public String getCollege(){
        return college;
    }
    public void setCollege(String college){
        this.college = college;
    }
    //branch
    public String getBranch(){
        return branch;
    }
    public void setBranch(String branch){
        this.branch = branch;
    }
    //graduation year
    public int getGraduationYear(){
        return graduationYear;
    }
    public void setGraduationYear(int graduationYear){
        this.graduationYear = graduationYear;
    }
    //cgpa
    public double getCgpa(){
        return cgpa;
    }
    public void setCgpa(double cgpa){
        this.cgpa = cgpa;
    }
    //skills
    public String getSkills(){
        return skills;
    }
    public void setSkills(String skills){
        this.skills = skills;
    }
    //experience years
    public int getExperienceYears(){
        return experienceYears;
    }
    public void setExperienceYears(int experienceYears){
        this.experienceYears = experienceYears;
    }

    //why use toString() -> when we want to view all candidates instead of writing s.o.p(candidate.getCga), s.o.p(candidate.getName)... we can directly write s.o.p(candidate)
    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", college='" + college + '\'' +
                ", branch='" + branch + '\'' +
                ", graduationYear=" + graduationYear +
                ", cgpa=" + cgpa +
                ", skills='" + skills + '\'' +
                ", experienceYears=" + experienceYears +
                '}';
    }

}