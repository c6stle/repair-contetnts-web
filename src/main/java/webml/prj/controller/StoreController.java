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
import webml.prj.dto.StoreDto;
import webml.prj.service.StoreService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public String getStore(Model model) {
        model.addAttribute("stores", storeService.getStoreList());
        return "prj/store/store_mng";
    }

    @ResponseBody
    @PostMapping
    public Map<String, Object> regStore(@RequestBody @Valid StoreDto storeDto, BindingResult bindingResult) {
        Map<String, Object> rtnMap = new HashMap<>();
        try {
            if (bindingResult.hasErrors()) {
                FieldError error = bindingResult.getFieldErrors().get(0);
                throw new MessageException(error.getDefaultMessage());
            }
            storeService.save(storeDto);
            rtnMap.put("message", "저장되었습니다.");
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
        return rtnMap;
    }

}
