package webml.prj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webml.prj.service.RepairService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/repair")
public class RepairController {

    private final RepairService repairService;

    @GetMapping
    public String getRepairList(Model model){

        model.addAttribute("repairList", repairService.getRepairList());
        return "prj/repair/repair_mng";
    }

}
