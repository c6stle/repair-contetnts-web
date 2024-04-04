package webml.prj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import webml.prj.entity.Repair;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepairDto {

    @NotNull
    private Long partnerIdx;
    private String partnerNm;

    private Long storeIdx;
    private String storeNm;

    private Long repairIdx;
    private String productVal;
    private String specificVal;

    @NotEmpty
    private String repairContents;

    @NotNull
    private Long price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receiveDt;

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

    public void setReceiveDtToday() {
        this.receiveDt = LocalDate.now();
    }

    public void setRepairIdx(Long repairIdx) {
        this.repairIdx = repairIdx;
    }
}
