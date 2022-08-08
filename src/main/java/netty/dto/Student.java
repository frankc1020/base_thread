package netty.dto;

import lombok.Data;

/**
 * @author admin
 * @title: Student
 * @projectName base_thread
 * @description: TODO
 * @date 2022/8/4 14:50
 */
@Data
public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }
}
