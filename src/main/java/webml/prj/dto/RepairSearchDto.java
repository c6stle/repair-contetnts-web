package webml.prj.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepairSearchDto {
    private Integer pageNum;
    private String beginDt;
    private String endDt;
    private Long partnerIdx;
}
