import com.example.roommatematching.Initialize;
import com.example.roommatematching.stableMatching;
import org.junit.jupiter.api.Test;

public class MatchTest {
    @Test
    void testSimplePairs() {
        // Testing functionality of matching if all people want pairs.
            // allows us to check if primary pair matching works.
        runExecutable("simplePairMatching.xlsx");
    }

    @Test
    void testSimplePairs_oddManOut() {
        // Testing functionality of matching if odd number of people want pairs.
        // two people: one from each gender should be left out due to odd number.
        runExecutable("simplePairMatching_OddManOut.xlsx");
    }

    @Test
    void testSimpleGroups() {
        // Testing functionality of matching groups together, with or without original roommates.
        // Nobody should be left out.
        runExecutable("simpleGroupMatching.xlsx");
    }

    @Test
    void testSimpleGroups_oddManOut() {
        // Testing functionality of matching groups together, with odd man out for Male and Female.
        // two people: one from each gender should be left out due to odd number.
        runExecutable("simpleGroupMatching_oddManOut.xlsx");
    }

    // Helper method to run main.
    private static void runExecutable(String filename) {
        Initialize.initializeAllStudents(filename);
        Initialize.createSeekers();
        stableMatching matcher = Initialize.initialize_matching();
        System.out.println("\nRunning Algorithm...");
        matcher.matchStudents();
    }
}
