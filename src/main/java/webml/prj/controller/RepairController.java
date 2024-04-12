package webml.prj.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import webml.base.core.exception.MessageException;
import webml.base.util.PagingInfo;
import webml.prj.dto.RepairDto;
import webml.prj.dto.RepairSearchDto;
import webml.prj.service.PartnerService;
import webml.prj.service.RepairService;
import webml.prj.service.StoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/repair")
public class RepairController {

    private final RepairService repairService;

    private final PartnerService partnerService;

    private final StoreService storeService;

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
    public Map<String, Object> regRepair(@RequestBody @Valid RepairDto repairRegDto, BindingResult bindingResult) {
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
            rtnMap.put("message", "저장되었습니다.");
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    @GetMapping("/{repairIdx}")
    public String getRepairInfo(@PathVariable Long repairIdx, Model model){
        model.addAttribute("partnerList", partnerService.getPartnerList());
        model.addAttribute("storeList", storeService.getStoreList());
        model.addAttribute("repairInfo", repairService.getRepairInfo(repairIdx));
        return "prj/repair/repair_edit";
    }

    @ResponseBody
    @PostMapping("/{repairIdx}")
    public Map<String, Object> updateRepair(@PathVariable Long repairIdx,
                                            @RequestBody @Valid RepairDto repairUpdateDto,
                                            BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }

            repairUpdateDto.setRepairIdx(repairIdx);
            repairService.updateRepair(repairUpdateDto);
            rtnMap.put("message", "저장되었습니다.");
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    @ResponseBody
    @DeleteMapping("/{repairIdx}")
    public Map<String, Object> deleteRepair(@PathVariable Long repairIdx) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            repairService.deleteRepair(repairIdx);
            rtnMap.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/excel")
    public Workbook downloadRepairs(RepairSearchDto searchDto) {
        Workbook workbook = null;

        List<RepairDto> repairList = repairService.downloadRepairList(searchDto);


        return workbook;
    }

}
