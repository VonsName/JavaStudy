package com.example.thriftservice;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class AuthClient {
    private  com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.Client rpcNetAuthService;
    private TBinaryProtocol protocol;
    private TSocket transport ;
    private RPCAuthService.Client getRpcAuthService() {
        return rpcAuthService;
    }

    public com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.Client getRpcNetAuthService() {
        return rpcNetAuthService;
    }

    public  void  open() throws TTransportException {
        transport.open();
    }
    public  void  close()
    {
        transport.close();
    }
    public   AuthClient() throws TTransportException {
        transport = new TSocket("localhost",9090);
        protocol = new TBinaryProtocol(transport);

        TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol, com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.class.getSimpleName());
        rpcNetAuthService = new com.xxx.mbe.thrift.rpcInterface.auth.RPCNetAuthService.Client(mp2);
    }
}
