package com.example.ebs.dto;
import jakarta.validation.constraints.NotBlank; import lombok.Data;
@Data public class UserForm { @NotBlank private String username; @NotBlank private String password; private String role="ROLE_CLERK"; }
