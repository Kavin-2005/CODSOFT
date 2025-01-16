import java.util.*;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int enrolledStudents;
    String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
        this.schedule = schedule;
    }

    public boolean hasAvailableSlots() {
        return enrolledStudents < capacity;
    }
    public void registerStudent() {
        if (hasAvailableSlots()) {
            enrolledStudents++;
        } else {
            System.out.println("Sorry, no slots available for " + title);
        }
    }

    public void dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        } else {
            System.out.println("No students enrolled in " + title);
        }
    }

    @Override
    public String toString() {
        return "Course Code: " + courseCode + ", Title: " + title + "\nDescription: " + description +
                "\nSchedule: " + schedule + "\nEnrolled: " + enrolledStudents + "/" + capacity;
    }
}

class Student {
    String studentID;
    String name;
    List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerForCourse(Course course) {
        if (course.hasAvailableSlots()) {
            registeredCourses.add(course);
            course.registerStudent();
            System.out.println(name + " has been registered for " + course.title);
        } else {
            System.out.println("No slots available for " + course.title);
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
            System.out.println(name + " has dropped " + course.title);
        } else {
            System.out.println(name + " is not registered for " + course.title);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Student ID: " + studentID + ", Name: " + name + "\nRegistered Courses:\n");
        if (registeredCourses.isEmpty()) {
            sb.append("No courses registered.");
        } else {
            for (Course course : registeredCourses) {
                sb.append(course.title).append("\n");
            }
        }
        return sb.toString();
    }
}

public class CourseRegistrationSystem {

    private static Map<String, Course> courses = new HashMap<>();
    private static Map<String, Student> students = new HashMap<>();
    public static void initializeCourses() {
        courses.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basics of programming and computer science", 50, "Mon-Wed-Fri 10:00-11:00"));
        courses.put("MATH101", new Course("MATH101", "Calculus I", "Introduction to calculus and its applications", 40, "Tue-Thu 14:00-15:30"));
        courses.put("ENG101", new Course("ENG101", "English Literature", "Study of English literature from classic to modern works", 30, "Mon-Wed-Fri 13:00-14:00"));
        courses.put("BIO101", new Course("BIO101", "Biology Fundamentals", "Introduction to biology and life sciences", 25, "Tue-Thu 11:00-12:30"));
    }
    
    public static void displayCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses.values()) {
            System.out.println(course);
            System.out.println();
        }
    }
    public static void addStudent(String studentID, String name) {
        students.put(studentID, new Student(studentID, name));
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeCourses();
        addStudent("S001", "Alice");
        addStudent("S002", "Bob");
        addStudent("S003", "newton");

        System.out.println("Welcome to the Course Registration System!\n");

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Display available courses");
            System.out.println("2. Register for a course");
            System.out.println("3. Drop a course");
            System.out.println("4. Display student info");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayCourses();
                    break;

                case 2:
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    Student student = students.get(studentID);
                    if (student != null) {
                        displayCourses();
                        System.out.print("Enter the course code to register: ");
                        String courseCode = scanner.nextLine();
                        Course course = courses.get(courseCode);
                        if (course != null) {
                            student.registerForCourse(course);
                        } else {
                            System.out.println("Course not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextLine();
                    student = students.get(studentID);
                    if (student != null) {
                        System.out.print("Enter the course code to drop: ");
                        String dropCourseCode = scanner.nextLine();
                        Course dropCourse = courses.get(dropCourseCode);
                        if (dropCourse != null) {
                            student.dropCourse(dropCourse);
                        } else {
                            System.out.println("Course not found.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextLine();
                    student = students.get(studentID);
                    if (student != null) {
                        System.out.println(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting the system...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}