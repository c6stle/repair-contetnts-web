package webml.prj.entity;

import jakarta.persistence.*;
import lombok.*;
import webml.prj.entity.common.Base;

import java.time.LocalDateTime;

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
    private LocalDateTime receiveDt;

    private String delYn;
}
