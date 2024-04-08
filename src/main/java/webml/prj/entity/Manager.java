package webml.prj.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Manager {

    @Id
    @GeneratedValue
    private Long managerIdx;

    @NotEmpty
    private String managerMail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_idx")
    private Partner partner;

    public void update(String managerMail) {
        this.managerMail = managerMail;
    }
}
