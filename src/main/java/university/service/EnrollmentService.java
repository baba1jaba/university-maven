package university.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;

public class EnrollmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(EnrollmentService.class);

    private final CourseRepository repository;
    private int nextId = 1;

    public EnrollmentService(CourseRepository repository) {
        this.repository = repository;
    }

    /**
     * Записывает студента на курс.
     */
    public Enrollment enroll(Student student, Course course) {

        // ERROR: некорректные входные данные
        if (student == null || course == null) {
            logger.error(
                    "Enrollment failed: student or course is missing"
            );

            throw new IllegalArgumentException(
                    "Нужно указать студента и курс"
            );
        }

        // Проверка существующей записи
        Enrollment existing =
                repository.findByStudentAndCourse(
                        student.getId(),
                        course.getId()
                );

        // WARN: студент уже записан
        if (existing != null) {
            logger.warn(
                    "Student {} is already enrolled in course {}",
                    student.getName(),
                    course.getTitle()
            );

            throw new IllegalStateException(
                    "Студент уже записан на этот курс"
            );
        }

        // Создание новой записи
        Enrollment enrollment =
                new Enrollment(nextId, student, course);

        nextId++;

        repository.save(enrollment);

        // INFO: успешная запись
        logger.info(
                "Student {} successfully enrolled in course {}",
                student.getName(),
                course.getTitle()
        );

        return enrollment;
    }

    /**
     * Проверяет, записан ли студент на курс.
     */
    public boolean isEnrolled(Student student, Course course) {

        if (student == null || course == null) {
            throw new IllegalArgumentException(
                    "Нужно указать студента и курс"
            );
        }

        return repository.findByStudentAndCourse(
                student.getId(),
                course.getId()
        ) != null;
    }
}
