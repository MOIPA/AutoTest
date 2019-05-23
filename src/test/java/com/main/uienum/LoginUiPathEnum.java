package com.main.uienum;

public enum LoginUiPathEnum {

    DRIVER_HOME("driver_home","D:\\Doc--GitRepo\\AutoTest\\src\\main\\resources\\chromedriver.exe"),

    LOGIN_NAME("login_name","//*[@id=\"login-container\"]/form/div[1]/div/div/input"),
    LOGIN_PWD("login_pwd","//*[@id=\"login-container\"]/form/div[2]/div/div/input"),
    LOGIN_CONFIRM("login_confirm","//*[@id=\"login-container\"]/form/div[4]/div/button"),
    LOGGED_NAME("logged_name","//*[@id=\"app\"]/div/div[1]/div/div/div[2]/div[1]");

    private String name;
    private String path;

    LoginUiPathEnum(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public static String getPath(String name) {
        for (LoginUiPathEnum loginUiPathEnum : LoginUiPathEnum.values()) {
            if (loginUiPathEnum.getName() == name) {
                return loginUiPathEnum.getPath();
            }
        }
        return "";
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }
}
