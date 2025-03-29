import com.example.roommatematching.Initialize;
import com.example.roommatematching.stableMatching;
import org.junit.jupiter.api.Test;

public class MatchTest {
    @Test
    void testOddManOutMale() {
        runExecutable("roommateSheet.xlsx");
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
