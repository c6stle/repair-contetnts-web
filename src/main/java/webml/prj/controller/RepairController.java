package webml.prj.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import webml.base.core.exception.MessageException;
import webml.base.util.CustomMap;
import webml.base.util.PagingUtil;
import webml.base.util.WorkbookUtil;
import webml.prj.dto.RepairDto;
import webml.prj.dto.RepairSearchDto;
import webml.prj.service.PartnerService;
import webml.prj.service.RepairService;
import webml.prj.service.StoreService;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        PagingUtil pagingUtil = new PagingUtil(totalCnt, pageNum, 20);

        model.addAttribute("pagingInfo", pagingUtil);
        model.addAttribute("partnerList", partnerService.getPartnerList());
        model.addAttribute("repairList", repairService.getRepairList(searchDto, pagingUtil));
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
    public ResponseEntity<byte[]> downloadRepairs(@RequestBody RepairSearchDto searchDto) {
        Workbook workbook = null;

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            List<CustomMap> repairList = repairService.downloadRepairList(searchDto);
            workbook = new WorkbookUtil()
                    .setHead("날짜", "제품번호", "고유번호", "수리내용", "가격", "매장")
                    .setColNmList("receiveDt", "productVal", "specificVal", "repairContents", "price", "storeNm")
                    .setBody(repairList).getWorkbook();

            workbook.write(os);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;filename=%s.xlsx", URLEncoder.encode("진&솔 수리내역", StandardCharsets.UTF_8)))
                    .body(os.toByteArray());
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
    }

}
