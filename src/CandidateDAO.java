import java.sql.SQLException;
import java.util.List;

public interface CandidateDAO {

    boolean addCandidate(Candidate candidate) throws SQLException;

    List<Candidate> getAllCandidates() throws SQLException;

    Candidate searchCandidate(int id) throws SQLException;

    List<Candidate> searchCandidateByName(String name) throws SQLException;

    boolean updateCandidate(Candidate candidate) throws SQLException;

    boolean updateCandidateField(
            int id,
            int fieldChoice,
            Object newValue
    ) throws SQLException;

    boolean deleteCandidate(int id) throws SQLException;

    List<Candidate> filterByCGPA(double cgpa) throws SQLException;

    List<Candidate> sortByCGPA() throws SQLException;

    int getTotalCandidates() throws SQLException;

    List<Candidate> searchCandidateBySkill(String skillsInput)
            throws SQLException;
}