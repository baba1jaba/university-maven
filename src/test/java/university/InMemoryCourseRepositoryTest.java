package university;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;
import university.repository.InMemoryCourseRepository;

class InMemoryCourseRepositoryTest {
    @Test
    void findByIdReturnsSavedEnrollment() {
        CourseRepository repository = new InMemoryCourseRepository();
        Enrollment enrollment = new Enrollment(1, new Student(1, "Анна Смирнова"), new Course(1, "Математика"));

        repository.save(enrollment);

        assertSame(enrollment, repository.findById(1));
        assertNull(repository.findById(2));
    }

    @Test
    void findByStudentAndCourseMatchesBothIds() {
        CourseRepository repository = new InMemoryCourseRepository();
        Enrollment enrollment = new Enrollment(1, new Student(1, "Анна Смирнова"), new Course(1, "Математика"));
        repository.save(enrollment);

        assertSame(enrollment, repository.findByStudentAndCourse(1, 1));
        assertNull(repository.findByStudentAndCourse(2, 1));
        assertNull(repository.findByStudentAndCourse(1, 2));
    }
}
