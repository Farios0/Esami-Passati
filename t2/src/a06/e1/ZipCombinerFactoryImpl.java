package a06.e1;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory{

    private IntStream utility(int size1, int size2) {
        return IntStream.range(0, size1 < size2? size1 : size2);
    }
    
    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
        return (l1, l2) -> {
            return utility(l1.size(), l2.size())
            .mapToObj(i -> new Pair<X, Y>(l1.get(i), l2.get(i))).collect(Collectors.toList());
        };
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return (l1, l2) -> {
            return utility(l1.size(), l2.size())
            .mapToObj(i -> new Pair<X, Y>(l1.get(i), l2.get(i))).filter(p -> predicate.test(p.get1())).map(p -> mapper.apply(p.get2())).toList();
            
        };
    }

    @Override
    public <Y> ZipCombiner<Integer, Y, List<Y>> taker() {
        return (l1, l2) -> {
            AtomicInteger counter = new AtomicInteger(0);
            return utility(l1.size(), l2.size()).mapToObj(i -> l1.get(i))
            .map(i -> l2.stream().skip(counter.getAndAdd(i)).limit(i).toList()).toList();
            
        };
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return (l1, l2) -> {
            List<Integer> zeroPositions = IntStream.range(0, l2.size()).filter(i -> l2.get(i) == 0).boxed().collect(Collectors.toList());
            int delta = -1;
            for (int i = 0; i < zeroPositions.size(); i++) {
                int temp = zeroPositions.get(i);
                zeroPositions.set(i, (temp - 1) - delta);
                delta = temp;
            }
            AtomicInteger counter = new AtomicInteger(0);
            return zeroPositions.stream().map(i -> new Pair<>(l1.get(counter.getAndIncrement()), i)).toList();
            
        };
    }
}
