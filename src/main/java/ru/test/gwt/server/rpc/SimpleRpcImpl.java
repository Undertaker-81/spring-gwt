package ru.test.gwt.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.stereotype.Component;
import ru.test.gwt.server.service.HelloService;
import ru.test.gwt.shared.rpc.SimpleRpc;

@SuppressWarnings("GwtServiceNotRegistered")
@Component
public class SimpleRpcImpl extends RemoteServiceServlet implements SimpleRpc {

    private final HelloService helloService;

    public SimpleRpcImpl(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String targetName) {
        return helloService.sayHello(targetName);
    }

}
