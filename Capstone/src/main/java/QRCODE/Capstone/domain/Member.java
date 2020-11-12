package QRCODE.Capstone.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "member")
    private List<OldPlace> oldPlaces = new ArrayList<>();


    private String currentPlace;
    private LocalDateTime localDateTime;

    private String name;
    private int age;
    private String phone;

}
