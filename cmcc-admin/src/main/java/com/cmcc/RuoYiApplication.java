package com.cmcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RuoYiApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  内核用例管理系统   ლ(´ڡ`ლ)ﾞ  \n" +
                " ________   _____ ______    ________   ________     \n" +
                "|\\   ____\\ |\\   _ \\  _   \\ |\\   ____\\ |\\   ____\\    \n" +
                "\\ \\  \\___| \\ \\  \\\\\\__\\ \\  \\\\ \\  \\___| \\ \\  \\___|    \n" +
                " \\ \\  \\     \\ \\  \\\\|__| \\  \\\\ \\  \\     \\ \\  \\       \n" +
                "  \\ \\  \\____ \\ \\  \\    \\ \\  \\\\ \\  \\____ \\ \\  \\____  \n" +
                "   \\ \\_______\\\\ \\__\\    \\ \\__\\\\ \\_______\\\\ \\_______\\\n" +
                "    \\|_______| \\|__|     \\|__| \\|_______| \\|_______|");

    }
}
