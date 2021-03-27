package ru.test.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;
import ru.test.gwt.server.model.Person;
import ru.test.gwt.shared.dto.PersonDto;
import ru.test.gwt.shared.dto.StringDto;
import ru.test.gwt.shared.rest.SimpleRest;
import ru.test.gwt.shared.rpc.SimpleRpc;
import ru.test.gwt.shared.rpc.SimpleRpcAsync;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.dom.client.Style.Unit.PCT;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_CENTER;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;

public class Application implements EntryPoint {

    private static final SimpleRpcAsync rpc = GWT.create(SimpleRpc.class);
    private static final SimpleRest rest = GWT.create(SimpleRest.class);

    TextBox inputField;

    @Override
    public void onModuleLoad() {
        Defaults.setServiceRoot("/");

        final LayoutPanel mainPanel = new LayoutPanel();
        mainPanel.setWidth("100%");
        mainPanel.setHeight("100%");

        RootPanel.get().add(mainPanel);

         CellTable<PersonDto> table = createCellTable();

        mainPanel.add(table);
        //cell mode
        int cell = 0;
        final SingleSelectionModel<PersonDto> selectionModel
                = new SingleSelectionModel<>();
        table.setSelectionModel(selectionModel);


     //   mainPanel.setWidgetLeftWidth(table, 0, PCT, 50, PCT);
        //right panel
        final VerticalPanel verticalPanel = new VerticalPanel();
        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final Button newButton = new Button("Добавить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

            }
        });

        final Button editButton = new Button("Изменить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

            }
        });

        final Button deleteButton = new Button("Удалить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                doRestRequest(selectionModel.getSelectedObject());
               table.redraw();
            }
        });

        horizontalPanel.add(newButton);
        horizontalPanel.add(editButton);
        horizontalPanel.add(deleteButton);
        horizontalPanel.setSpacing(5);
        verticalPanel.add(horizontalPanel);
        //form
        mainPanel.add(verticalPanel);
        mainPanel.setWidgetLeftWidth(table, 0, PCT, 50, PCT);
        mainPanel.setWidgetRightWidth(verticalPanel, 0, PCT, 50, PCT);
    }
    private  CellTable<PersonDto> createCellTable() {
        CellTable<PersonDto> table = new CellTable<>();

        TextColumn<PersonDto> firstName = new TextColumn<PersonDto>() {
            @Override
            public String getValue(PersonDto person) {
                return person.getFirstName();
            }
        };
      //  firstName.setSortable(true);
        TextColumn<PersonDto> lastName = new TextColumn<PersonDto>() {
            @Override
            public String getValue(PersonDto person) {
                return person.getLastName();
            }
        };
       // lastName.setSortable(true);

        TextColumn<PersonDto> middleName = new TextColumn<PersonDto>() {
            @Override
            public String getValue(PersonDto person) {
                return person.getMiddleName();
            }
        };
        table.addColumn(firstName, "Имя");
        table.addColumn(lastName, "Фамилия");
        table.addColumn(middleName, "Отчество");
// Create a data provider.
        ListDataProvider<PersonDto> dataProvider = new ListDataProvider<>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(table);

        // Add the data to the data provider, which automatically pushes it to the
        // widget.
        List<PersonDto> list = dataProvider.getList();
        REST.withCallback(new MethodCallback<List<PersonDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("ошибка");
            }
            @Override
            public void onSuccess(Method method, List<PersonDto> dtoList) {
                list.addAll(dtoList);
            }
        }).call(rest).allPerson();




        return table;

    }

    private List<PersonDto>  doRestRequest(){
        List<PersonDto> list = new ArrayList<>();
         REST.withCallback(new MethodCallback<List<PersonDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("ошибка");
            }

            @Override
            public void onSuccess(Method method, List<PersonDto> dtoList) {
                list.addAll(dtoList);
            }
        }).call(rest).allPerson();
         return list;
    }

    private void   doRestRequest(PersonDto personDto){
        REST.withCallback(new MethodCallback<PersonDto>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("ошибка");
            }

            @Override
            public void onSuccess(Method method, PersonDto personDto) {
               Window.alert("delete");
            }
        }).call(rest).delete(String.valueOf(personDto.getId()));
    }
/*
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

 */

}
