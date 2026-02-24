
import java.sql.SQLException;
import java.util.List;

public class DataLayerTest {

    private MatcherDAO dao;

    public DataLayerTest() {
        dao = new MatcherDAO();
    }

    public void runAllTests() {
        try {
            System.out.println("===== RUNNING DATA LAYER TESTS =====");

            testStudentCRUD();
            testSkillCRUD();
            testInterestCRUD();
            testCareerCRUD();

            System.out.println("===== ALL TESTS COMPLETED SUCCESSFULLY =====");

        } catch (SQLException e) {
            System.err.println("Test failed:");
            e.printStackTrace();
        }
    }

    private void testStudentCRUD() throws SQLException {
        System.out.println("Testing Student CRUD...");

        Student s = new Student(0, "Test Student");
        dao.createStudent(s);

        List<Student> students = dao.getAllStudents();
        System.out.println("Students count: " + students.size());

        Student first = students.get(0);
        first.setName("Updated Student");
        dao.updateStudent(first);

        dao.deleteStudent(first.getStudentId());

        System.out.println("Student CRUD OK\n");
    }

    private void testSkillCRUD() throws SQLException {
        System.out.println("Testing Skill CRUD...");

        Skill skill = new Skill(0, "Test Skill");
        dao.createSkill(skill);

        List<Skill> skills = dao.getAllSkills();
        System.out.println("Skills count: " + skills.size());

        Skill first = skills.get(0);
        first.setSkillName("Updated Skill");
        dao.updateSkill(first);

        dao.deleteSkill(first.getSkillId());

        System.out.println("Skill CRUD OK\n");
    }

    private void testInterestCRUD() throws SQLException {
        System.out.println("Testing Interest CRUD...");

        Interest interest = new Interest(0, "Test Interest");
        dao.createInterest(interest);

        List<Interest> interests = dao.getAllInterests();
        System.out.println("Interests count: " + interests.size());

        Interest first = interests.get(0);
        first.setInterestName("Updated Interest");
        dao.updateInterest(first);

        dao.deleteInterest(first.getInterestId());

        System.out.println("Interest CRUD OK\n");
    }

    private void testCareerCRUD() throws SQLException {
        System.out.println("Testing Career CRUD...");

        Career career = new Career(0, "Test Career", "Software", "Test Description");
        dao.createCareer(career);

        List<Career> careers = dao.getAllCareers();
        System.out.println("Careers count: " + careers.size());

        Career first = careers.get(0);
        first.setTitle("Updated Career");
        dao.updateCareer(first);

        dao.deleteCareer(first.getCareerId());

        System.out.println("Career CRUD OK\n");
    }
}