package ru.test.gwt.shared.dto;

public class StringDto {

    private String value;

    public StringDto(){}

    public StringDto(String val){
        setValue(val);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
