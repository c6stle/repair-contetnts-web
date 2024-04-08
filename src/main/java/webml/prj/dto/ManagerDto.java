package webml.prj.dto;

import lombok.*;
import webml.prj.entity.Manager;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerDto {

    private Long managerIdx;

    private String managerMail;

    private Long partnerIdx;

    private String partnerNm;

    public ManagerDto(Manager manager) {
        this.managerIdx = manager.getManagerIdx();
        this.managerMail = manager.getManagerMail();
        this.partnerIdx = manager.getPartner().getPartnerIdx();
        this.partnerNm = manager.getPartner().getPartnerNm();
    }
}
