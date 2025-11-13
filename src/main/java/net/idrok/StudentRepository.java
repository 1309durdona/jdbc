package net.idrok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        jdbcTemplate.update(sql, dto.getName(), dto.getUserName(), dto.getCreatedDate());
    }

    public void deleteId(Integer id) {
        String sql = "DELETE FROM student WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public StudentDTO getById(Integer id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(StudentDTO.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Yangi metod: O'quvchini yangilash
    public void update(StudentDTO dto) {
        String sql = "UPDATE student SET name = ?, user_name = ? WHERE id = ?";
        jdbcTemplate.update(sql, dto.getName(), dto.getUserName(), dto.getId());
    }

    // Yangi metod: Username bo'yicha qidirish
    public List<StudentDTO> findByUsername(String username) {
        String sql = "SELECT * FROM student WHERE user_name ILIKE ? ORDER BY id";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(StudentDTO.class),
                "%" + username + "%");
    }

    // Yangi metod: Sahifalash (pagination)
    public List<StudentDTO> getStudentsWithPagination(int page, int size) {
        String sql = "SELECT * FROM student ORDER BY id LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(StudentDTO.class), size, offset);
    }

    // Yangi metod: Umumiy sonni olish
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM student";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}