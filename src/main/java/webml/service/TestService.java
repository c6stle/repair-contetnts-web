package webml.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import webml.core.util.CustomMap;

@Slf4j
@Service
public class TestService {

    public CustomMap method1(CustomMap param) {
        CustomMap rtnMap = new CustomMap();
        log.info("TestService.method1 param : {}", param);
        rtnMap.put("inputMap", param);
        return rtnMap;
    }

    public CustomMap method2(CustomMap param) {
        CustomMap rtnMap = new CustomMap();
        log.info("TestService.method2 param : {}", param);
        rtnMap.put("inputMap", param);
        return rtnMap;
    }


}
