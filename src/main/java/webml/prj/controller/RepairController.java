package webml.prj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import webml.base.util.PagingInfo;
import webml.prj.dto.RepairSearchDto;
import webml.prj.service.PartnerService;
import webml.prj.service.RepairService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/repair")
public class RepairController {

    private final RepairService repairService;

    private final PartnerService partnerService;

    @GetMapping
    public String getRepairList(@ModelAttribute(name = "searchForm") RepairSearchDto searchDto, Model model){
        searchDto = searchDto == null ? new RepairSearchDto(1, null, null, null) : searchDto;
        int pageNum = searchDto.getPageNum() == null ? 1 : searchDto.getPageNum();
        log.info("param : {}", searchDto);

        PagingInfo pagingInfo = new PagingInfo(repairService.getRepairListCnt(searchDto), pageNum, 20);

        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("partnerList", partnerService.getPartnerList());
        model.addAttribute("repairList", repairService.getRepairList(pagingInfo, searchDto));
        return "prj/repair/repair_mng";
    }

}
