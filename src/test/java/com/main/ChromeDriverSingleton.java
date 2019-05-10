package com.main;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverSingleton {

    private static ChromeDriver instance;
    private ChromeDriverSingleton() {}

    public static synchronized ChromeDriver getInstance() {
        if (instance == null) {
            System.setProperty("webdriver.chrome.driver", "D:\\Doc--GitRepo\\Java\\test\\src\\main\\resources\\chromedriver.exe");
            instance = new ChromeDriver();
        }
        return instance;
    }

}
