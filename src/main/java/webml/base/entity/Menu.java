package webml.base.entity;

import jakarta.persistence.*;
import lombok.*;
import webml.base.entity.common.Base;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends Base {

    @Id
    @GeneratedValue
    private Long menuIdx;
    private int menuOrder;
    private String menuNm;
    private String menuLink;

    private String visibleLinkYn;

    private String viewAuthority;
    private String saveAuthority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_idx")
    private Menu parent;

    @OrderBy("menuOrder asc")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Menu> childMenu;

    public void update(int menuOrder, String menuNm, String menuLink, String viewAuthority, String saveAuthority, Menu parent) {
        this.menuOrder = menuOrder;
        this.menuNm = menuNm;
        this.menuLink = menuLink;
        this.viewAuthority = viewAuthority;
        this.saveAuthority = saveAuthority;
        this.parent = parent;
    }
    public void update(String menuNm, String menuLink, String viewAuthority, String saveAuthority) {
        this.menuNm = menuNm;
        this.menuLink = menuLink;
        this.viewAuthority = viewAuthority;
        this.saveAuthority = saveAuthority;
    }
    public void update(String viewAuthority, String saveAuthority) {
        this.viewAuthority = viewAuthority;
        this.saveAuthority = saveAuthority;
    }
    public void updateOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }


}