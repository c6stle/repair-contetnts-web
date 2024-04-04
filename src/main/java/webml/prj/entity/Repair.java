package webml.prj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import webml.prj.entity.common.Base;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Repair extends Base {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_idx")
    private Partner partner;

    @Id
    @GeneratedValue
    private Long repairIdx;

    private String productVal;//제품번호

    private String specificVal;//고유번호

    private String repairContents;//수리내용

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_idx")
    private Store store;

    //수령 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receiveDt;

    public void update(Partner partner, LocalDate receiveDt, String productVal, String specificVal, String repairContents, Long price, Store store) {
        this.partner = partner;
        this.receiveDt = receiveDt;
        this.productVal = productVal;
        this.specificVal = specificVal;
        this.repairContents = repairContents;
        this.price = price;
        this.store = store;
    }
}
