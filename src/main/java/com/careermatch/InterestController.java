import com.google.gson.Gson;

import java.util.List;

import static spark.Spark.*;

/**
 * InterestController
 *
 * Routes:
 *   GET    /api/interests          → getAllInterests
 *   GET    /api/interests/:id      → getInterestById
 *   POST   /api/interests          → save (insert)
 *   PUT    /api/interests/:id      → save (update)
 */
public class InterestController {

    private final BusinessManager bm = new BusinessManager();
    private final Gson gson = new Gson();

    public InterestController() {

        get("/api/interests", (req, res) -> {
            try {
                List<Interest> interests = bm.getAllInterests();
                return gson.toJson(interests);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        get("/api/interests/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Interest i = bm.getInterestById(id);
                if (i == null) { res.status(404); return error("Interest not found"); }
                return gson.toJson(i);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        post("/api/interests", (req, res) -> {
            try {
                Interest parsed = gson.fromJson(req.body(), Interest.class);
                Interest toInsert = new Interest(0, parsed.getInterestName());
                bm.saveInterest(toInsert);
                List<Interest> all = bm.getAllInterests();
                Interest saved = all.stream()
                        .filter(it -> it.getInterestName().equals(toInsert.getInterestName()))
                        .reduce((a, b) -> b).orElse(toInsert);
                res.status(201);
                return gson.toJson(saved);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        put("/api/interests/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Interest parsed = gson.fromJson(req.body(), Interest.class);
                Interest toUpdate = new Interest(id, parsed.getInterestName());
                bm.saveInterest(toUpdate);
                return gson.toJson(bm.getInterestById(id));
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