import com.example.roommatematching.Initialize;
import com.example.roommatematching.StableMatching;
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

    @Test
    void testSimpleGroupBackups() {
        // Testing how incomplete groups are handled if there are available backups.
        // One group of 3, and 1 person looking for a pair, but has group as a backup.
        // no odd man out should remain.
        runExecutable("simpleGroupBackups.xlsx");
    }

    @Test
    void testSimplePairBackups() {
        // Testing how incomplete groups are handled if there are no available backups, but they can be split into pairs
        // Two groups of 3, and 2 people looking for a pair, but the group has pairs as a backup.
        // no odd man out should remain.
        runExecutable("simplePairBackups.xlsx");
    }

    // Helper method to run main.
    private static void runExecutable(String filename) {
        Initialize.initializeAllStudents(filename);
        Initialize.createSeekers();
        StableMatching matcher = Initialize.initialize_matching();
        System.out.println("\nRunning Algorithm...");
        matcher.matchStudents();
    }
}
