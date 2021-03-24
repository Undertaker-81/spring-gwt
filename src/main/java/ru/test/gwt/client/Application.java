package ru.test.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;
import ru.test.gwt.shared.dto.StringDto;
import ru.test.gwt.shared.rest.SimpleRest;
import ru.test.gwt.shared.rpc.SimpleRpc;
import ru.test.gwt.shared.rpc.SimpleRpcAsync;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;

public class Application implements EntryPoint {

    private static final SimpleRpcAsync rpc = GWT.create(SimpleRpc.class);
    private static final SimpleRest rest = GWT.create(SimpleRest.class);

    TextBox inputField;

    @Override
    public void onModuleLoad() {
        Defaults.setServiceRoot("/");

        final VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setWidth("100%");
        mainPanel.setHeight("100%");
        mainPanel.setHorizontalAlignment(ALIGN_CENTER);
        mainPanel.setVerticalAlignment(ALIGN_MIDDLE);
        RootPanel.get().add(mainPanel);

        final FlowPanel centerPanel = new FlowPanel();
        mainPanel.add(centerPanel);

        centerPanel.add(new Label("Say hello to:"));
        inputField = new TextBox();
        centerPanel.add(inputField);
        final HorizontalPanel hPanel = new HorizontalPanel();
        centerPanel.add(hPanel);

        final Button rpcButton = new Button("RPC test", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                doRpcRequest();
            }
        });
        hPanel.add(rpcButton);

        final Button restButton = new Button("REST test", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                doRestRequest();
            }
        });
        hPanel.add(restButton);
    }

    private void doRpcRequest() {
        rpc.sayHello(inputField.getText(), new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                // TODO
            }

            @Override
            public void onSuccess(String s) {
                Window.alert(s);
            }
        });
    }

    private void doRestRequest(){
        REST.withCallback(new MethodCallback<StringDto>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                // TODO
            }

            @Override
            public void onSuccess(Method method, StringDto stringDto) {
                Window.alert(stringDto.getValue());
            }
        }).call(rest).sayHello(new StringDto(inputField.getText()));
    }

}
