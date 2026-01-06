package a06.e1;

import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FluentParserFactoryImpl implements FluentParserFactory{

    private <T> FluentParser<T> withIterator(Iterator<T> iterator) {
        return t -> {
            if (iterator.hasNext() && iterator.next().equals(t)) {
                return withIterator(iterator);
            }
            throw new IllegalStateException("invalid data");
        };
    }

    @Override
    public FluentParser<Integer> naturals() {
        return withIterator(IntStream.iterate(0, i -> i + 1).iterator());
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return withIterator(Stream.iterate(0, i -> i + 1)
        .map(i -> IntStream.range(0, i).boxed().collect(Collectors.toList()))
        .iterator());
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return withIterator(Stream.iterate(0, i -> i + 1)
        .flatMap(i -> IntStream.range(0, i).boxed()).iterator());
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return withIterator(Stream.iterate(0, i -> i + 1).flatMap(i -> IntStream.range(0, i).mapToObj(in -> {
            StringBuilder result = new StringBuilder(s);
            for (int index = 0; index < in; index++) {
            result.append(s); }
            return result.toString();
        }
            )).iterator());
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return withIterator(Stream.iterate(i0, op).map(i -> new Pair<>(i, Stream.generate(() -> s).limit(i).collect(Collectors.toList()))).iterator());
    }
    
}
