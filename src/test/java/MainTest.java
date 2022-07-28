import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
    @Disabled
    @Test
    @Timeout(value = 22)
    public void main() {
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
