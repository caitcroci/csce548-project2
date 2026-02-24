import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * ApiClientTest – Console App
 * Calls the live Spark REST API to test all endpoints.
 *
 * Prerequisites:
 *   1. MySQL running with career_matcher database populated.
 *   2. ApiServer running:  java -cp "lib/*:src" ApiServer
 *   3. Then run this class: java -cp "lib/*:src" ApiClientTest
 */
public class ApiClientTest {

    private static final String BASE = "http://localhost:4567/api";
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        System.out.println("========================================");
        System.out.println("  PathFinder – API Client Test");
        System.out.println("========================================\n");

        testStudentEndpoints();
        testSkillEndpoints();
        testInterestEndpoints();
        testCareerEndpoints();

        System.out.println("\n========================================");
        System.out.println("  API client test complete!");
        System.out.println("========================================");
    }

    /* ── STUDENTS ──────────────────────────────────────────────────── */
    static void testStudentEndpoints() throws Exception {
        System.out.println("=== STUDENTS ===");

        // GET all
        String all = get("/students");
        System.out.println("GET /api/students → " + truncate(all));

        // GET by id
        String one = get("/students/1");
        System.out.println("GET /api/students/1 → " + one);

        // POST – create
        String created = post("/students", "{\"name\":\"API Test Student\"}");
        System.out.println("POST /api/students → " + created);

        // Extract id from response (simple parse, no full JSON lib needed here)
        int newId = extractIntField(created, "studentId");
        if (newId > 0) {
            // PUT – update
            String updated = put("/students/" + newId,
                    "{\"name\":\"API Test Student Updated\"}");
            System.out.println("PUT /api/students/" + newId + " → " + updated);

            // GET updated
            String verify = get("/students/" + newId);
            System.out.println("GET /api/students/" + newId + " → " + verify);
        }
        System.out.println();
    }

    /* ── SKILLS ────────────────────────────────────────────────────── */
    static void testSkillEndpoints() throws Exception {
        System.out.println("=== SKILLS ===");

        String all = get("/skills");
        System.out.println("GET /api/skills → " + truncate(all));

        String one = get("/skills/1");
        System.out.println("GET /api/skills/1 → " + one);

        String created = post("/skills", "{\"skillName\":\"Kotlin\"}");
        System.out.println("POST /api/skills → " + created);

        int newId = extractIntField(created, "skillId");
        if (newId > 0) {
            String updated = put("/skills/" + newId, "{\"skillName\":\"Kotlin (Updated)\"}");
            System.out.println("PUT /api/skills/" + newId + " → " + updated);
        }
        System.out.println();
    }

    /* ── INTERESTS ─────────────────────────────────────────────────── */
    static void testInterestEndpoints() throws Exception {
        System.out.println("=== INTERESTS ===");

        String all = get("/interests");
        System.out.println("GET /api/interests → " + truncate(all));

        String one = get("/interests/1");
        System.out.println("GET /api/interests/1 → " + one);

        String created = post("/interests", "{\"interestName\":\"Quantum Computing\"}");
        System.out.println("POST /api/interests → " + created);

        int newId = extractIntField(created, "interestId");
        if (newId > 0) {
            String updated = put("/interests/" + newId,
                    "{\"interestName\":\"Quantum Computing (Updated)\"}");
            System.out.println("PUT /api/interests/" + newId + " → " + updated);
        }
        System.out.println();
    }

    /* ── CAREERS ───────────────────────────────────────────────────── */
    static void testCareerEndpoints() throws Exception {
        System.out.println("=== CAREERS ===");

        String all = get("/careers");
        System.out.println("GET /api/careers → " + truncate(all));

        String one = get("/careers/1");
        System.out.println("GET /api/careers/1 → " + one);

        String created = post("/careers",
                "{\"title\":\"API Tester\",\"category\":\"QA\",\"description\":\"Tests REST APIs.\"}");
        System.out.println("POST /api/careers → " + created);

        int newId = extractIntField(created, "careerId");
        if (newId > 0) {
            String updated = put("/careers/" + newId,
                    "{\"title\":\"API Tester Updated\",\"category\":\"QA\",\"description\":\"Updated description.\"}");
            System.out.println("PUT /api/careers/" + newId + " → " + updated);
        }
        System.out.println();
    }

    /* ── HTTP helpers ──────────────────────────────────────────────── */

    static String get(String path) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .GET().build();
        return HTTP.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

    static String post(String path, String json) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return HTTP.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

    static String put(String path, String json) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return HTTP.send(req, HttpResponse.BodyHandlers.ofString()).body();
    }

    /** Naive field extractor for simple flat JSON – avoids adding a second JSON lib. */
    static int extractIntField(String json, String field) {
        try {
            String marker = "\"" + field + "\":";
            int idx = json.indexOf(marker);
            if (idx < 0) return -1;
            int start = idx + marker.length();
            int end = start;
            while (end < json.length() && (Character.isDigit(json.charAt(end)))) end++;
            return Integer.parseInt(json.substring(start, end));
        } catch (Exception e) {
            return -1;
        }
    }

    static String truncate(String s) {
        return s.length() > 120 ? s.substring(0, 120) + "..." : s;
    }
}