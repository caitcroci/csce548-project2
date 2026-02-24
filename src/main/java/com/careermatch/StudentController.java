import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

/**
 * StudentController
 *
 * Routes:
 *   GET    /api/students          → getAllStudents
 *   GET    /api/students/:id      → getStudentById
 *   POST   /api/students          → save (insert, id should be 0 in body)
 *   PUT    /api/students/:id      → save (update, id taken from path)
 */
public class StudentController {

    private final BusinessManager bm = new BusinessManager();
    private final Gson gson = new Gson();

    public StudentController() {

        // GET all
        get("/api/students", (req, res) -> {
            try {
                List<Student> students = bm.getAllStudents();
                return gson.toJson(students);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        // GET by id
        get("/api/students/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Student s = bm.getStudentById(id);
                if (s == null) { res.status(404); return error("Student not found"); }
                return gson.toJson(s);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        // POST – insert
        post("/api/students", (req, res) -> {
            try {
                Student parsed = gson.fromJson(req.body(), Student.class);
                Student toInsert = new Student(0, parsed.getName());
                bm.saveStudent(toInsert);
                List<Student> all = bm.getAllStudents();
                Student saved = all.stream()
                        .filter(st -> st.getName().equals(toInsert.getName()))
                        .reduce((a, b) -> b)
                        .orElse(toInsert);
                res.status(201);
                return gson.toJson(saved);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        // PUT – update
        put("/api/students/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Student parsed = gson.fromJson(req.body(), Student.class);
                Student toUpdate = new Student(id, parsed.getName());
                bm.saveStudent(toUpdate);
                return gson.toJson(bm.getStudentById(id));
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });
    }

    private String error(String msg) {
        return "{\"error\":\"" + msg + "\"}";
    }
}