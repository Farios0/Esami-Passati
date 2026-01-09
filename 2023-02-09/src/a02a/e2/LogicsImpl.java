package a02a.e2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class LogicsImpl implements Logics{
    Set<Pair<Integer, Integer>> grid;
    Set<Pair<Integer, Integer>> bishops;
    private int size;

    public LogicsImpl(int size){
        this.bishops = new HashSet<>();
        this.grid = new HashSet<>();
        this.size = size;
    }
    @Override
    public List<Pair<Integer, Integer>> getBCells(Pair<Integer, Integer> position) {
        List<List<Integer>> dir = List.of(List.of(-1 , 1), List.of(1 , -1), List.of(-1 , -1), List.of(1 , 1));
        List<Pair<Integer, Integer>> result = dir.stream().flatMap(l -> getDiagonal(position, l.getFirst(), l.getLast())).toList();
        grid.addAll(result);
        bishops.add(position);
        return result;  
    }

    @Override
    public boolean isItOver() {
        if (bishops.size() + grid.size() == size * size) {
            grid.clear();
            bishops.clear();
            return true;
        }
        return false;
    }

    private Stream<Pair<Integer, Integer>> getDiagonal(Pair<Integer, Integer> pos, int ud, int lr) {
        return Stream.iterate(1, i ->  i + 1).map(i -> new Pair<>(pos.getX() + i * ud, pos.getY() + i * lr))
        .takeWhile(p -> p.getX() >= 0 && p.getY() >= 0 && p.getX() < size && p.getY() < size);
    }
    
}
