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
                case 2 -> showAllStudents();

                default -> System.out.println("Noto'g'ri tanlov. Iltimos, qayta urinib ko'ring.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("QUYIDAGI AMALLARDAN BIRINI TANLANG:");
        System.out.println("=".repeat(50));
        System.out.println("1. O'quvchi qo'shish");
        System.out.println("2. O'quvchilar ro'yxatini ko'rish");
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