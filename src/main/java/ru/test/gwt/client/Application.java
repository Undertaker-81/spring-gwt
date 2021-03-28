package ru.test.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;
import ru.test.gwt.shared.dto.PersonDto;
import ru.test.gwt.shared.rest.SimpleRest;
import ru.test.gwt.shared.rpc.SimpleRpc;
import ru.test.gwt.shared.rpc.SimpleRpcAsync;

import java.util.List;

import static com.google.gwt.dom.client.Style.Unit.PCT;

public class Application implements EntryPoint {

    private static final SimpleRpcAsync rpc = GWT.create(SimpleRpc.class);
    private static final SimpleRest rest = GWT.create(SimpleRest.class);

    private TextBox firstName;
    private TextBox lastName;
    private TextBox middleName;
    private boolean isNewPerson = false;


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
        final SingleSelectionModel<PersonDto> selectionModel
                = new SingleSelectionModel<>();
        table.setSelectionModel(selectionModel);


     //   mainPanel.setWidgetLeftWidth(table, 0, PCT, 50, PCT);
        //right panel
        final VerticalPanel verticalPanel = new VerticalPanel();
        final HorizontalPanel horizontalPanel = new HorizontalPanel();
        final  VerticalPanel formPanel = new VerticalPanel();
        final HorizontalPanel buttonPanel = new HorizontalPanel();
        final Button newButton = new Button("Добавить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (!formPanel.isVisible())
               formPanel.setVisible(true);
                isNewPerson = true;
            }
        });

        final Button editButton = new Button("Изменить", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (!formPanel.isVisible())
                    formPanel.setVisible(true);
                PersonDto personDto = selectionModel.getSelectedObject();
                firstName.setText(personDto.getFirstName());
                lastName.setText(personDto.getLastName());
                middleName.setText(personDto.getMiddleName());
                isNewPerson = false;
            }
        });

        final Button deleteButton = new Button("Удалить", new ClickHandler() {

            @Override
            public void onClick(ClickEvent clickEvent) {

                REST.withCallback(new MethodCallback<PersonDto>() {
                    @Override
                    public void onFailure(Method method, Throwable throwable) {
                        // Window.alert("ошибка");
                    }

                    @Override
                    public void onSuccess(Method method, PersonDto personDto) {
                        Window.alert("Запись удалена");
                    }
                }).call(rest).delete(String.valueOf(selectionModel.getSelectedObject().getId()));
                updateTable(table);
            }

        });

        horizontalPanel.add(newButton);
        horizontalPanel.add(editButton);
        horizontalPanel.add(deleteButton);
        horizontalPanel.setSpacing(5);
        verticalPanel.add(horizontalPanel);
        //form

        formPanel.add(new Label("Фамилия"));
         lastName = new TextBox();
        formPanel.add(lastName);

        formPanel.add(new Label("Имя"));
        firstName = new TextBox();
        formPanel.add(firstName);
        formPanel.add(new Label("Отчество"));
         middleName = new TextBox();
        formPanel.add(middleName);
        verticalPanel.setSpacing(20);
        formPanel.setSpacing(5);
        formPanel.setVisible(false);
        verticalPanel.add(formPanel);
        Button buttonOK = new SubmitButton("Записать", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                PersonDto personDto = new PersonDto();
               if (isNewPerson){
                   personDto.setFirstName(firstName.getText());
                   personDto.setLastName(lastName.getText());
                   personDto.setMiddleName(middleName.getText());
                   REST.withCallback(new MethodCallback<PersonDto>() {
                       @Override
                       public void onFailure(Method method, Throwable throwable) {
                           Window.alert("Ошибка");
                       }
                       @Override
                       public void onSuccess(Method method, PersonDto personDto) {
                           Window.alert("Запись сохранена");
                           updateTable(table);

                       }
                   }).call(rest).create(personDto);
               }else {
                   personDto = selectionModel.getSelectedObject();
                   personDto.setFirstName(firstName.getText());
                   personDto.setLastName(lastName.getText());
                   personDto.setMiddleName(middleName.getText());
                   REST.withCallback(new MethodCallback<PersonDto>() {
                       @Override
                       public void onFailure(Method method, Throwable throwable) {
                           Window.alert("Ошибка");
                       }
                       @Override
                       public void onSuccess(Method method, PersonDto personDto) {
                           Window.alert("Запись изменена");
                           updateTable(table);

                       }
                   }).call(rest).update(String.valueOf(personDto.getId()), personDto);
               }
                formPanel.setVisible(false);
            }

        });
        Button buttonCancel = new ResetButton("Отмена", new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                formPanel.setVisible(false);
            }
        });
        buttonPanel.add(buttonOK);
        buttonPanel.add(buttonCancel);
        buttonPanel.setSpacing(10);
        formPanel.add(buttonPanel);

        mainPanel.add(verticalPanel);
        mainPanel.setWidgetLeftWidth(table, 10, PCT, 50, PCT);
        mainPanel.setWidgetRightWidth(verticalPanel, 0, PCT, 60, PCT);

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

        table.addColumn(lastName, "Фамилия");
        table.addColumn(firstName, "Имя");
        table.addColumn(middleName, "Отчество");

        updateTable(table);

        return table;

    }

    private void updateTable(CellTable<PersonDto>  table){
        ListDataProvider<PersonDto> dataProvider = new ListDataProvider<>();

        // Connect the table to the data provider.
        dataProvider.addDataDisplay(table);
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
    }

/*
    private void updateTable(CellTable<PersonDto>  table){

        REST.withCallback(new MethodCallback<List<PersonDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("ошибка");
            }
            @Override
            public void onSuccess(Method method, List<PersonDto> dtoList) {
                table.setRowCount(dtoList.size(), true);
                table.setRowData(0, dtoList);
            }
        }).call(rest).allPerson();
        table.redraw();

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
