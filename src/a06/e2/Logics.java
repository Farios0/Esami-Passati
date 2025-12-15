package a06.e2;

import java.util.List;
import java.util.stream.Stream;

public interface Logics {
    public void updateCells();
    boolean timeToQuit();
    Stream<Pair<Pair<Integer, Integer>, String>> getStream();
}
