package university.repository;

import java.util.ArrayList;
import java.util.List;
import university.model.Enrollment;

/**
 * Хранит записи в списке внутри программы.
 * Базы данных нет. Это одна из реализаций CourseRepository.
 * Хранит список зачислений прямо в оперативной памяти
 */
public class InMemoryCourseRepository implements CourseRepository {
    private final List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    @Override
    public Enrollment findById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        return null;
    }

    @Override
    public Enrollment findByStudentAndCourse(int studentId, int courseId) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().getId() == studentId && enrollment.getCourse().getId() == courseId) {
                return enrollment;
            }
        }
        return null;
    }
}
