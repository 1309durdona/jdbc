package net.idrok;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static StudentService studentService;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        studentService = context.getBean(StudentService.class);

        System.out.println("O'quvchi ma'lumotlari dasturiga xush kelibsiz!");

        while (true) {
            showMenu();
            int choice = getIntInput("\nTanlovingiz: ");

            switch (choice) {
                case 0 -> exitProgram();
                case 1 -> addStudent();
                case 2 -> deleteStudent();
                case 3 -> showAllStudents();
                case 4 -> updateStudent();
                case 5 -> searchStudentById();
                case 6 -> searchStudentByUsername();
                case 7 -> showStudentsWithPagination();
                default -> System.out.println("Noto'g'ri tanlov. Iltimos, qayta urinib ko'ring.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("QUYIDAGI AMALLARDAN BIRINI TANLANG:");
        System.out.println("=".repeat(50));
        System.out.println("1. O'quvchi qo'shish");
        System.out.println("2. O'quvchini o'chirish");
        System.out.println("3. O'quvchilar ro'yxatini ko'rish");
        System.out.println("4. O'quvchini tahrirlash");
        System.out.println("5. O'quvchi qidirish ID bo'yicha");
        System.out.println("6. O'quvchi qidirish username bo'yicha");
        System.out.println("7. Sahifalab ko'rish");
        System.out.println("0. Chiqish");
    }

    private static void addStudent() {
        try {
            System.out.print("O'quvchi ismi: ");
            String name = scanner.nextLine();
            System.out.print("O'quvchi username: ");
            String userName = scanner.nextLine();

            StudentDTO newStudent = StudentDTO.builder()
                    .name(name)
                    .userName(userName)
                    .createdDate(LocalDateTime.now())
                    .build();

            studentService.createStudent(newStudent);
            System.out.println("✅ Yangi o'quvchi muvaffaqiyatli qo'shildi.");

        } catch (Exception e) {
            System.out.println("❌ Xatolik: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        try {
            int deleteId = getIntInput("O'chiriladigan o'quvchi ID sini kiriting: ");
            studentService.deleteStudent(deleteId);
            System.out.println("✅ O'quvchi muvaffaqiyatli o'chirildi.");
        } catch (Exception e) {
            System.out.println("❌ Xatolik: " + e.getMessage());
        }
    }

    private static void showAllStudents() {
        try {
            List<StudentDTO> studentList = studentService.getAllStudents();

            System.out.println("\n+-----+-------------------------+------------------+---------------------+");
            System.out.println("| ID  | Name                    | Username         | Created Date        |");
            System.out.println("+-----+-------------------------+------------------+---------------------+");

            studentList.forEach(dto -> {
                String date = dto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                System.out.printf("| %-3d | %-23s | %-16s | %-19s |\n",
                        dto.getId(), dto.getName(), dto.getUserName(), date);
            });

            System.out.println("+-----+-------------------------+------------------+---------------------+");
            System.out.printf("Jami: %d ta o'quvchi\n", studentList.size());
        } catch (Exception e) {
            System.out.println("❌ Xatolik: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        try {
            int id = getIntInput("Tahrirlanadigan o'quvchi ID sini kiriting: ");
            StudentDTO existing = studentService.getStudentById(id);

            System.out.println("Joriy ma'lumotlar:");
            System.out.println("ID: " + existing.getId());
            System.out.println("Ism: " + existing.getName());
            System.out.println("Username: " + existing.getUserName());
            System.out.println("Yaratilgan sana: " + existing.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            System.out.print("\nYangi ism (hozirgi: '" + existing.getName() + "'): ");
            String newName = scanner.nextLine();
            System.out.print("Yangi username (hozirgi: '" + existing.getUserName() + "'): ");
            String newUsername = scanner.nextLine();

            StudentDTO updated = StudentDTO.builder()
                    .id(id)
                    .name(newName.isEmpty() ? existing.getName() : newName)
                    .userName(newUsername.isEmpty() ? existing.getUserName() : newUsername)
                    .createdDate(existing.getCreatedDate())
                    .build();

            studentService.updateStudent(id, updated);
            System.out.println("✅ O'quvchi ma'lumotlari muvaffaqiyatli yangilandi.");

        } catch (Exception e) {
            System.out.println("❌ Xatolik: " + e.getMessage());
        }
    }

    private static void searchStudentById() {
        try {
            int searchId = getIntInput("Qidiriladigan o'quvchi ID sini kiriting: ");
            StudentDTO student = studentService.getStudentById(searchId);

            System.out.println("\n🔍 Qidiruv natijasi:");
            System.out.println("+-----+-------------------------+------------------+---------------------+");
            System.out.println("| ID  | Name                    | Username         | Created Date        |");
            System.out.println("+-----+-------------------------+------------------+---------------------+");

            String date = student.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.printf("| %-3d | %-23s | %-16s | %-19s |\n",
                    student.getId(), student.getName(), student.getUserName(), date);
            System.out.println("+-----+-------------------------+------------------+---------------------+");

        } catch (Exception e) {
            System.out.println("❌ Xatolik: " + e.getMessage());
        }
    }

    private static void searchStudentByUsername() {
        try {
            System.out.print("Qidiriladigan username kiriting: ");
            String username = scanner.nextLine();

            List<StudentDTO> students = studentService.findByUsername(username);

            if (students.isEmpty()) {
                System.out.println("❌ Hech qanday o'quvchi topilmadi.");
                return;
            }

            System.out.println("\n🔍 Qidiruv natijalari (" + students.size() + " ta topildi):");
            System.out.println("+-----+-------------------------+------------------+---------------------+");
            System.out.println("| ID  | Name                    | Username         | Created Date        |");
            System.out.println("+-----+-------------------------+------------------+---------------------+");

            students.forEach(dto -> {
                String date = dto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                System.out.printf("| %-3d | %-23s | %-16s | %-19s |\n",
                        dto.getId(), dto.getName(), dto.getUserName(), date);
            });

            System.out.println("+-----+-------------------------+------------------+---------------------+");

        } catch (Exception e) {
            System.out.println("❌ Xatolik: " + e.getMessage());
        }
    }

    private static void showStudentsWithPagination() {
        try {
            int page = getIntInput("Sahifa raqami: ");
            int size = getIntInput("Sahifa o'lchami: ");

            if (page < 1 || size < 1) {
                System.out.println("❌ Sahifa raqami va o'lchami 1 dan katta bo'lishi kerak.");
                return;
            }

            List<StudentDTO> students = studentService.getStudentsWithPagination(page, size);
            int total = studentService.getTotalCount();
            int totalPages = (int) Math.ceil((double) total / size);

            if (students.isEmpty()) {
                System.out.println("❌ Hech qanday o'quvchi topilmadi.");
                return;
            }

            System.out.println("\n📄 Sahifa: " + page + " / " + totalPages + " (Jami: " + total + " ta o'quvchi)");
            System.out.println("+-----+-------------------------+------------------+---------------------+");
            System.out.println("| ID  | Name                    | Username         | Created Date        |");
            System.out.println("+-----+-------------------------+------------------+---------------------+");

            students.forEach(dto -> {
                String date = dto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                System.out.printf("| %-3d | %-23s | %-16s | %-19s |\n",
                        dto.getId(), dto.getName(), dto.getUserName(), date);
            });

            System.out.println("+-----+-------------------------+------------------+---------------------+");
            System.out.println("Sahifada: " + students.size() + " ta o'quvchi");

        } catch (Exception e) {
            System.out.println("❌ Xatolik: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // newline ni o'chirish
                return value;
            } catch (Exception e) {
                System.out.println("❌ Iltimos, raqam kiriting!");
                scanner.nextLine(); // noto'g'ri kiritilgan qiymatni o'chirish
            }
        }
    }

    private static void exitProgram() {
        System.out.println("\nDasturdan chiqish amalga oshirildi. Xayr!");
        scanner.close();
        System.exit(0);
    }
}