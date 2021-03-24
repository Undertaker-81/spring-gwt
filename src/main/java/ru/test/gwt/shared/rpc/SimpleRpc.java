package ru.test.gwt.shared.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/simple")
public interface SimpleRpc extends RemoteService {

    String sayHello(String targetName);

}
