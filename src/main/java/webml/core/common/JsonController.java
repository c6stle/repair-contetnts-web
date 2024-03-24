package webml.core.common;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webml.core.exception.MessageException;
import webml.core.util.CustomMap;

@Controller
@RequestMapping("/json")
public class JsonController {

    @ResponseBody
    @GetMapping("/{s}/{m}")
    public Object get(@RequestParam CustomMap param,
                      @PathVariable("s") String serviceName,
                      @PathVariable("m") String methodName) throws Exception {
        //서비스명 파싱
        String serviceClassName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";

        //서비스 인스턴스 생성/반환
        return this.serviceMethod(param, serviceClassName, methodName);
    }


    @ResponseBody
    @PostMapping("/{s}/{m}")
    public Object post(@RequestBody CustomMap param,
                       @PathVariable("s") String serviceName,
                       @PathVariable("m") String methodName) throws Exception {
        String serviceClassName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";
        return this.serviceMethod(param, serviceClassName, methodName);
    }

    private Object serviceMethod(CustomMap param, String serviceClassName, String methodName) throws Exception {
        Object rtn;

        try {
            Class<?> classObj = Class.forName("webml.service." + serviceClassName);
            Object service = classObj.getDeclaredConstructor().newInstance();

            MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();

            bean.setTargetObject(service);
            bean.setTargetMethod(methodName);
            bean.setArguments(param);
            bean.prepare();
            rtn = bean.invoke();

            return rtn;
        } catch (Exception e) {
            throw new MessageException(e.getMessage());
        }
    }


}
