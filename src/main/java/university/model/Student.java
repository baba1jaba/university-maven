package university.model;

/**
 * Студент. Хранит только номер и имя.
 * Не знает, записан ли он уже на курс.
 */
public class Student {
    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
