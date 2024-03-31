package webml.prj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webml.base.util.PagingInfo;
import webml.prj.service.RepairService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/repair")
public class RepairController {

    private final RepairService repairService;

    @GetMapping
    public String getRepairList(Model model, @RequestParam int pageNum){
        model.addAttribute("pagingInfo", new PagingInfo(repairService.getRepairListCnt(), pageNum, 20));
        model.addAttribute("repairList", repairService.getRepairList());
        return "prj/repair/repair_mng";
    }

}
