package webml.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import webml.base.entity.common.Base;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority extends Base {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authorityCd;
    private String authorityNm;
}
