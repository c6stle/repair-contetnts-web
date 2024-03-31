package webml.prj.dto;

import lombok.*;
import webml.prj.entity.Partner;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartnerDto {

    private Long partnerIdx;

    private String partnerNm;

    private List<ManagerDto> managers;

    public PartnerDto(Partner partner) {
        this.partnerIdx = partner.getPartnerIdx();
        this.partnerNm = partner.getPartnerNm();
        this.managers = partner.getManagers().stream().map(ManagerDto::new).collect(Collectors.toList());
    }
}
