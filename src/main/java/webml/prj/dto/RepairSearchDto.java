package webml.prj.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepairSearchDto {
    private Integer pageNum;
    private String beginDt;
    private String endDt;
    private Long partnerIdx;
}
