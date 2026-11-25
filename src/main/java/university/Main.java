package university;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import university.model.Course;
import university.model.Student;
import university.repository.CourseRepository;
import university.repository.InMemoryCourseRepository;
import university.service.EnrollmentService;

public class Main {

    private static final Logger logger =
            LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        CourseRepository repository =
                new InMemoryCourseRepository();

        EnrollmentService service =
                new EnrollmentService(repository);

        Student anna = new Student(1, "Анна Смирнова");

        Course javaCourse =
                new Course(1, "Программирование на Java");

        Course mathCourse =
                new Course(2, "Математика");

        // Успешная запись
        service.enroll(anna, javaCourse);

        // Повторная запись
        try {
            service.enroll(anna, javaCourse);
        } catch (IllegalStateException exception) {
            logger.info("Duplicate enrollment scenario completed");
        }

        // Запись на другую дисциплину
        service.enroll(anna, mathCourse);

        // Проверка ERROR
        try {
            service.enroll(anna, null);
        } catch (IllegalArgumentException exception) {
            logger.info("Invalid enrollment scenario completed");
        }
    }
}