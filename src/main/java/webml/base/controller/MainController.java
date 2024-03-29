package webml.base.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import webml.base.core.security.MemberDetails;
import webml.base.dto.MenuDto;
import webml.base.service.MenuService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MenuService menuService;

    @GetMapping("/")
    public String index(Model model) throws Exception {
        try {
            MemberDetails principal = (MemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<MenuDto> parentMenu = menuService.getSideMenuList(principal.getAuthoritiesStrArr());
            model.addAttribute("sidebar", parentMenu);
        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }
        return "index";
    }

}
