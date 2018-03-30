package com.watent.mvc.servlet;

import com.watent.mvc.annotation.Controller;
import com.watent.mvc.annotation.Qualifier;
import com.watent.mvc.annotation.RequestMapping;
import com.watent.mvc.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手写请求分发服务 web.xml 拦截所有请求
 *
 * @author Dylan
 * @date 2018/3/26 16:34
 */
public class DispatcherServlet extends HttpServlet {

    /**
     * 包扫描下所有类全限定名
     */
    private List<String> packageNames = new ArrayList<>();

    /**
     * 所有类实例
     * key:     annotation value 值
     * value:   实例
     */
    Map<String, Object> instanceMap = new HashMap<>();
    /**
     * URL路径和处理方法映射
     */
    Map<String, Object> handlerMap = new HashMap<>();

    public DispatcherServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();
        //工程名
        String context = req.getContextPath();
        String path = uri.replace(context, "");

        Method method = (Method) handlerMap.get(path);

        Object obj = instanceMap.get("/" + path.split("/")[1]);

        try {
            method.invoke(obj, req, resp, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        scanPackage("com.watent.mvc");

        try {
            filterAndInstance();
            ioc();
            handlerMap();

            for (Map.Entry entry : handlerMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void handlerMap() {

        if (instanceMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {

            if (entry.getValue().getClass().isAnnotationPresent(Controller.class)) {

                Controller controller = entry.getValue().getClass().getAnnotation(Controller.class);
                String nameSpace = controller.value();
                Method[] methods = entry.getValue().getClass().getDeclaredMethods();
                for (Method method : methods) {

                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String path = requestMapping.value();
                        String fullPath = nameSpace + path;
                        handlerMap.put(fullPath, method);
                    }
                }
            }
        }
    }

    private void ioc() {

        if (instanceMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Qualifier.class)) {
                    Qualifier annotation = field.getAnnotation(Qualifier.class);
                    String value = annotation.value();
                    try {
                        field.set(entry.getValue(), instanceMap.get(value));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void filterAndInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (packageNames.isEmpty()) {
            return;
        }
        for (String packageName : packageNames) {
            Class clazz = Class.forName(packageName.replace(".class", ""));
            if (clazz.isAnnotationPresent(Controller.class)) {
                Object instance = clazz.newInstance();
                Controller annotation = (Controller) clazz.getAnnotation(Controller.class);
                String key = annotation.value();
                instanceMap.put(key, instance);
            } else if (clazz.isAnnotationPresent(Service.class)) {
                Object instance = clazz.newInstance();
                Service annotation = (Service) clazz.getAnnotation(Service.class);
                String key = annotation.value();
                instanceMap.put(key, instance);
            }
        }
    }


    private void scanPackage(String basePackage) {

        URL url = this.getClass().getClassLoader().getResource("/" + replaceTo(basePackage));
        String pathFile = url.getFile();
        File file = new File(pathFile);
        String[] files = file.list();
        for (String path : files) {
            File eachFile = new File(pathFile + path);
            if (eachFile.isDirectory()) {
                System.out.println(basePackage + "." + eachFile.getName());
                scanPackage(basePackage + "." + eachFile.getName());
            } else {
                System.out.println(basePackage + "." + eachFile.getName());
                packageNames.add(basePackage + "." + eachFile.getName());
            }
        }
    }

    private String replaceTo(String path) {
        return path.replaceAll("\\.", File.separator);
    }
}
