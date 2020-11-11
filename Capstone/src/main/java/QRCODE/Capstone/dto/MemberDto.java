package QRCODE.Capstone.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String username;

    private String name;

    private int age;

    private String phone;

    private String password;

}
