package a02a.e2;

import java.util.List;

public interface Logics {
    List<Pair<Integer, Integer>> getBCells(Pair<Integer, Integer> position);
    boolean isItOver();
}
