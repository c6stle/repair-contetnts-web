package webml.prj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import webml.prj.service.PartnerService;
import webml.prj.service.StoreService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final StoreService storeService;

    private final PartnerService partnerService;

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("partnerList", partnerService.getPartnerList());
        model.addAttribute("storeList", storeService.getStoreList());
        return "prj/main";
    }
}
