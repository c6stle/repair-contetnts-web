package webml.prj.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import webml.prj.entity.Store;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDto {
    private Long storeIdx;

    @NotEmpty
    private String storeNm;

    private String storeAddress;

    public StoreDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.storeNm = store.getStoreNm();
        this.storeAddress = store.getStoreAddress();
    }
}
