package domain;

import controller.dto.UserUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @ManyToOne(targetEntity = Major.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List<TakeClass> takeClasses;

    @Builder(builderClassName = "UserSignUpBuilder", builderMethodName = "signupBuilder")
    public User(String loginId, String password, String username, Major major, String email, String phoneNumber){
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.major = major;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = Role.STUDENT;
        this.takeClasses = new ArrayList<>();
    }

    public void update(UserUpdateRequestDto requestDto){
        this.email = requestDto.getEmail();
        this.phoneNumber = requestDto.getPhoneNumber();
    }

    // 수강신청
    public void registration(TakeClass takeClass){
        this.takeClasses.add(takeClass);
    }

    // 수강취소
    public void cancel(TakeClass takeClass){
        this.takeClasses.remove(takeClass);
    }

    //public Collections<? extends GrantedAuthority>


}
