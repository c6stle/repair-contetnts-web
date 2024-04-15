package webml.prj.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import webml.prj.dto.RepairSearchDto;
import webml.prj.dto.StatisticsResponseDto;
import webml.prj.service.PartnerService;
import webml.prj.service.RepairService;
import webml.prj.service.StoreService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final RepairService repairService;

    private final StoreService storeService;

    private final PartnerService partnerService;

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("partnerList", partnerService.getPartnerList());
        model.addAttribute("storeList", storeService.getStoreList());

        List<StatisticsResponseDto> statistics = repairService.statistics();

        String countIncreaseYn;
        String sumIncreaseYn;
        double countPer = 0;
        double sumPer = 0;

        StatisticsResponseDto nowMonth = statistics.get(statistics.size() - 1);
        if (statistics.size() > 1) {
            StatisticsResponseDto beforeMonth = statistics.get(statistics.size() - 2);
            Long nowCount = nowMonth.getCount();
            Long nowSum = nowMonth.getSum();
            Long beforeCount = beforeMonth.getCount();
            Long beforeSum = beforeMonth.getSum();

            countIncreaseYn = nowCount > beforeCount ? "Increase" : "Decrease";
            sumIncreaseYn = nowCount > beforeCount ? "Increase" : "Decrease";
            countPer = nowCount > beforeCount ? nowCount * 100.00 / beforeCount : beforeCount * 100.00 / nowCount;
            sumPer = nowSum > beforeSum ? nowSum * 100.00 / beforeSum : beforeSum * 100.00 / nowSum;
        } else {
            countIncreaseYn = "-";
            sumIncreaseYn = "-";
        }

        nowMonth.setIncreaseYn(countIncreaseYn, sumIncreaseYn);
        nowMonth.setPercent(countPer, sumPer);

        model.addAttribute("statistics", statistics);
        model.addAttribute("nowMonth", nowMonth);
        model.addAttribute("totalCnt", repairService.getRepairListCnt(new RepairSearchDto(null, null, null, null)));
        return "prj/main";
    }
}
