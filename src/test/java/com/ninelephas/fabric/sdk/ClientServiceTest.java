package com.ninelephas.fabric.sdk;

import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.service.impl.ClientService;
import com.ninelephas.fabric.sdk.service.impl.ClientServiceImpl;
import org.hyperledger.fabric.sdk.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zouwei on 2017/4/25.
 */
public class ClientServiceTest {

    private ClientService clientService;

    @Test
    public void registTest() throws Exception {
        clientService = new ClientServiceImpl("http://192.168.2.13:7054");
        //创建用户
        String mspID = "Org1MSP";
        UserEntity admin = new UserEntity("admin", mspID);
        admin.setSecret("adminpw");
        UserEntity user = new UserEntity("user2", mspID);
        user.setAffiliation("org1.department1");
        user.setAdmin(admin);
        boolean res = clientService.regist(user);
        Assert.assertTrue(res);
    }


    @Test
    public void chainInitializedTest() throws Exception {
        clientService = new ClientServiceImpl("http://192.168.2.13:7054");
        String mspID = "Org1MSP";
        UserEntity admin = new UserEntity("admin", mspID);
        admin.setSecret("adminpw");

        ChainConfiguration chainConfiguration = new ChainConfiguration(new File("/Users/zouwei/projects/alopex/src/test/resources/foo.tx"));
        Set<Peer> peers = new HashSet<>(2);
        peers.add(clientService.newPeer("peer0", "grpc://192.168.2.13:7051", null));
        peers.add(clientService.newPeer("peer1", "grpc://192.168.2.13:7056", null));

        Set<Orderer> orders = new HashSet<>(1);
        orders.add(clientService.newOrder("orderer0", "grpc://192.168.2.13:7050", null));

        Set<EventHub> eventHubs = new HashSet<>(1);
        eventHubs.add(clientService.newEventHub("eventhub0", "grpc://192.168.2.13:7053", null));

        Chain chain = clientService.newChainAndInitialize(admin, "foo", chainConfiguration, orders, peers, eventHubs);

        boolean isInitialized = chain.isInitialized();

        Assert.assertTrue(isInitialized);
    }


    @Test
    public void installTest(){

    }

}
