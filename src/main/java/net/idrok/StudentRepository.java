package net.idrok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Barcha o'quvchilarni olish
    public List<StudentDTO> getAllStudents() {
        String sql = "SELECT * FROM student ORDER BY id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StudentDTO.class));
    }

    public void save(StudentDTO dto) {
        String sql = "INSERT INTO student (name, user_name, created_date) VALUES (?, ?, ?)";

        PreparedStatementSetter setter = ps -> {
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getUserName());
            ps.setObject(3, Timestamp.valueOf(LocalDateTime.now()));
        };

        jdbcTemplate.update(sql, setter);
    }

    public void deleteId(Integer id) {
        String sql = "DELETE FROM student WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}