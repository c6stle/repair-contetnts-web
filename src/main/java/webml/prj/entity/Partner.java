package webml.prj.entity;

import jakarta.persistence.*;
import lombok.*;
import webml.prj.entity.common.Base;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Partner extends Base {

    @Id
    @GeneratedValue
    @Column(name = "partner_idx")
    private Long partnerIdx;

    private String partnerNm;

    @OneToMany(mappedBy = "partner")
    private List<Manager> managers;
}
