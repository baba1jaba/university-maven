package university.service;

import university.model.Course;
import university.model.Enrollment;
import university.model.Student;
import university.repository.CourseRepository;


public class EnrollmentService {
    private final CourseRepository repository;
    private int nextId = 1;

    public EnrollmentService(CourseRepository repository) {//деп инж
        this.repository = repository;
    }

    /**
     * Записывает студента на курс, если такой записи еще нет.
     */
    public Enrollment enroll(Student student, Course course) {
        if (student == null || course == null) {
            throw new IllegalArgumentException("Нужно указать студента и курс");
        }

        Enrollment existing = repository.findByStudentAndCourse(student.getId(), course.getId());
        if (existing != null) {
            throw new IllegalStateException("Студент уже записан на этот курс");
        }

        Enrollment enrollment = new Enrollment(nextId, student, course);
        nextId = nextId + 1;
        repository.save(enrollment);
        return enrollment;
    }
}
