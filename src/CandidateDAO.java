import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidateDAO {

    // Add Candidate
    public boolean addCandidate(Candidate candidate) {

        Connection con = DBConnection.getConnection();

        String sql = "INSERT INTO candidates(name,email,phone,college,branch,graduation_year,cgpa,skills,experience_years) VALUES(?,?,?,?,?,?,?,?,?)";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getEmail());
            ps.setString(3, candidate.getPhone());
            ps.setString(4, candidate.getCollege());
            ps.setString(5, candidate.getBranch());
            ps.setInt(6, candidate.getGraduationYear());
            ps.setDouble(7, candidate.getCgpa());
            ps.setString(8, candidate.getSkills());
            ps.setInt(9, candidate.getExperienceYears());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // View All Candidates
    public void viewAllCandidates() {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM candidates";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println("---------------------------------------");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Email : " + rs.getString("email"));
                System.out.println("Phone : " + rs.getString("phone"));
                System.out.println("College : " + rs.getString("college"));
                System.out.println("Branch : " + rs.getString("branch"));
                System.out.println("Graduation Year : " + rs.getInt("graduation_year"));
                System.out.println("CGPA : " + rs.getDouble("cgpa"));
                System.out.println("Skills : " + rs.getString("skills"));
                System.out.println("Experience : " + rs.getInt("experience_years"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search Candidate by ID
    public void searchCandidate(int id) {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM candidates WHERE id=?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("---------------------------------------");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Email : " + rs.getString("email"));
                System.out.println("Phone : " + rs.getString("phone"));
                System.out.println("College : " + rs.getString("college"));
                System.out.println("Branch : " + rs.getString("branch"));
                System.out.println("Graduation Year : " + rs.getInt("graduation_year"));
                System.out.println("CGPA : " + rs.getDouble("cgpa"));
                System.out.println("Skills : " + rs.getString("skills"));
                System.out.println("Experience : " + rs.getInt("experience_years"));

            } else {

                System.out.println("Candidate Not Found!");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search Candidate by Name
    public void searchCandidateByName(String name) {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM candidates WHERE LOWER(name) LIKE LOWER(?)";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("---------------------------------------");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Email : " + rs.getString("email"));
                System.out.println("Phone : " + rs.getString("phone"));
                System.out.println("College : " + rs.getString("college"));
                System.out.println("Branch : " + rs.getString("branch"));
                System.out.println("Graduation Year : " + rs.getInt("graduation_year"));
                System.out.println("CGPA : " + rs.getDouble("cgpa"));
                System.out.println("Skills : " + rs.getString("skills"));
                System.out.println("Experience : " + rs.getInt("experience_years"));
            }

            if (!found) {
                System.out.println("Candidate Not Found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update Candidate
    public boolean updateCandidate(Candidate candidate) {

        Connection con = DBConnection.getConnection();

        String sql = "UPDATE candidates SET name=?, email=?, phone=?, college=?, branch=?, graduation_year=?, cgpa=?, skills=?, experience_years=? WHERE id=?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getEmail());
            ps.setString(3, candidate.getPhone());
            ps.setString(4, candidate.getCollege());
            ps.setString(5, candidate.getBranch());
            ps.setInt(6, candidate.getGraduationYear());
            ps.setDouble(7, candidate.getCgpa());
            ps.setString(8, candidate.getSkills());
            ps.setInt(9, candidate.getExperienceYears());
            ps.setInt(10, candidate.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        // Delete Candidate
    public boolean deleteCandidate(int id) {

        Connection con = DBConnection.getConnection();

        String sql = "DELETE FROM candidates WHERE id=?";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Filter Candidates by CGPA
    public void filterByCGPA(double cgpa) {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM candidates WHERE cgpa >= ? ORDER BY cgpa DESC";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, cgpa);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("---------------------------------------");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("CGPA : " + rs.getDouble("cgpa"));
                System.out.println("Branch : " + rs.getString("branch"));
                System.out.println("Skills : " + rs.getString("skills"));
            }

            if (!found) {
                System.out.println("No Candidates Found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sort Candidates by CGPA
    public void sortByCGPA() {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM candidates ORDER BY cgpa DESC";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println("---------------------------------------");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("CGPA : " + rs.getDouble("cgpa"));
                System.out.println("College : " + rs.getString("college"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search Candidate by Skill
    public void searchCandidateBySkill(String skill) {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT * FROM candidates WHERE LOWER(skills) LIKE LOWER(?)";

        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + skill + "%");

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("---------------------------------------");
                System.out.println("ID : " + rs.getInt("id"));
                System.out.println("Name : " + rs.getString("name"));
                System.out.println("Skills : " + rs.getString("skills"));
                System.out.println("CGPA : " + rs.getDouble("cgpa"));
            }

            if (!found) {
                System.out.println("No Candidates Found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Total Candidates
    public int getTotalCandidates() {

        Connection con = DBConnection.getConnection();

        String sql = "SELECT COUNT(*) FROM candidates";

        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}