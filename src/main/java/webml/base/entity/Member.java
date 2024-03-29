package webml.base.entity;

import jakarta.persistence.*;
import lombok.*;
import webml.base.entity.common.Base;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    private String userId;
    private String userNm;
    private String password;
    private LocalDateTime lastLoginDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_cd")
    private Authority authority;

    public void update(String userNm, Authority authority) {
        this.userNm = userNm;
        this.authority = authority;
    }

    public void update(LocalDateTime lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

}
