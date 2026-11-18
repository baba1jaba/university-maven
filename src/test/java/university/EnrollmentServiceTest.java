package university;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;
import university.repository.InMemoryCourseRepository;
import university.service.EnrollmentService;
class EnrollmentServiceTest {
    @Test
    void enrollSucceedsWhenStudentIsNewOnCourse() {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService service = new EnrollmentService(repository);
        Student anna = new Student(1, "Анна Смирнова");
        Course javaCourse = javaCourse();

        Enrollment enrollment = service.enroll(anna, javaCourse);

        assertEquals(1, enrollment.getId());
        assertSame(anna, enrollment.getStudent());
        assertSame(javaCourse, enrollment.getCourse());
        assertSame(enrollment, repository.findById(1));
    }

    @Test
    void enrollFailsWhenStudentOrCourseIsMissing() {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService service = new EnrollmentService(repository);

        assertThrows(IllegalArgumentException.class, () ->
                service.enroll(new Student(1, "Анна Смирнова"), null)
        );
        assertNull(repository.findById(1));
    }

    @Test
    void enrollFailsWhenStudentAlreadyEnrolled() {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService service = new EnrollmentService(repository);
        Student anna = new Student(1, "Анна Смирнова");
        Student boris = new Student(2, "Борис Козлов");
        Course javaCourse = javaCourse();
        Course mathCourse = new Course(2, "Математика");

        service.enroll(anna, javaCourse);

        assertThrows(IllegalStateException.class, () -> service.enroll(anna, javaCourse));
        assertNull(repository.findById(2));

        Enrollment otherCourse = service.enroll(anna, mathCourse);
        assertEquals(2, otherCourse.getId());

        Enrollment otherStudent = service.enroll(boris, javaCourse);
        assertEquals(3, otherStudent.getId());
    }
    @Test
    void isEnrolledReturnsTrueOnlyAfterEnroll() {
        CourseRepository repository = new InMemoryCourseRepository();
        EnrollmentService service = new EnrollmentService(repository);
        Student anna = new Student(1, "Анна Смирнова");
        Course javaCourse = javaCourse();

        assertFalse(service.isEnrolled(anna, javaCourse));

        service.enroll(anna, javaCourse);

        assertFalse(service.isEnrolled(anna, javaCourse));
        assertFalse(service.isEnrolled(anna, new Course(2, "Математика")));
        assertThrows(IllegalArgumentException.class, () -> service.isEnrolled(anna, null));
    }
    private Course javaCourse() {
        return new Course(1, "Программирование на Java");
    }
}
