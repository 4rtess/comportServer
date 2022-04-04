package com.artess.comportServer.contoller;

import com.artess.comportServer.frame.MainFrame;
import com.artess.comportServer.serialport.SerialPortController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private SerialPortController serialPortController;

    public MainController() {
        serialPortController = new SerialPortController("COM6");
        try {
            MainFrame frame = new MainFrame("http://localhost:8080/main", false, false);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @PostMapping("/onLight")
    public String onLight() {
        serialPortController.writeString("HIGH");
        return "main";
    }
    @PostMapping("/offLight")
    public String offLight() {
        serialPortController.writeString("LOW");
        return "main";
    }

}
