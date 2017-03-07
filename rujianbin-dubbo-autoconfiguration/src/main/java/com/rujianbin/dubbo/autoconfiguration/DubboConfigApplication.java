package com.rujianbin.dubbo.autoconfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:/dubbo/dubbo-config.xml"})
@ComponentScan({"com.rujianbin.dubbo.autoconfiguration"})
//@SpringBootApplication(scanBasePackages="com.rujianbin.dubbo.autoconfiguration")
public class DubboConfigApplication {

}
