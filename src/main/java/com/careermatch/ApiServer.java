import static spark.Spark.*;

public class ApiServer {

    public static void main(String[] args) {

        port(4567);

        // Handle CORS preflight (OPTIONS) — must be registered before all other routes
        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            res.header("Access-Control-Allow-Origin", "*");
            res.status(200);
            return "OK";
        });

        // Apply CORS headers to every response AFTER route handler completes
        afterAfter((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept");
            res.header("Access-Control-Max-Age", "86400");
            if (!req.requestMethod().equalsIgnoreCase("OPTIONS")) {
                res.type("application/json");
            }
        });

        new StudentController();
        new SkillController();
        new InterestController();
        new CareerController();

        System.out.println("PathFinder API running on http://localhost:4567");
    }
}