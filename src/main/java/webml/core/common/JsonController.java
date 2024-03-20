package webml.core.common;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webml.core.exception.MessageException;
import webml.core.util.CustomMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Controller
@RequestMapping("/json")
public class JsonController {

    @ResponseBody
    @PostMapping("/{s}/{m}")
    public Object post(@RequestBody CustomMap param,
                       @PathVariable("s") String serviceName,
                       @PathVariable("m") String methodName) throws Exception {
        //return
        Object rtn = null;

        //서비스명 파싱
        String serviceClassName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1, serviceName.length()) + "Service";

        //서비스 인스턴스 생성
        Class<?> classObj = Class.forName("webml.service." + serviceClassName);
        Object service = classObj.getDeclaredConstructor().newInstance();

        Parameter[] parameters = null;
        Method[] declaredMethods = service.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (methodName.equals(method.getName())) {
                parameters = method.getParameters();
                break;
            }
        }

        if (parameters == null) {
            throw new MessageException("Not Found Method");
        }

        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();

        bean.setTargetObject(service);
        bean.setTargetMethod(methodName);
        bean.setArguments(param);
        bean.prepare();
        rtn = bean.invoke();

        return rtn;
    }

}
