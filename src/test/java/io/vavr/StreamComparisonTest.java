package io.vavr;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamComparisonTest {
    public Map<Integer, List<String>> userStatistic(List<User> users) {
        return users.stream()
                .filter(u -> u.getAge() >= 18)
                .collect(Collectors.groupingBy(User::getAge, Collectors.mapping(User::getName, Collectors.toList())));
    }

    public io.vavr.collection.Map<Integer, io.vavr.collection.List<String>> userStatistic(io.vavr.collection.List<User> users) {
        return users.filter(u -> u.getAge() >= 18)
                .groupBy(User::getAge)
                .mapValues(usersGroup -> usersGroup.map(User::getName));
    }

}
