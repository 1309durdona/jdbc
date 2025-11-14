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


    public List<StudentDTO> getAllStudents() {
        return studentRepository.getAllStudents();
    }

}