package a02a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers{

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        if (list.isEmpty()) {return null;}
        return new RecursiveIteratorImpl<>(0, list);           
        };
    

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> result = new ArrayList<>();
        for (int i = 0; i < max && input != null; i++) {
            result.add(input.getElement());
            input = input.next();
        }
        return result;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        List<Pair<X, Y>> needed = new ArrayList<>();
        while (first != null && second != null) {
            needed.add(new Pair<X,Y>(first.getElement(), second.getElement()));
            first = first.next();
            second = second.next();
        }
        return fromList(needed); 
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        var itcopy = iterator;
        int length = 0;
        while (itcopy != null) {
            length++;
            itcopy = itcopy.next();
        }
        var second = fromList(IntStream.range(0, length).boxed().toList());
        return zip(iterator, second);
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        List<X> needed = new ArrayList<>();
        
        while (first != null && second != null) {
            needed.add(first.getElement());
            needed.add(second.getElement());
            
            first = first.next();
            second = second.next();
        }
        while (first != null) {
            needed.add(first.getElement());
            first = first.next();
        }

        while (second != null) {
            needed.add(second.getElement());
            second = second.next();
        }
        return fromList(needed);
    }
    private class RecursiveIteratorImpl<X> implements RecursiveIterator<X> {
            private int counter;
            private final List<X> lista;

            RecursiveIteratorImpl(int counter, List<X> list) {
                this.counter = counter;
                lista = List.copyOf(list);
            }

            @Override
            public X getElement() {
                return lista.get(counter);
            }

            @Override
            public RecursiveIterator<X> next() {
                if (lista.size() == counter + 1) {return null;}
                return new RecursiveIteratorImpl<>(counter + 1, lista);
            }
    }
}
