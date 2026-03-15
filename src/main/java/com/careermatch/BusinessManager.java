import java.sql.SQLException;
import java.util.List;

/**
 * BusinessManager – Business Layer
 * Sits between the UI/API and MatcherDAO.
 * Save: if ID == 0 → insert, else → update.
 */
public class BusinessManager {

    private final MatcherDAO dao;

    public BusinessManager() {
        this.dao = new MatcherDAO();
    }

    /* ================================================================
       STUDENTS
    ================================================================ */

    /** Insert if studentId == 0, otherwise update. */
    public void saveStudent(Student student) throws SQLException {
        if (student.getStudentId() == 0) {
            dao.createStudent(student);
        } else {
            dao.updateStudent(student);
        }
    }

    public Student getStudentById(int id) throws SQLException {
        return dao.getStudentById(id);
    }

    public List<Student> getAllStudents() throws SQLException {
        return dao.getAllStudents();
    }

    public void deleteStudent(int id) throws SQLException {
        dao.deleteStudent(id);
    }

    /* ================================================================
       SKILLS
    ================================================================ */

    public void saveSkill(Skill skill) throws SQLException {
        if (skill.getSkillId() == 0) {
            dao.createSkill(skill);
        } else {
            dao.updateSkill(skill);
        }
    }

    public Skill getSkillById(int id) throws SQLException {
        return dao.getSkillById(id);
    }

    public List<Skill> getAllSkills() throws SQLException {
        return dao.getAllSkills();
    }

    public void deleteSkill(int id) throws SQLException {
        dao.deleteSkill(id);
    }

    /* ================================================================
       INTERESTS
    ================================================================ */

    public void saveInterest(Interest interest) throws SQLException {
        if (interest.getInterestId() == 0) {
            dao.createInterest(interest);
        } else {
            dao.updateInterest(interest);
        }
    }

    public Interest getInterestById(int id) throws SQLException {
        return dao.getInterestById(id);
    }

    public List<Interest> getAllInterests() throws SQLException {
        return dao.getAllInterests();
    }

    public void deleteInterest(int id) throws SQLException {
        dao.deleteInterest(id);
    }

    /* ================================================================
       CAREERS
    ================================================================ */

    public void saveCareer(Career career) throws SQLException {
        if (career.getCareerId() == 0) {
            dao.createCareer(career);
        } else {
            dao.updateCareer(career);
        }
    }

    public Career getCareerById(int id) throws SQLException {
        return dao.getCareerById(id);
    }

    public List<Career> getAllCareers() throws SQLException {
        return dao.getAllCareers();
    }

    public void deleteCareer(int id) throws SQLException {
        dao.deleteCareer(id);
    }

    /**
     * Deletes a career and its associated career_requirements rows in one
     * transaction so foreign-key constraints are not violated.
     */
    public void deleteCareerCascade(int id) throws SQLException {
        dao.deleteCareerCascade(id);
    }

    /* ================================================================
       CAREER REQUIREMENTS  (composite key – no plain int ID)
    ================================================================ */

    public void saveCareerRequirement(int careerId, int skillId, int interestId, int weight)
            throws SQLException {
        dao.updateCareerRequirement(careerId, skillId, interestId, weight);
    }

    public void createCareerRequirement(int careerId, int skillId, int interestId, int weight)
            throws SQLException {
        dao.createCareerRequirement(careerId, skillId, interestId, weight);
    }

    public List<String> getCareerRequirementsByCareerId(int careerId) throws SQLException {
        return dao.getCareerRequirementsByCareerId(careerId);
    }

    public void deleteCareerRequirement(int careerId, int skillId, int interestId)
            throws SQLException {
        dao.deleteCareerRequirement(careerId, skillId, interestId);
    }
}