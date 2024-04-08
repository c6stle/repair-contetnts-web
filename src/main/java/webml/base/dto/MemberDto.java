package webml.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import webml.base.entity.Member;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    
    private Long memberIdx;
    private String userId;
    @NotEmpty
    private String userNm;
    @NotNull
    private String authorityCd;
    private String authorityNm;
    private String regUserId;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginDt;

    public void setMemberIdx(Long memberIdx) {
        this.memberIdx = memberIdx;
    }

    public void setLastLoginDt(LocalDateTime lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public MemberDto(Member member) {
        this.memberIdx = member.getMemberIdx();
        this.userId = member.getUserId();
        this.userNm = member.getUserNm();
        this.lastLoginDt = member.getLastLoginDt();
        this.authorityCd = member.getAuthority().getAuthorityCd();
        this.authorityNm = member.getAuthority().getAuthorityNm();
        this.regUserId = member.getRegUserId();
    }
}
