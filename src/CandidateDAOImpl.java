import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CandidateDAOImpl implements CandidateDAO {

    // Common columns used in select queries to avoid writing them again
    private static final String COLS =
            "id,name,email,phone,college,branch,graduation_year,cgpa,skills,experience_years";

    // Converts each row from the database into a Candidate object
    private Candidate mapResultSetToCandidate(ResultSet rs)
            throws SQLException {
        return new Candidate(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("college"),
                rs.getString("branch"),
                rs.getInt("graduation_year"),
                rs.getDouble("cgpa"),
                rs.getString("skills"),
                rs.getInt("experience_years")
        );
    }

    // Common method for select queries to avoid repeating the same code
    // Object... values is used to pass any number of parameters
    private List<Candidate> getCandidates(String sql, Object... values)
            throws SQLException {
        List<Candidate> candidates = new ArrayList<>();

        // Try with resources automatically closes the connection and statement
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets values in place of ? in the query
            // i+1 because JDBC index starts from 1
            for (int i = 0; i < values.length; i++) {
                ps.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                // Read each row and add the candidate to the list
                while (rs.next()) {
                    candidates.add(mapResultSetToCandidate(rs));
                }
            }
        }

        return candidates;
    }

    @Override
    public boolean addCandidate(Candidate candidate) throws SQLException {
        String sql = "INSERT INTO candidates " +
                "(name,email,phone,college,branch,graduation_year,cgpa,skills,experience_years) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";

        // PreparedStatement is used to prevent SQL injection
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getEmail());
            ps.setString(3, candidate.getPhone());
            ps.setString(4, candidate.getCollege());
            ps.setString(5, candidate.getBranch());
            ps.setInt(6, candidate.getGraduationYear());
            ps.setDouble(7, candidate.getCgpa());
            ps.setString(8, candidate.getSkills());
            ps.setInt(9, candidate.getExperienceYears());

            // Returns true if a row is affected
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Candidate> getAllCandidates() throws SQLException {
        return getCandidates("SELECT " + COLS + " FROM candidates");
    }

    @Override
    public Candidate searchCandidate(int id) throws SQLException {
        List<Candidate> candidates = getCandidates(
                "SELECT " + COLS + " FROM candidates WHERE id=?",
                id
        );

        // If candidate is not found return null, otherwise return the candidate
        return candidates.isEmpty() ? null : candidates.get(0);
    }

    @Override
    public List<Candidate> searchCandidateByName(String name)
            throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // LOWER makes the search case-insensitive; % allows partial-name matches
        return getCandidates(
                "SELECT " + COLS +
                        " FROM candidates WHERE LOWER(name) LIKE LOWER(?)",
                "%" + name.trim() + "%"
        );
    }

    @Override
    public boolean updateCandidate(Candidate candidate) throws SQLException {
        String sql = "UPDATE candidates SET " +
                "name=?, email=?, phone=?, college=?, branch=?, " +
                "graduation_year=?, cgpa=?, skills=?, experience_years=? " +
                "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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
        }
    }

    // To update a particular field instead of updating all the details
    @Override
    public boolean updateCandidateField(
            int id,
            int fieldChoice,
            Object newValue
    ) throws SQLException {

        // Column names are taken from this array instead of direct user input
        String[] columns = {
                "name",
                "email",
                "phone",
                "college",
                "branch",
                "graduation_year",
                "cgpa",
                "skills",
                "experience_years"
        };

        if (fieldChoice < 1 || fieldChoice > columns.length) {
            return false;
        }

        // fieldChoice-1 because array index starts from 0
        String column = columns[fieldChoice - 1];
        String sql = "UPDATE candidates SET " + column + "=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // setObject is used because the new value can be of different data types
            ps.setObject(1, newValue);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteCandidate(int id) throws SQLException {
        String sql = "DELETE FROM candidates WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Candidate> filterByCGPA(double cgpa) throws SQLException {
        return getCandidates(
                "SELECT " + COLS +
                        " FROM candidates WHERE cgpa >= ? ORDER BY cgpa DESC",
                cgpa
        );
    }

    @Override
    public List<Candidate> sortByCGPA() throws SQLException {
        return getCandidates(
                "SELECT " + COLS + " FROM candidates ORDER BY cgpa DESC"
        );
    }

    @Override
    public int getTotalCandidates() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM candidates";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

            return 0;
        }
    }

    @Override
    public List<Candidate> searchCandidateBySkill(String skillsInput)
            throws SQLException {
        List<String> requiredSkills = new ArrayList<>();

        // Split the skills by comma and remove spaces and case differences
        if (skillsInput != null) {
            for (String skill : skillsInput.split(",")) {
                String normalizedSkill = skill.trim()
                        .replace(" ", "")
                        .toLowerCase(Locale.ROOT);

                if (!normalizedSkill.isEmpty()) {
                    requiredSkills.add(normalizedSkill);
                }
            }
        }

        if (requiredSkills.isEmpty()) {
            return new ArrayList<>();
        }

        // StringBuilder is used because the query changes based on number of skills
        StringBuilder sql = new StringBuilder(
                "SELECT " + COLS + " FROM candidates WHERE "
        );

        for (int i = 0; i < requiredSkills.size(); i++) {

            // AND is used because the candidate should have all the entered skills
            if (i > 0) {
                sql.append(" AND ");
            }

            // FIND_IN_SET checks if the skill is present in the comma separated skills
            // This avoids matching Java with JavaScript
            sql.append(
                    "FIND_IN_SET(?, REPLACE(LOWER(skills), ' ', '')) > 0"
            );
        }

        return getCandidates(sql.toString(), requiredSkills.toArray());
    }
}