package a06.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class LogicsImpl implements Logics {
    private final List<List<Integer>> columns;
    private final int size;
    private boolean first = true;
    private boolean isOver;
    
    public LogicsImpl(int size) {
        this.size = size;
        columns = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            columns.add(new ArrayList<>(size));
            for (int j = 0; j < size; j++) {
                columns.get(i).add(random.nextInt(2) + 1);
            }
        }
    }
    @Override
    public List<String> updateGrid() {
        if (first) {
            first = false;
            return IntStream.range(0, size)
            .boxed().flatMap(i -> columns.stream()
                .map(colum -> colum.get(i)))
            .map(in -> String.valueOf(in))
            .toList();
        }
        isOver = true;
        for (List<Integer> column : columns) {
            boolean done = false;
            int previous = column.get(0);
            for (int i = 1; i < size && !done; i++) {
                if (previous != 0 && previous == column.get(i)) {
                    column.set(i, previous * 2);
                    scrollDown(column, i - 1);
                    done = true;
                    isOver = false;
                }
                previous = column.get(i);
            }
        } 

        return IntStream.range(0, size)
            .boxed().flatMap(i -> columns.stream()
                .map(colum -> colum.get(i)))
            .map(in -> {
                if (in == 0) {
                    return "";
                }
                return String.valueOf(in);
            }).toList();
    }
    private void scrollDown(List<Integer> column, int index) {
        if (index == 0) {
            column.set(0, 0);
            return; 
        }
        column.set(index, column.get(index - 1));
        scrollDown(column, index - 1);
    }
    public boolean isOver() {
        return isOver;
    }
    
}
