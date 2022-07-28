import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {
    @ParameterizedTest
    @NullSource
    public void constructor_ListOfHorsesIsNull(List<Horse> horses) {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(horses)
        );
    }

    @ParameterizedTest
    @NullSource
    public void constructor_ListOfHorsesIsNull_ExceptionMessage(List<Horse> horses) {
        String exceptionMessage = "";
        try {
            new Hippodrome(horses);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        Assertions.assertEquals("Horses cannot be null.", exceptionMessage);
    }

    @Test
    public void constructor_ListOfHorsesIsEmpty() {
        List<Horse> emptyListOfHorses = new ArrayList<>();
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(emptyListOfHorses)
        );
    }

    @Test
    public void constructor_ListOfHorsesIsEmpty_ExceptionMessage() {
        String exceptionMessage = "";
        try {
            List<Horse> emptyListOfHorses = new ArrayList<>();
            new Hippodrome(emptyListOfHorses);
        } catch (IllegalArgumentException e) {
            exceptionMessage = e.getMessage();
        }
        Assertions.assertEquals("Horses cannot be empty.", exceptionMessage);
    }

    @Test
    public void getHorses() {
        List<Horse> listOfHorses = new ArrayList<>(){{
            int countOfHorses = 30;
            for (int i = 1; i <= countOfHorses; ++i) {
                add(new Horse("Horse_" + i, 1.0d));
            }
        }};
        Hippodrome hippodrome = new Hippodrome(new ArrayList<>(listOfHorses));
        Assertions.assertEquals(listOfHorses, hippodrome.getHorses());
    }

    @Test
    public void move() {
        List<Horse> listOfHorses = new ArrayList<>(){{
            int countOfHorses = 50;
            for (int i = 1; i <= countOfHorses; ++i) {
                add(Mockito.mock(Horse.class));
            }
        }};
        Hippodrome hippodrome = new Hippodrome(new ArrayList<>(listOfHorses));
        hippodrome.move();
        for (Horse horse : listOfHorses) {
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }

    @Test
    public void getWinner() {
        double maxDistance = 10.0d;
        List<Horse> listOfHorses = new ArrayList<>(){{
            int countOfHorses = 10;
            double distanceForThisHorse = maxDistance;
            for (int i = 1; i <= countOfHorses; ++i) {
                add(new Horse("Horse_" + i, 1.0d, distanceForThisHorse));
                --distanceForThisHorse;
            }
        }};
        Collections.shuffle(listOfHorses);
        Hippodrome hippodrome = new Hippodrome(new ArrayList<>(listOfHorses));
        Assertions.assertEquals(maxDistance, hippodrome.getWinner().getDistance());
    }
}
