package a06.e2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class LogicsImpl implements Logics{
    private final List<List<Pair<Pair<Integer, Integer>, String>>> cells;
    private final int width;

    LogicsImpl(final int width) {
        this.width = width;
        cells = new ArrayList<>();
        setUpGrid();
    }

    private void setUpGrid() {
        for (int i = 0; i < width; i++) {
            List<Pair<Pair<Integer, Integer>, String>> row = new ArrayList<>();
            cells.add(row);
            for(int j = 0; j < width; j++){
            Pair<Pair<Integer, Integer>, String> cell = new Pair<>(new Pair<>(j, i), String.valueOf((int) (Math.random() * 2) + 1)) ;
            row.add(cell);
            }
        }
    }

    

    public  Stream<Pair<Pair<Integer, Integer>, String>> getStream() {
        return cells.stream().flatMap(l -> l.stream());
    }

    @Override
    public void updateCells() {
        for (var l : cells) {
            for (int j = 0; j < width - 1; j ++)
            if (l.get(j).getY().equals(l.get(j + 1).getY())) {
                l.set(j + 1, new Pair<>(l.get(j).getX(), String.valueOf(Integer.parseInt(l.get(j).getY()) * 2)));
                updateRow(l.get(j).getX());
                break;
            }
        }
    }
    private void updateRow(Pair<Integer, Integer> position) {
        List<Pair<Pair<Integer, Integer>, String>> l = cells.get(position.getX());
        l.set(position.getY(), new Pair<>(position, " "));
    }

    @Override
    public boolean timeToQuit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'timeToQuit'");
    }

}
