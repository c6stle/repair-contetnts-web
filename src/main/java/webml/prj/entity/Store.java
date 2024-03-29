package webml.prj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import webml.prj.entity.common.Base;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends Base {

    @Id
    @GeneratedValue
    @Column(name = "store_idx")
    private Long storeIdx;

    private String storeNm;
}
