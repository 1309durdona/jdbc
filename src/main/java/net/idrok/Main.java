package net.idrok;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        StudentRepository studentRepository = (StudentRepository) context.getBean("studentRepository");

        System.out.println("O'quvchi malumotlari dasturiga xush kelibsiz!");

        while (true) {

            System.out.println("\nQuyidagi amallardan birini tanlang:");
            System.out.println("1. O'quvchi qo'shish");
            System.out.println("2. O'quvchini o'chirish");
            System.out.println("3. O'quvchilar ro'yxatini ko'rish");
            System.out.println("4. O'quvchini tahrirlash");
            System.out.println("5. O'quvchi qidirish id buyicha");
            System.out.println("0. Chiqish");
            System.out.print("Tanlovingiz: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // To consume the newline character

            switch (choice) {
                case 0:
                    System.out.println("Dasturdan chiqish amalga oshirildi. Xayr!");
                    return;
                case 1:
                    System.out.print("O'quvchi ismi: ");
                    String name = scanner.nextLine();
                    System.out.print("O'quvchi username: ");
                    String userName = scanner.nextLine();
                    StudentDTO newStudent = new StudentDTO(name, userName, LocalDateTime.now());
                    studentRepository.save(newStudent);
                    System.out.println("Yangi o'quvchi qo'shildi.");
                    break;
                case 2:
                    System.out.print("O'chiriladigan o'quvchi ID sini kiriting: ");
                    int deleteId = scanner.nextInt();
                    studentRepository.deleteId(deleteId);
                    System.out.println("O'quvchi o'chirildi.");
                    break;
                case 3:
                    List<StudentDTO> studentList = studentRepository.getAllStudents();

                    System.out.println("\n+-----+-------------------------+------------------+---------------------+");
                    System.out.println("| ID  | Name                    | Username         | Created Date        |");
                    System.out.println("+-----+-------------------------+------------------+---------------------+");

                    studentList.forEach(dto -> {
                        String date = dto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        System.out.printf("| %-3d | %-23s | %-16s | %-19s |\n",
                                dto.getId(), dto.getName(), dto.getUserName(), date);
                    });

                    System.out.println("+-----+-------------------------+------------------+---------------------+");
                    System.out.printf("Total: %d students\n", studentList.size());
                    break;
                case 4:
                    // Edit student functionality can be implemented here
                    System.out.println("O'quvchini tahrirlash funksiyasi hozircha mavjud emas.");
                    break;

                case 5:
                    System.out.print("Qidiriladigan o'quvchi ID sini kiriting: ");
                    int searchId = scanner.nextInt();
                    StudentDTO student = studentRepository.getById(searchId);
                    if (student != null) {
                        System.out.println("O'quvchi topildi: " + student);
                    } else {
                        System.out.println("O'quvchi topilmadi.");
                    }
                    break;
                default:
                    System.out.println("Noto'g'ri tanlov. Iltimos, qayta urinib ko'ring.");
                    break;
            }



        }














    }
}