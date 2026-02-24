import java.sql.SQLException;
import java.util.List;

/**
 * BusinessLayerTest – Console App
 * Demonstrates Create → Read → Update → Read → Delete lifecycle
 * for Student (plus related Skill, Interest, Career, CareerRequirement).
 */
public class BusinessLayerTest {

    private static final BusinessManager bm = new BusinessManager();

    public static void main(String[] args) {
        try {
            System.out.println("========================================");
            System.out.println("  PathFinder – Business Layer Test");
            System.out.println("========================================\n");

            // ── 1. CREATE supporting objects ────────────────────────────
            System.out.println("--- Step 1: Creating supporting objects ---");

            Skill skill = new Skill(0, "Java");
            bm.saveSkill(skill);
            // Reload to get the DB-generated ID
            Skill savedSkill = findSkillByName("Java");
            printSkill(savedSkill);

            Interest interest = new Interest(0, "Software Development");
            bm.saveInterest(interest);
            Interest savedInterest = findInterestByName("Software Development");
            printInterest(savedInterest);

            Career career = new Career(0, "Backend Developer", "Software",
                    "Develops server-side applications and APIs.");
            bm.saveCareer(career);
            Career savedCareer = findCareerByTitle("Backend Developer");
            printCareer(savedCareer);

            // Link the career to skill + interest
            bm.createCareerRequirement(
                    savedCareer.getCareerId(),
                    savedSkill.getSkillId(),
                    savedInterest.getInterestId(),
                    3);
            System.out.println("CareerRequirement created.");

            // ── 2. CREATE student ────────────────────────────────────────
            System.out.println("\n--- Step 2: Creating new Student ---");
            Student student = new Student(0, "Alice Johnson");
            bm.saveStudent(student);

            // Find the student by scanning all (since no last-insert-id yet)
            Student savedStudent = findStudentByName("Alice Johnson");
            System.out.println("CREATED:");
            printStudent(savedStudent);

            // ── 3. UPDATE student ────────────────────────────────────────
            System.out.println("\n--- Step 3: Updating Student name ---");
            savedStudent.setName("Alice Johnson-Smith");
            bm.saveStudent(savedStudent);         // ID != 0 → triggers update

            Student updatedStudent = bm.getStudentById(savedStudent.getStudentId());
            System.out.println("UPDATED:");
            printStudent(updatedStudent);

            // ── 4. READ ALL ──────────────────────────────────────────────
            System.out.println("\n--- Step 4: Reading all Students ---");
            List<Student> allStudents = bm.getAllStudents();
            System.out.println("Total students in DB: " + allStudents.size());
            allStudents.forEach(BusinessLayerTest::printStudent);

            // ── 5. READ career requirements ──────────────────────────────
            System.out.println("\n--- Step 5: Career requirements for career ID "
                    + savedCareer.getCareerId() + " ---");
            bm.getCareerRequirementsByCareerId(savedCareer.getCareerId())
              .forEach(System.out::println);

            // ── 6. DELETE ────────────────────────────────────────────────
            System.out.println("\n--- Step 6: Deleting test data ---");

            bm.deleteCareerRequirement(
                    savedCareer.getCareerId(),
                    savedSkill.getSkillId(),
                    savedInterest.getInterestId());
            System.out.println("CareerRequirement deleted.");

            bm.deleteStudent(savedStudent.getStudentId());
            System.out.println("Student deleted. Verify (should be null): "
                    + bm.getStudentById(savedStudent.getStudentId()));

            bm.deleteCareer(savedCareer.getCareerId());
            System.out.println("Career deleted.");

            bm.deleteSkill(savedSkill.getSkillId());
            System.out.println("Skill deleted.");

            bm.deleteInterest(savedInterest.getInterestId());
            System.out.println("Interest deleted.");

            System.out.println("\n========================================");
            System.out.println("  Business layer test complete!");
            System.out.println("========================================");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* ── helpers ─────────────────────────────────────────────────── */

    private static Student findStudentByName(String name) throws SQLException {
        return bm.getAllStudents().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student not found: " + name));
    }

    private static Skill findSkillByName(String name) throws SQLException {
        return bm.getAllSkills().stream()
                .filter(s -> s.getSkillName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Skill not found: " + name));
    }

    private static Interest findInterestByName(String name) throws SQLException {
        return bm.getAllInterests().stream()
                .filter(i -> i.getInterestName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Interest not found: " + name));
    }

    private static Career findCareerByTitle(String title) throws SQLException {
        return bm.getAllCareers().stream()
                .filter(c -> c.getTitle().equals(title))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Career not found: " + title));
    }

    private static void printStudent(Student s) {
        System.out.printf("  Student  [id=%d, name=%s]%n",
                s.getStudentId(), s.getName());
    }

    private static void printSkill(Skill s) {
        System.out.printf("  Skill    [id=%d, name=%s]%n",
                s.getSkillId(), s.getSkillName());
    }

    private static void printInterest(Interest i) {
        System.out.printf("  Interest [id=%d, name=%s]%n",
                i.getInterestId(), i.getInterestName());
    }

    private static void printCareer(Career c) {
        System.out.printf("  Career   [id=%d, title=%s, category=%s]%n",
                c.getCareerId(), c.getTitle(), c.getCategory());
    }
}