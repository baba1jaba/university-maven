package university.model;

/**
 * Одна запись студента на курс.
 * Проверку «уже записан» делает сервис.
 */
public class Enrollment {
    private final int id;// финал значение полей можно задать только один единственный раз
    private final Student student;
    private final Course course;

    public Enrollment(int id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "Запись №" + id
                + ": " + student.getName()
                + " на курс " + course.getTitle();
    }
}
