package webml.prj.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import webml.base.core.exception.MessageException;
import webml.base.util.PagingInfo;
import webml.prj.dto.RepairRegDto;
import webml.prj.dto.RepairSearchDto;
import webml.prj.service.PartnerService;
import webml.prj.service.RepairService;

import java.util.HashMap;
import java.util.Map;

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

        long totalCnt = repairService.getRepairListCnt(searchDto);
        PagingInfo pagingInfo = new PagingInfo(totalCnt, pageNum, 20);

        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("partnerList", partnerService.getPartnerList());
        model.addAttribute("repairList", repairService.getRepairList(pagingInfo, searchDto));
        return "prj/repair/repair_mng";
    }

    @ResponseBody
    @PostMapping
    public Map<String, Object> regRepair(@RequestBody @Valid RepairRegDto repairRegDto, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }

            if (repairRegDto.getReceiveDt() == null) {
                repairRegDto.setReceiveDtToday();
            }
            repairService.regRepair(repairRegDto);
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

}
