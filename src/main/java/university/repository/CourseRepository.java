package university.repository;

import university.model.Enrollment;

/**
 * Контракт хранилища записей на курсы.
 * Сервис зависит от этого интерфейса, а не от конкретного класса.
 * Задает контракт: любые методы сохранения и получения записей должны реализовывать этот интерфейс
 */
public interface CourseRepository {
    void save(Enrollment enrollment);

    Enrollment findById(int id);

    Enrollment findByStudentAndCourse(int studentId, int courseId);
}
