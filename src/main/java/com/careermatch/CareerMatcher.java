
import java.util.*;

public class CareerMatcher {

    public Map<Career, Integer> matchCareers(
            List<Skill> studentSkills,
            List<Interest> studentInterests,
            Map<Career, Map<String, Integer>> careerWeights) {

        Map<Career, Integer> scores = new HashMap<>();

        for (Career career : careerWeights.keySet()) {
            int score = 0;
            Map<String, Integer> weights = careerWeights.get(career);

            for (Skill skill : studentSkills) {
                score += weights.getOrDefault(skill.getSkillName(), 0);
            }

            for (Interest interest : studentInterests) {
                score += weights.getOrDefault(interest.getInterestName(), 0);
            }

            scores.put(career, score);
        }

        return scores;
    }
}
