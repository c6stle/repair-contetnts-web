package webml.prj.dto;

import com.querydsl.core.Tuple;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatisticsResponseDto {
    private String month;
    private Long count;
    private Long sum;
    private String countIncreaseYn;
    private String sumIncreaseYn;
    private double countPer;
    private double sumPer;

    public StatisticsResponseDto(Tuple tuple) {
        this.month = tuple.get(0, String.class);
        this.count = tuple.get(1, Long.class);
        this.sum = tuple.get(2, Long.class);
    }

    public void setIncreaseYn(String countIncreaseYn, String sumIncreaseYn) {
        this.countIncreaseYn = countIncreaseYn;
        this.sumIncreaseYn = sumIncreaseYn;
    }

    public void setPercent(double countPer, double sumPer) {
        this.countPer = countPer;
        this.sumPer = sumPer;
    }
}
