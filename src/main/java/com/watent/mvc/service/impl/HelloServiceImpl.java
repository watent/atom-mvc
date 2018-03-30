package com.watent.mvc.service.impl;

import com.watent.mvc.annotation.Service;
import com.watent.mvc.service.HelloService;

/**
 * HelloService Impl
 *
 * @author Dylan
 * @date 2018/3/30 10:19
 */
@Service("helloServiceImpl")
public class HelloServiceImpl implements HelloService {

    @Override
    public String query(String param) {
        return null;
    }

    @Override
    public String insert(String param) {
        System.out.println("HelloServiceImpl.insert()");
        return null;
    }

    @Override
    public String update(String param) {
        System.out.println("HelloServiceImpl.update()");
        return null;
    }
}
