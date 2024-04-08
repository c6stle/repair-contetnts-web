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
import webml.prj.dto.PartnerDto;
import webml.prj.service.PartnerService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    public String getPartnerList(Model model) {
        model.addAttribute("partners", partnerService.getPartnerList());
        return "prj/partner/partner_mng";
    }

    @GetMapping("/{partnerIdx}")
    public String getPartnerInfo(@PathVariable Long partnerIdx, Model model) {
        model.addAttribute("partner", partnerService.getPartnerInfo(partnerIdx));
        return "prj/partner/partner_edit";
    }

    @GetMapping("/new")
    public String getPartner() {
        return "prj/partner/partner_reg";
    }

    @ResponseBody
    @PostMapping
    public Map<String, Object> regPartner(@RequestBody @Valid PartnerDto partnerDto, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }
            partnerService.regPartner(partnerDto);
            rtnMap.put("message", "저장되었습니다.");
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    @ResponseBody
    @PostMapping("/{partnerIdx}")
    public Map<String, Object> updatePartner(@PathVariable Long partnerIdx,
                                             @RequestBody @Valid PartnerDto partnerDto, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }
            partnerDto.setPartnerIdx(partnerIdx);
            partnerService.updatePartner(partnerDto);
            rtnMap.put("message", "저장되었습니다.");
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    @ResponseBody
    @PutMapping("/{partnerIdx}") //delYn update 용도
    public Map<String, Object> deletePartner(@PathVariable Long partnerIdx) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            partnerService.deletePartner(partnerIdx);
            rtnMap.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

    @ResponseBody
    @DeleteMapping("/{managerIdx}")
    public Map<String, Object> deleteManager(@PathVariable Long managerIdx) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            partnerService.deleteManager(managerIdx);
            rtnMap.put("message", "삭제되었습니다.");
        } catch (Exception e) {
            log.error("", e);
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

}
