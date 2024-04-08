package webml.prj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import webml.prj.entity.Partner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartnerDto {

    private Long partnerIdx;

    @NotEmpty
    private String partnerNm;

    @NotEmpty
    private List<ManagerDto> managers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;

    private String regUserId;

    public PartnerDto(Partner partner) {
        this.partnerIdx = partner.getPartnerIdx();
        this.partnerNm = partner.getPartnerNm();
        this.managers = partner.getManagers().stream().map(ManagerDto::new).collect(Collectors.toList());
        this.regDt = partner.getRegDt();
        this.regUserId = partner.getRegUserId();
    }

    public void setPartnerIdx(Long partnerIdx) {
        this.partnerIdx = partnerIdx;
    }
}
