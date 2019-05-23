package com.main.uienum;

public enum EmployeeUiPathEnum {

    // form of a employee
    ENTERPRISE_ADD_EMPLOYEE_BUTTON("enterprise_add_employee_button", "//*[@id=\"right-table\"]/div[1]/div[1]/button[1]/span"),
    ENTERPRISE_ADD_EMPLOYEE_NAME("enterprise_add_employee_name", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[1]/div/div[1]/input"),
    ENTERPRISE_ADD_EMPLOYEE_EMAIL("enterprise_add_employee_email", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[3]/div/div[1]/input"),
    ENTERPRISE_ADD_EMPLOYEE_PASSWORD("enterprise_add_employee_password", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[4]/div/div[1]/input"),
    ENTERPRISE_ADD_EMPLOYEE_PHONE("enterprise_add_employee_phone", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[5]/div/div[1]/input"),
    ENTERPRISE_ADD_EMPLOYEE_BRIEF("enterprise_add_employee_brief", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[6]/div/div[1]/textarea"),
    ENTERPRISE_ADD_EMPLOYEE_POSITION("enterprise_add_employee_position", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[7]/div/div/input"),
    ENTERPRISE_ADD_EMPLOYEE_FEMALE_BUTTON("enterprise_add_employee_female_button", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[2]/div/div/label[2]/span/input"),
    ENTERPRISE_ADD_EMPLOYEE_MALE_BUTTON("enterprise_add_employee_male_button", "/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[2]/div/div/label[1]/span/input"),
    ENTERPRISE_ADD_EMPLOYEE_SUBMIT_BUTTON("enterprise_add_employee_submit_button", "/html/body/div[13]/div[2]/div/div/div[3]/div/button[2]"),

    ENTERPRISE_ORGANIZATION_BUTTON("enterprise_organization","//*[@id=\"app\"]/div/div[2]/div[1]/div/div[1]/div[1]/div/div[2]/div[3]"),
    ENTERPRISE_LAST_EMPLOYEE("enterprise_last_employee","//*[@id=\"right-table\"]/div[2]/div/div[2]/table/tbody/tr[last()]/td[3]/div/span");


    private String name;
    private String path;

    EmployeeUiPathEnum(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public static String getPath(String name) {
        for (EmployeeUiPathEnum uiPathEnum : EmployeeUiPathEnum.values()) {
            if (uiPathEnum.getName() == name) {
                return uiPathEnum.getPath();
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
