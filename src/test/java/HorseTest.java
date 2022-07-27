import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    @ParameterizedTest
    @NullSource
    public void constructor_NameIsNull(String name) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 1.0d)
        );
    }

    @ParameterizedTest
    @NullSource
    public void constructor_NameIsNull_ExceptionMessage(String name) {
        String exceptionMessage = "";
        try {
            new Horse(name, 1.0d);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        Assertions.assertEquals("Name cannot be null.", exceptionMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "    "})
    public void constructor_NameIsEmpty(String name) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 1.0d)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "    "})
    public void constructor_NameIsEmpty_ExceptionMessage(String name) {
        String exceptionMessage = "";
        try {
            new Horse(name, 1.0d);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        Assertions.assertEquals("Name cannot be blank.", exceptionMessage);
    }

    @Test
    public void constructor_SpeedIsNegativeNumber() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Horse", -1.0d)
        );
    }

    @Test
    public void constructor_SpeedIsNegativeNumber_ExceptionMessage() {
        String exceptionMessage = "";
        try {
            new Horse("Horse", -1.0d);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        Assertions.assertEquals("Speed cannot be negative.", exceptionMessage);
    }

    @Test
    public void constructor_DistanceIsNegativeNumber() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Horse", 1.0d, -1.0d)
        );
    }

    @Test
    public void constructor_DistanceIsNegativeNumber_ExceptionMessage() {
        String exceptionMessage = "";
        try {
            new Horse("Horse", 1.0d, -1.0d);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        Assertions.assertEquals("Distance cannot be negative.", exceptionMessage);
    }

    @Test
    public void getName() {
        String name = "Horse";
        Horse horse = new Horse(name, 1.0d);
        Assertions.assertEquals(name, horse.getName());
    }

    @Test
    public void getSpeed() {
        double speed = 1.0d;
        Horse horse = new Horse("Horse", speed);
        Assertions.assertEquals(speed, horse.getSpeed());
    }

    @Test
    public void getDistance_Specified() {
        double distance = 1.0d;
        Horse horse = new Horse("Horse", 1.0d, distance);
        Assertions.assertEquals(distance, horse.getDistance());
    }

    @Test
    public void getDistance_Default() {
        Horse horse = new Horse("Horse", 1.0d);
        Assertions.assertEquals(0.0d, horse.getDistance());
    }

    @Test
    public void getRandomDouble_Verify() {
        try (MockedStatic<Horse> utilities =  Mockito.mockStatic(Horse.class)) {
            new Horse("Horse", 1.0d).move();
            utilities.verify(() -> Horse.getRandomDouble(0.2d, 0.9d));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "2.0d, 2.0d, 0.2d",
            "2.0d, 4.0d, 0.4d",
            "4.0d, 2.0d, 0.6d",
            "4.0d, 4.0d, 0.8d"
    })
    public void move_CheckWorkOfFormula(double speed, double distance, double fakeRandomDouble) {
        try (MockedStatic<Horse> utilities =  Mockito.mockStatic(Horse.class)) {
            utilities.when(() -> Horse.getRandomDouble(0.2d, 0.9d)).thenReturn(fakeRandomDouble);
            Horse horse = new Horse("Horse", speed, distance);
            horse.move();
            Assertions.assertEquals(distance + speed * fakeRandomDouble, horse.getDistance());
        }
    }
}
