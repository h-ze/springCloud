package hz.client;

import hz.lib.DeviceFixServiceGrpc;
import hz.lib.booleanReply;
import hz.lib.conditionsRequest;
import hz.lib.deviceFix;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class DeviceGrpcService {

    @GrpcClient("grpc-server")
    private Channel serverChannel;

    public String insertDeviceFix( ){

        DeviceFixServiceGrpc.DeviceFixServiceBlockingStub stub = DeviceFixServiceGrpc.newBlockingStub(serverChannel);
        booleanReply response = stub.insertDeviceFix(
                deviceFix.newBuilder()
                        .setId("UUID-O1")
                        .setSerialNum("AUCCMA-01")
                        .setAddress("SHENZHEN")
                        .setCreatetime(new Date().toString())
                        .setUpdatetime(new Date().toString())
                        .setStatus(1)
                        .setType(1)
                        .build());
        log.info("grpc消费者收到：--》"+response.getReply());
        if(response.getReply()){
            return "success";
        }else{
            return "fail";
        }
    }

    public String selectDeviceFix(){
        DeviceFixServiceGrpc.DeviceFixServiceBlockingStub deviceFixServiceBlockingStub = DeviceFixServiceGrpc.newBlockingStub(serverChannel);
        deviceFix deviceFix = deviceFixServiceBlockingStub.searchDeviceFix(conditionsRequest.newBuilder().setId("1").build());
        log.info("grpc收到消息:{}",deviceFix);
        return deviceFix.toString();
    }
}

