package university;

import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;
import university.repository.InMemoryCourseRepository;
import university.service.EnrollmentService;

/**
 * Точка входа. Main создает хранилище и передает его в сервис.
 */
public class Main {
    public static void main(String[] args) {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService service = new EnrollmentService(repository);

        Student anna = new Student(1, "Анна Смирнова");
        Course javaCourse = new Course(1, "Программирование на Java");
        Course mathCourse = new Course(2, "Математика");

        Enrollment first = service.enroll(anna, javaCourse);
        System.out.println("Оформлено: " + first);

        try {
            service.enroll(anna, javaCourse);
        } catch (IllegalStateException exception) {
            System.out.println("Отклонено: " + exception.getMessage());
        }

        Enrollment second = service.enroll(anna, mathCourse);
        System.out.println("Оформлено: " + second);
    }
}
