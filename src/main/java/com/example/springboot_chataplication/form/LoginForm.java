package com.example.springboot_chataplication.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginForm {
    @NotBlank(message = "IDは必須です")
    @Length(min = 1,max=20)
    private String loginId;

    @NotBlank(message = "PASSは必須です")
    @Length(min = 1,max=50)
    private String password;
}
