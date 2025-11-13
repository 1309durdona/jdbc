package net.idrok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Integer id;
    private String name;
    private String userName;
    private LocalDateTime createdDate;


    public StudentDTO(String name, String userName, LocalDateTime now) {
        this.name = name;
        this.userName = userName;
        this.createdDate = now;
    }
}
