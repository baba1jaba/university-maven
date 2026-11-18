# Университет: запись студента на курс (ЛР №2 — Maven, ЛР №3 — Jenkins)

Вариант 6. Проект собирается Maven, а в ЛР №3 процесс сборки автоматизирован в Jenkins
через Pipeline, описанный в файле `Jenkinsfile`.

## Структура проекта

```
university-maven
├── pom.xml          описание проекта для Maven (ЛР №2)
├── Jenkinsfile      описание Pipeline для Jenkins (ЛР №3)  <-- НОВЫЙ ФАЙЛ
├── README.md
├── .gitignore
└── src
    ├── main/java/university
    │   ├── Main.java
    │   ├── model/       Student, Course, Enrollment
    │   ├── repository/  CourseRepository, InMemoryCourseRepository
    │   └── service/     EnrollmentService
    └── test/java/university
        ├── EnrollmentServiceTest.java
        └── InMemoryCourseRepositoryTest.java
```

## Что изменилось по сравнению с ЛР №2

| | ЛР №2 | ЛР №3 |
|---|---|---|
| Кто запускает `mvn compile / test / package` | вы, вручную в терминале | Jenkins, автоматически |
| Новый файл | — | `Jenkinsfile` |
| Код приложения и тесты | без изменений | без изменений |
| `pom.xml` | настройка Maven | без изменений (поправлен только комментарий) |
| Результаты тестов | `target/surefire-reports`, отчёт `target/reports/surefire.html` | то же, плюс отображение в интерфейсе Jenkins (шаг `junit`) |

## Pipeline

```
Build (mvn compile) -> Test (mvn test + публикация JUnit) -> Package (mvn package -DskipTests)
```

| Этап | Команда | Что делает |
|---|---|---|
| Build | `mvn compile` | компилирует `src/main` в `target/classes` |
| Test | `mvn test` | запускает 5 JUnit-тестов, формирует `target/surefire-reports/TEST-*.xml` |
| Test (post) | `junit 'target/surefire-reports/TEST-*.xml'` | публикует результаты тестов в Jenkins |
| Package | `mvn package -DskipTests` | собирает `target/university-enrollment-1.0-SNAPSHOT.jar` |

`-DskipTests` на этапе Package нужен, потому что тесты уже выполнены на этапе Test.
Если тест упал, этап Test завершается ошибкой и этап Package не выполняется.

## Индивидуальное задание ЛР №3 (вариант 6): результаты JUnit в Jenkins

- **Что было добавлено.** В `Jenkinsfile`, в этап `Test`, блок `post { always { junit '...' } }`.
- **Зачем.** Без него Jenkins показывает только текстовый `Console Output`. С шагом `junit` результаты
  видны по каждому тесту отдельно: сколько прошло, какой упал и почему, есть график по сборкам.
- **На каком этапе.** Этап `Test`, сразу после `mvn test`.
- **Где Maven сохраняет результаты JUnit.** `target/surefire-reports/`, файлы `TEST-<класс>.xml`
  (рядом лежат текстовые `.txt`). Их создаёт плагин `maven-surefire-plugin`.
- **Какая команда.** Шаг Pipeline `junit 'target/surefire-reports/TEST-*.xml'`
  (маска `TEST-*.xml` берёт только XML-результаты тестов).
- **Что при ошибке.** Блок `always` выполняется и при упавших тестах, поэтому результаты публикуются
  в любом случае; сборка при этом остаётся в статусе FAILURE, этап Package пропускается.
- **Как проверить.** После запуска откройте страницу сборки (`#N`): появится ссылка **Test Result**
  (5 тестов, 0 падений). На странице задания виден график **Test Result Trend**.

## Что нужно для работы

1. JDK 17+ и Maven (из ЛР №2), `mvn -v` должен работать.
2. Jenkins (Windows-служба), плагины Pipeline и JUnit (входят в Suggested plugins).
3. Maven должен быть виден Jenkins: его папка `bin` добавлена в **системную** переменную `Path`
   (не только в пользовательскую), после чего служба Jenkins перезапущена.
4. Проект скопирован в Workspace задания: `<JENKINS_HOME>\workspace\<имя задания>\`,
   так что `pom.xml` лежит прямо в этой папке, без лишней вложенной папки.

## Ручной запуск (ЛР №2)

```
mvn clean test       тесты + отчёт target/reports/surefire.html
mvn package          JAR в target/
java -jar target\university-enrollment-1.0-SNAPSHOT.jar
```
