package net.idrok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void createStudent(StudentDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Ism maydoni bo'sh bo'lmasligi kerak");
        }
        if (dto.getUserName() == null || dto.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("Username maydoni bo'sh bo'lmasligi kerak");
        }

        studentRepository.save(dto);
    }

    public void updateStudent(Integer id, StudentDTO dto) {
        StudentDTO existing = studentRepository.getById(id);
        if (existing == null) {
            throw new IllegalArgumentException("O'quvchi topilmadi");
        }
        studentRepository.update(dto);
    }

    public void deleteStudent(Integer id) {
        if (studentRepository.getById(id) == null) {
            throw new IllegalArgumentException("O'quvchi topilmadi");
        }
        studentRepository.deleteId(id);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public StudentDTO getStudentById(Integer id) {
        StudentDTO student = studentRepository.getById(id);
        if (student == null) {
            throw new IllegalArgumentException("O'quvchi topilmadi");
        }
        return student;
    }

    public List<StudentDTO> findByUsername(String username) {
        return studentRepository.findByUsername(username);
    }

    public List<StudentDTO> getStudentsWithPagination(int page, int size) {
        return studentRepository.getStudentsWithPagination(page, size);
    }

    public int getTotalCount() {
        return studentRepository.getTotalCount();
    }
}