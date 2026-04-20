package Part2.Models;

public record RaceConfig(
        String passage,
        int numOfTypists,
        boolean autocorrect,
        boolean caffeine,
        boolean night
){}