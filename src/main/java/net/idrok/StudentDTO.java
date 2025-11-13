package net.idrok;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
public class StudentDTO {
    private Integer id;
    private String name;
    private String userName;
    private LocalDateTime createdDate;


    public StudentDTO() {
    }

    public StudentDTO(String name, String userName, LocalDateTime createdDate) {

        this.name = name;
        this.userName = userName;
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(userName, that.userName) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userName, createdDate);
    }

}
