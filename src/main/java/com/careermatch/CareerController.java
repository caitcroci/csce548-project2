import com.google.gson.Gson;

import java.util.List;

import static spark.Spark.*;

/**
 * CareerController
 *
 * Routes:
 *   GET    /api/careers          → getAllCareers
 *   GET    /api/careers/:id      → getCareerById
 *   POST   /api/careers          → save (insert)
 *   PUT    /api/careers/:id      → save (update)
 */
public class CareerController {

    private final BusinessManager bm = new BusinessManager();
    private final Gson gson = new Gson();

    public CareerController() {

        get("/api/careers", (req, res) -> {
            try {
                List<Career> careers = bm.getAllCareers();
                return gson.toJson(careers);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        get("/api/careers/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Career c = bm.getCareerById(id);
                if (c == null) { res.status(404); return error("Career not found"); }
                return gson.toJson(c);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        post("/api/careers", (req, res) -> {
            try {
                Career parsed = gson.fromJson(req.body(), Career.class);
                Career toInsert = new Career(0, parsed.getTitle(), parsed.getCategory(), parsed.getDescription());
                bm.saveCareer(toInsert);
                List<Career> all = bm.getAllCareers();
                Career saved = all.stream()
                        .filter(cr -> cr.getTitle().equals(toInsert.getTitle()))
                        .reduce((a, b) -> b).orElse(toInsert);
                res.status(201);
                return gson.toJson(saved);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        put("/api/careers/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Career parsed = gson.fromJson(req.body(), Career.class);
                Career toUpdate = new Career(id, parsed.getTitle(), parsed.getCategory(), parsed.getDescription());
                bm.saveCareer(toUpdate);
                return gson.toJson(bm.getCareerById(id));
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
