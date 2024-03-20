package webml.core.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webml.core.util.CustomMap;

@Controller
@RequestMapping("/open")
public class OpenController {

    @GetMapping("/{d1}")
    public String get(HttpServletRequest request, Model model, @RequestBody(required = false) CustomMap param,
                      @PathVariable("d1") String d1) {
        model.addAttribute("param", param);
        return d1;
    }

    @GetMapping("/{d1}/{d2}")
    public String get(HttpServletRequest request, Model model, @RequestBody(required = false) CustomMap param,
                      @PathVariable("d1") String d1, @PathVariable("d2") String d2) {
        model.addAttribute("param", param);
        return d1 + "/" + d2;
    }

}
