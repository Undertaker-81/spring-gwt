package ru.test.gwt.shared.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;

public interface SimpleRpcAsync {
    void sayHello(String targetName, AsyncCallback<String> async);
}
