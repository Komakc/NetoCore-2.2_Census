package sar.game;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        
        //Поиск несовершеннолетних
        long countMiner = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println(countMiner + " miners of " + persons.size());

        //Поиск призывников
        List<String> listMan = persons.stream()
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .filter(person -> person.getSex() == Sex.MAN)
                .map(person -> person.getFamily())
                .limit(10)
                .collect(Collectors.toList());
        System.out.println(listMan);

        //Поиск работоспособных
        List<Person> listWorkers = persons.stream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> (person.getSex() == Sex.MAN && person.getAge() >= 18 && person.getAge() <= 65)
                        || (person.getSex() == Sex.WOMAN && person.getAge() >= 18 && person.getAge() <= 60))
                .sorted(Comparator.comparing(person -> person.getFamily()))
                .limit(10)
                .collect(Collectors.toList());
        for (Person listWorker : listWorkers) {
            System.out.println(listWorker);
        }
    }
}