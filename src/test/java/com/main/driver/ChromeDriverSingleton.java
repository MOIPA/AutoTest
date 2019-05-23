package com.main.driver;

import com.main.uienum.LoginUiPathEnum;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverSingleton {

    private static ChromeDriver instance;
    private ChromeDriverSingleton() {}

    public static synchronized ChromeDriver getInstance() {
        if (instance == null) {
            System.setProperty("webdriver.chrome.driver", LoginUiPathEnum.getPath("driver_home"));
            instance = new ChromeDriver();
        }
        return instance;
    }

    public static void resetDriver() {
        instance = new ChromeDriver();
    }

}
