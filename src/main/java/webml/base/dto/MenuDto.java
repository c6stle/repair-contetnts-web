package webml.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import webml.base.entity.Menu;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuDto {

    private Long menuIdx;
    private int menuOrder;
    @NotEmpty
    private String menuNm;
    private String menuLink;
    @NotEmpty
    private String viewAuthority;
    @NotEmpty
    private String saveAuthority;
    private Long parentMenuIdx;
    private String parentMenuNm;
    private List<MenuDto> childMenu;
    private String regUserId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDt;

    //메뉴 순서변경에 쓰이는 변수
    private Long bfParentMenuIdx; //상위 메뉴 변경시 menuOrder Reset 을 위한 변수
    private String parentMenuChgYn;

    public void setMenuIdx(Long menuIdx) {
        this.menuIdx = menuIdx;
    }

    public MenuDto(Menu menu) {
        this.menuIdx = menu.getMenuIdx();
        this.menuOrder = menu.getMenuOrder();
        this.menuNm = menu.getMenuNm();
        this.menuLink = menu.getMenuLink();
        this.viewAuthority = menu.getViewAuthority();
        this.saveAuthority = menu.getSaveAuthority();
        this.parentMenuIdx = menu.getParent() == null ? null : menu.getParent().getMenuIdx();
        this.parentMenuNm = menu.getParent() == null ? null : menu.getParent().getMenuNm();
        this.childMenu = menu.getChildMenu().stream().map(MenuDto::new).collect(Collectors.toList());
        this.regDt = menu.getRegDt();
        this.regUserId = menu.getRegUserId();
    }
}
