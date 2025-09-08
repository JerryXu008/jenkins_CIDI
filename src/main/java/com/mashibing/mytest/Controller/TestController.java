package com.mashibing.mytest.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
   @GetMapping("/hello")
    public  String test(){

       return "111滚动更新  AAAAAAAAAhello ,jenkins-latest！！！！！";
   }
}
