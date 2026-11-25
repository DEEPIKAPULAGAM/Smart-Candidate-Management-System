import java.sql.SQLException;
import java.util.List;

// DAO stands for Data Access Object
// Abstraction: defines database operations without exposing implementation
public interface CandidateDAO {

    // Insert a new candidate
    boolean addCandidate(Candidate candidate) throws SQLException;

    // Retrieve all candidates
    List<Candidate> getAllCandidates() throws SQLException;

    // Search for a candidate using their unique ID
    Candidate searchCandidate(int id) throws SQLException;

    // Search for candidates by full or partial name
    List<Candidate> searchCandidateByName(String name) throws SQLException;

    // Update all details of an existing candidate
    boolean updateCandidate(Candidate candidate) throws SQLException;

    // Update only the selected field of a candidate
    boolean updateCandidateField(
            int id,
            int fieldChoice,
            Object newValue
    ) throws SQLException;

    // Delete a candidate using their ID
    boolean deleteCandidate(int id) throws SQLException;

    // Retrieve candidates who meet the minimum CGPA requirement
    List<Candidate> filterByCGPA(double cgpa) throws SQLException;

    // Retrieve candidates in descending order of CGPA
    List<Candidate> sortByCGPA() throws SQLException;

    // Return the total number of candidates
    int getTotalCandidates() throws SQLException;

    // Search for candidates matching the required skills
    List<Candidate> searchCandidateBySkill(String skillsInput)
            throws SQLException;
}