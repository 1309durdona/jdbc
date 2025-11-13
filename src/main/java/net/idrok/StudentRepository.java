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

    public List getAllStudents(){
        String sql = "SELECT * FROM student";
        List studentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(StudentDTO.class));
        return studentList;
    }

    public void save(StudentDTO dto) {
        String sql = "INSERT INTO student (name,user_name,created_date) values('%s','%s','%s')";
        sql = String.format(sql, dto.getName(), dto.getUserName(), dto.getCreatedDate());
        jdbcTemplate.update(sql);
    }

    public void deleteId(Integer id){
        String sql = "DELETE FROM student WHERE id = %d";
        sql = String.format(sql, id);
        jdbcTemplate.update(sql);
    }

    public StudentDTO getById(Integer id) {
        String sql = "SELECT * FROM student WHERE id = %d";
        sql = String.format(sql, id);
        StudentDTO dto = null;
        try {
            dto = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(StudentDTO.class));

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return dto;
    }
}
