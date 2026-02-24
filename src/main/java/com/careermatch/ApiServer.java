import static spark.Spark.*;

/**
 * ApiServer – Spark Java REST API entry point
 *
 * Starts the server and registers all controllers.
 * Default port: 4567
 *
 * Run:  java -cp ".:lib/*" ApiServer
 */
public class ApiServer {

    public static void main(String[] args) {

        // ── Server config ────────────────────────────────────────────
        port(4567);

        // Allow JSON responses from all endpoints
        before((req, res) -> res.type("application/json"));

        // ── CORS headers (handy if you test from a browser / Postman) ─
        options("/*", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT");
            res.header("Access-Control-Allow-Headers", "Content-Type");
            return "OK";
        });
        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));

        // ── Register controllers ──────────────────────────────────────
        new StudentController();
        new SkillController();
        new InterestController();
        new CareerController();

        System.out.println("PathFinder API running on http://localhost:4567");
    }
}