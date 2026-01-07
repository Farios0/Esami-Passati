package a06.e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicsImpl implements Logics{
    private final List<Integer> cells;
    private Random random;
    private Pair<Integer, Integer> previous;
    private boolean bingo;
    private boolean canClose;
    private int counter = 1;

    public LogicsImpl(int size) {
        previous = new Pair<Integer,Integer>(0, -1);
        this.random = new Random();
        this.cells = new ArrayList<>();
        for (int i = 0; i < size * (size - 1) + size; i++) {
            cells.add(random.nextInt(6) + 1);
        }
        System.out.println(cells);
    }
    @Override
    public String hitcell(int position) {
        counter = (counter + 1) % 2; 
        int result = cells.get(position);
        if (result == previous.getX() && counter == 1 && position != previous.getY()) {
            bingo = true;
        } else {
            bingo = false;
            if (counter == 1) {
                canClose = true;
            } else {
                canClose = false;
                previous = new Pair<Integer,Integer>(result, position);
            }
        }
        return String.valueOf(result);
        
    }
    @Override
    public boolean isBingo() {
        return bingo;
    }
    @Override
    public int getPreviousPosition() {
        return previous.getY();
    }
    @Override
    public boolean canClose() {
        return canClose;
    }
}
