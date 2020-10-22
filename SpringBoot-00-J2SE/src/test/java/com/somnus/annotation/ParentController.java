package com.somnus.annotation;

import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kevin
 * @packageName com.somnus.annotation
 * @title: ParentController
 * @description: TODO
 * @date 2020/6/28 16:46
 */
@RestController
public class ParentController {

    @PostMapping(value = "signin")
    public String signin(@RequestParam(value = "username") String username){
        return username;
    }
    @Test
    public void test0(){
        System.out.println(AnnotationUtils.getDefaultValue(RequestParam.class));
        System.out.println(AnnotationUtils.getDefaultValue(RequestParam.class, "required"));
        System.out.println(AnnotationUtils.getDefaultValue(ParentController.class.getAnnotation(RequestParam.class)));
        System.out.println(AnnotationUtils.getDefaultValue(ParentController.class.getAnnotation(RequestParam.class), "required"));
    }

    @Test
    public void test1(){
        System.out.println(ParentController.class.getAnnotation(RestController.class));//非null
        System.out.println(ChildController.class.getAnnotation(RestController.class));//null
        System.out.println(ParentController.class.getAnnotation(ResponseBody.class));//null
        System.out.println(ChildController.class.getAnnotation(ResponseBody.class));//null
        System.out.println(ParentController.class.getAnnotation(RequestBody.class));//null
        System.out.println(ChildController.class.getAnnotation(RequestBody.class));//null
    }

    @Test
    @SneakyThrows
    public void test2(){
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class.getMethod("signin", new Class[]{String.class}), PostMapping.class));
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class.getAnnotation(RestController.class), RestController.class));
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class.getAnnotation(RestController.class), ResponseBody.class));
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class.getAnnotation(RestController.class), RequestBody.class));//null
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class, RestController.class));

        System.out.println(AnnotationUtils.findAnnotation(ParentController.class.getMethod("signin", new Class[]{String.class}), PostMapping.class));
        System.out.println(AnnotationUtils.findAnnotation(ParentController.class, RestController.class));
    }

    @Test
    public void test3(){
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class, RestController.class));//非null
        System.out.println(AnnotationUtils.getAnnotation(ChildController.class, RestController.class));//null
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class, ResponseBody.class));//非null
        System.out.println(AnnotationUtils.getAnnotation(ChildController.class, ResponseBody.class));//null
        System.out.println(AnnotationUtils.getAnnotation(ParentController.class, RequestBody.class));//null
        System.out.println(AnnotationUtils.getAnnotation(ChildController.class, RequestBody.class));//null
    }

    @Test
    public void test4(){
        System.out.println(AnnotationUtils.findAnnotation(ParentController.class, RestController.class));//非null
        System.out.println(AnnotationUtils.findAnnotation(ChildController.class, RestController.class));//非null
        System.out.println(AnnotationUtils.findAnnotation(ParentController.class, ResponseBody.class));//非null
        System.out.println(AnnotationUtils.findAnnotation(ChildController.class, ResponseBody.class));//非null
        System.out.println(AnnotationUtils.findAnnotation(ParentController.class, RequestBody.class));//null
        System.out.println(AnnotationUtils.findAnnotation(ChildController.class, RequestBody.class));//null
    }

    @Test
    public void test5(){
        System.out.println(AnnotatedElementUtils.isAnnotated(ParentController.class, RestController.class));
        System.out.println(AnnotatedElementUtils.getMergedAnnotation(ParentController.class, RestController.class));
        System.out.println(AnnotatedElementUtils.isAnnotated(ChildController.class, RestController.class));
        System.out.println(AnnotatedElementUtils.getMergedAnnotation(ChildController.class, RestController.class));
    }

    @Test
    public void test6(){
        System.out.println(AnnotatedElementUtils.hasAnnotation(ParentController.class, RestController.class));
        System.out.println(AnnotatedElementUtils.findMergedAnnotation(ParentController.class, RestController.class));
        System.out.println(AnnotatedElementUtils.hasAnnotation(ChildController.class, RestController.class));
        System.out.println(AnnotatedElementUtils.findMergedAnnotation(ChildController.class, RestController.class));
    }
}

