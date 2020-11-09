package QRCODE.Capstone.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberDto {

    private String username;

    private String name;

    private int age;

    private String phone;

    private String password;

}
