
import java.sql.*;
import java.util.*;

public class MatcherDAO {

    /* ---------- STUDENTS ---------- */
    public void createStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students (name) VALUES (?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.executeUpdate();
        }
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? new Student(rs.getInt(1), rs.getString(2)) : null;
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        ResultSet rs = DBUtil.getConnection().createStatement().executeQuery("SELECT * FROM students");
        while (rs.next()) list.add(new Student(rs.getInt(1), rs.getString(2)));
        return list;
    }

    public void updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET name=? WHERE student_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setInt(2, s.getStudentId());
            ps.executeUpdate();
        }
    }

    public void deleteStudent(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection()) {
            c.createStatement().executeUpdate("DELETE FROM students WHERE student_id=" + id);
        }
    }

    /* ---------- SKILLS ---------- */
    public void createSkill(Skill s) throws SQLException {
        String sql = "INSERT INTO skills (skill_name) VALUES (?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getSkillName());
            ps.executeUpdate();
        }
    }

    public Skill getSkillById(int id) throws SQLException {
        ResultSet rs = DBUtil.getConnection().createStatement()
                .executeQuery("SELECT * FROM skills WHERE skill_id=" + id);
        return rs.next() ? new Skill(rs.getInt(1), rs.getString(2)) : null;
    }

    public List<Skill> getAllSkills() throws SQLException {
        List<Skill> list = new ArrayList<>();
        ResultSet rs = DBUtil.getConnection().createStatement().executeQuery("SELECT * FROM skills");
        while (rs.next()) list.add(new Skill(rs.getInt(1), rs.getString(2)));
        return list;
    }

    public void updateSkill(Skill s) throws SQLException {
        String sql = "UPDATE skills SET skill_name=? WHERE skill_id=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getSkillName());
            ps.setInt(2, s.getSkillId());
            ps.executeUpdate();
        }
    }

    public void deleteSkill(int skillId) throws SQLException {
    String deleteRequirements =
        "DELETE FROM career_requirements WHERE skill_id = ?";
    String deleteSkill =
        "DELETE FROM skills WHERE skill_id = ?";

    try (Connection conn = DBUtil.getConnection()) {
        conn.setAutoCommit(false);

        try (PreparedStatement ps1 = conn.prepareStatement(deleteRequirements);
             PreparedStatement ps2 = conn.prepareStatement(deleteSkill)) {

            ps1.setInt(1, skillId);
            ps1.executeUpdate();

            ps2.setInt(1, skillId);
            ps2.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }
}


    /* ===================== INTERESTS ===================== */

    public void createInterest(Interest i) throws SQLException {
        String sql = "INSERT INTO interests (interest_name) VALUES (?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, i.getInterestName());
            ps.executeUpdate();
        }
    }

    public Interest getInterestById(int id) throws SQLException {
        String sql = "SELECT * FROM interests WHERE interest_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next()
                    ? new Interest(rs.getInt("interest_id"), rs.getString("interest_name"))
                    : null;
        }
    }

    public List<Interest> getAllInterests() throws SQLException {
        List<Interest> list = new ArrayList<>();
        String sql = "SELECT * FROM interests";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Interest(rs.getInt("interest_id"), rs.getString("interest_name")));
            }
        }
        return list;
    }

    public void updateInterest(Interest i) throws SQLException {
        String sql = "UPDATE interests SET interest_name=? WHERE interest_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, i.getInterestName());
            ps.setInt(2, i.getInterestId());
            ps.executeUpdate();
        }
    }

    public void deleteInterest(int interestId) throws SQLException {

    String deleteRequirements =
        "DELETE FROM career_requirements WHERE interest_id = ?";
    String deleteInterest =
        "DELETE FROM interests WHERE interest_id = ?";

    try (Connection conn = DBUtil.getConnection()) {
        conn.setAutoCommit(false);

        try (PreparedStatement ps1 = conn.prepareStatement(deleteRequirements);
             PreparedStatement ps2 = conn.prepareStatement(deleteInterest)) {

            ps1.setInt(1, interestId);
            ps1.executeUpdate();

            ps2.setInt(1, interestId);
            ps2.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }
}

    /* ===================== CAREERS ===================== */

    public void createCareer(Career crr) throws SQLException {
        String sql = "INSERT INTO careers (title, category, description) VALUES (?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, crr.getTitle());
            ps.setString(2, crr.getCategory());
            ps.setString(3, crr.getDescription());
            ps.executeUpdate();
        }
    }

    public Career getCareerById(int id) throws SQLException {
        String sql = "SELECT * FROM careers WHERE career_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next()
                    ? new Career(
                            rs.getInt("career_id"),
                            rs.getString("title"),
                            rs.getString("category"),
                            rs.getString("description"))
                    : null;
        }
    }

    public List<Career> getAllCareers() throws SQLException {
        List<Career> list = new ArrayList<>();
        String sql = "SELECT * FROM careers";
        try (Connection c = DBUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Career(
                        rs.getInt("career_id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("description")));
            }
        }
        return list;
    }

    public void updateCareer(Career crr) throws SQLException {
        String sql = "UPDATE careers SET title=?, category=?, description=? WHERE career_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, crr.getTitle());
            ps.setString(2, crr.getCategory());
            ps.setString(3, crr.getDescription());
            ps.setInt(4, crr.getCareerId());
            ps.executeUpdate();
        }
    }

    public void deleteCareer(int id) throws SQLException {
        try (Connection c = DBUtil.getConnection()) {
            c.createStatement().executeUpdate(
                    "DELETE FROM careers WHERE career_id=" + id);
        }
    }

    /* ===================== CAREER REQUIREMENTS ===================== */

    public void createCareerRequirement(int careerId, int skillId, int interestId, int weight)
            throws SQLException {
        String sql = """
                INSERT INTO career_requirements (career_id, skill_id, interest_id, weight)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, careerId);
            ps.setInt(2, skillId);
            ps.setInt(3, interestId);
            ps.setInt(4, weight);
            ps.executeUpdate();
        }
    }

    public List<String> getCareerRequirementsByCareerId(int careerId) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT * FROM career_requirements WHERE career_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, careerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add("SkillID=" + rs.getInt("skill_id")
                        + ", InterestID=" + rs.getInt("interest_id")
                        + ", Weight=" + rs.getInt("weight"));
            }
        }
        return list;
    }

    public void updateCareerRequirement(int careerId, int skillId, int interestId, int weight)
            throws SQLException {
        String sql = """
                UPDATE career_requirements
                SET weight=?
                WHERE career_id=? AND skill_id=? AND interest_id=?
                """;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, weight);
            ps.setInt(2, careerId);
            ps.setInt(3, skillId);
            ps.setInt(4, interestId);
            ps.executeUpdate();
        }
    }

    public void deleteCareerRequirement(int careerId, int skillId, int interestId)
            throws SQLException {
        String sql = """
                DELETE FROM career_requirements
                WHERE career_id=? AND skill_id=? AND interest_id=?
                """;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, careerId);
            ps.setInt(2, skillId);
            ps.setInt(3, interestId);
            ps.executeUpdate();
        }
    }
}
