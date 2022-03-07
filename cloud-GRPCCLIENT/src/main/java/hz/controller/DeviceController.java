package hz.controller;


import hz.client.DeviceGrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("device")
public class DeviceController {

    @Autowired
    private DeviceGrpcService deviceGrpcService;

    @GetMapping("getDevice")
    public String getDevice(){
        String s = deviceGrpcService.selectDeviceFix();
        return s;
    }
}
