package webml.prj.dto;

import lombok.*;
import webml.prj.entity.Repair;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepairDto {

    private Long partnerIdx;
    private String partnerNm;

    private Long storeIdx;
    private String storeNm;

    private Long repairIdx;
    private String productVal;
    private String specificVal;
    private String repairContents;
    private Long price;
    private LocalDateTime receiveDt;

    public RepairDto(Repair repair) {
        this.partnerIdx = repair.getPartner().getPartnerIdx();
        this.partnerNm = repair.getPartner().getPartnerNm();
        this.storeIdx = repair.getStore().getStoreIdx();
        this.storeNm = repair.getStore().getStoreNm();
        this.repairIdx = repair.getRepairIdx();
        this.productVal = repair.getProductVal();
        this.specificVal = repair.getSpecificVal();
        this.repairContents = repair.getRepairContents();
        this.price = repair.getPrice();
        this.receiveDt = repair.getReceiveDt();
    }
}
