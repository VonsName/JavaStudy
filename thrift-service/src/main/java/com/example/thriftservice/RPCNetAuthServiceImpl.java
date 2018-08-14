package com.example.thriftservice;

import org.apache.thrift.TException;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class RPCNetAuthServiceImpl implements com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.Iface  {
    private static final Logger logger = LoggerFactory.getLogger(RPCNetAuthServiceImpl.class);

    private NetAuthService netAuthService =new NetAuthServiceImpl();//这里本来想用注入的方法使用，但是service启动的时候是静态类直接new的，所以没法注入，所以这里就new了
    @Override
    public boolean login(String userAccount, String password) throws TException {
        return netAuthService.login(userAccount,password);//这里调用了Service的方法
    }
    private  static void   startRPCServer()
    {
        try {
            // 设置协议工厂为 TBinaryProtocol.Factory
            TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();
            // 关联处理器与 Hello 服务的实现
            TMultiplexedProcessor processor = new TMultiplexedProcessor();
            TServerTransport t = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(t).processor(processor));
            processor.registerProcessor(com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.class.getSimpleName(), new com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.Processor<com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.Iface>(new RPCNetAuthServiceImpl()));
//          TSimpleServer server = new TSimpleServer(new Args(t).processor(processor));
            System.out.println("the serveris started and is listening at 9090...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        startRPCServer();
    }
}
