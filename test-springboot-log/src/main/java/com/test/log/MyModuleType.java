package com.test.log;

public enum MyModuleType {
    DEFAULT("1"),
    STUDENT("2"),
    TEACHER("3");
    private String moudle;
    private MyModuleType(String index){
        this.moudle=index;
    }

    public String getMoudle() {
        return moudle;
    }
}
