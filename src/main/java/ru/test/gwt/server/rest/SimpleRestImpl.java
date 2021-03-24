package ru.test.gwt.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.gwt.server.service.HelloService;
import ru.test.gwt.shared.dto.StringDto;
import ru.test.gwt.shared.rest.SimpleRest;

@RequestMapping("rest/simple")
@RestController
public class SimpleRestImpl implements SimpleRest {

    @Autowired
    private HelloService helloService;

    @PostMapping("hello")
    @Override
    public StringDto sayHello(@RequestBody StringDto targetName) {
        return helloService.sayHello(targetName);
    }

}
