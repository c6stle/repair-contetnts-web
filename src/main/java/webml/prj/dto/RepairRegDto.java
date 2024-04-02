package webml.prj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RepairRegDto {

    @NotEmpty
    private Long partnerIdx;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receiveDt;

    @NotEmpty
    private String productVal;

    @NotEmpty
    private String specificVal;

    @NotEmpty
    private String repairContents;

    @NotEmpty
    private Long price;

    @NotEmpty
    private Long storeIdx;

    public void setReceiveDtToday() {
        this.receiveDt = LocalDateTime.now();
    }
}
