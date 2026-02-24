import com.google.gson.Gson;

import java.util.List;

import static spark.Spark.*;

/**
 * SkillController
 *
 * Routes:
 *   GET    /api/skills          → getAllSkills
 *   GET    /api/skills/:id      → getSkillById
 *   POST   /api/skills          → save (insert)
 *   PUT    /api/skills/:id      → save (update)
 */
public class SkillController {

    private final BusinessManager bm = new BusinessManager();
    private final Gson gson = new Gson();

    public SkillController() {

        get("/api/skills", (req, res) -> {
            try {
                List<Skill> skills = bm.getAllSkills();
                return gson.toJson(skills);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        get("/api/skills/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Skill s = bm.getSkillById(id);
                if (s == null) { res.status(404); return error("Skill not found"); }
                return gson.toJson(s);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        post("/api/skills", (req, res) -> {
            try {
                Skill parsed = gson.fromJson(req.body(), Skill.class);
                Skill toInsert = new Skill(0, parsed.getSkillName());
                bm.saveSkill(toInsert);
                List<Skill> all = bm.getAllSkills();
                Skill saved = all.stream()
                        .filter(sk -> sk.getSkillName().equals(toInsert.getSkillName()))
                        .reduce((a, b) -> b).orElse(toInsert);
                res.status(201);
                return gson.toJson(saved);
            } catch (Exception e) {
                res.status(500);
                return error(e.getMessage());
            }
        });

        put("/api/skills/:id", (req, res) -> {
            try {
                int id = Integer.parseInt(req.params(":id"));
                Skill parsed = gson.fromJson(req.body(), Skill.class);
                Skill toUpdate = new Skill(id, parsed.getSkillName());
                bm.saveSkill(toUpdate);
                return gson.toJson(bm.getSkillById(id));
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