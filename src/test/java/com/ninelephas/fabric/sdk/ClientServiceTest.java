package com.ninelephas.fabric.sdk;

import com.ninelephas.alopex.configer.ConfigHelper;
import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.service.impl.ClientService;
import com.ninelephas.fabric.sdk.service.impl.ClientServiceImpl;
import org.apache.commons.configuration2.Configuration;
import org.hyperledger.fabric.sdk.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;


/**
 * Created by zouwei on 2017/4/25.
 */
public class ClientServiceTest {

    private ClientService clientService;

    @Test
    public void registTest() throws Exception {
        Configuration configuration = ConfigHelper.getConfig();
        //CA url
        String caUrl = configuration.getString("ca.url");
        String secret = configuration.getString("admin.secret");

        clientService = new ClientServiceImpl(caUrl);
        //创建用户
        String mspID = "Org1MSP";
        UserEntity admin = new UserEntity("admin", mspID);
        admin.setSecret(secret);
        UserEntity user = new UserEntity("user1", mspID);
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
    public void installTest() throws Exception {
        Configuration configuration = ConfigHelper.getConfig();
        //CA url
        String caUrl = configuration.getString("ca.url");
        String secret = configuration.getString("admin.secret");
        String peerList = configuration.getString("peerlist");
        String[] peerUrls = peerList.split(",");

        String ordererlist = configuration.getString("ordererlist");
        String[] ordererUrls = peerList.split(",");

        String eventhublist = configuration.getString("eventhublist");
        String[] eventhubUrls = peerList.split(",");


        clientService = new ClientServiceImpl(caUrl);
        //创建用户
        String mspID = "Org1MSP";
        UserEntity admin = new UserEntity("admin", mspID);
        admin.setSecret(secret);
        UserEntity user = new UserEntity("user1", mspID);
        user.setAffiliation("org1.department1");
        user.setAdmin(admin);
        boolean res = clientService.regist(user);
        //创建chain
        ChainConfiguration chainConfiguration = new ChainConfiguration(new File("/Users/zouwei/projects/alopex/src/test/resources/foo.tx"));
        Set<Peer> peers = new HashSet<>(2);
        for (int i = 0; i < peerUrls.length; i++) {
            peers.add(clientService.newPeer("peer" + i, peerUrls[i], null));
        }

        Set<Orderer> orders = new HashSet<>(1);
        for (int i = 0; i < ordererUrls.length; i++) {
            orders.add(clientService.newOrder("orderer" + i, ordererUrls[i], null));
        }


        Set<EventHub> eventHubs = new HashSet<>(1);
        for (int i = 0; i < eventhubUrls.length; i++) {
            eventHubs.add(clientService.newEventHub("eventhub" + i, eventhubUrls[i], null));
        }


        Chain chain = clientService.newChainAndInitialize(admin, "foo", chainConfiguration, orders, peers, eventHubs);

        //创建chaincodeID

        ChainCodeID chainCodeID = ChainCodeID.newBuilder().setName("example_cc_go").setPath("github.com/example_cc").setVersion("1").build();


        //install
        Collection<ProposalResponse> collection = clientService.install(user, chain, chainCodeID, "src/test", "1", TransactionRequest.Type.GO_LANG);

        for (ProposalResponse response : collection) {
            ChainCodeResponse.Status status = response.getStatus();

            Assert.assertEquals(status, ChainCodeResponse.Status.SUCCESS);
        }
    }


    @Test
    public void instantiateTest() throws Exception {
        Configuration configuration = ConfigHelper.getConfig();
        //CA url
        String caUrl = configuration.getString("ca.url");
        String secret = configuration.getString("admin.secret");
        String peerList = configuration.getString("peerlist");
        String[] peerUrls = peerList.split(",");

        String ordererlist = configuration.getString("ordererlist");
        String[] ordererUrls = peerList.split(",");

        String eventhublist = configuration.getString("eventhublist");
        String[] eventhubUrls = peerList.split(",");


        clientService = new ClientServiceImpl(caUrl);
        //创建用户
        String mspID = "Org1MSP";
        UserEntity admin = new UserEntity("admin", mspID);
        admin.setSecret(secret);
        UserEntity user = new UserEntity("user1", mspID);
        user.setAffiliation("org1.department1");
        user.setAdmin(admin);
        boolean res = clientService.regist(user);
        //创建chain
        ChainConfiguration chainConfiguration = new ChainConfiguration(new File("/Users/zouwei/projects/alopex/src/test/resources/foo.tx"));
        Set<Peer> peers = new HashSet<>(2);
        for (int i = 0; i < peerUrls.length; i++) {
            peers.add(clientService.newPeer("peer" + i, peerUrls[i], null));
        }

        Set<Orderer> orders = new HashSet<>(1);
        for (int i = 0; i < ordererUrls.length; i++) {
            orders.add(clientService.newOrder("orderer" + i, ordererUrls[i], null));
        }


        Set<EventHub> eventHubs = new HashSet<>(1);
        for (int i = 0; i < eventhubUrls.length; i++) {
            eventHubs.add(clientService.newEventHub("eventhub" + i, eventhubUrls[i], null));
        }


        Chain chain = clientService.newChainAndInitialize(admin, "foo", chainConfiguration, orders, peers, eventHubs);

        //创建chaincodeID

        ChainCodeID chainCodeID = ChainCodeID.newBuilder().setName("example_cc_go").setPath("github.com/example_cc").setVersion("1").build();


        //install
        Collection<ProposalResponse> collection = clientService.install(user, chain, chainCodeID, "src/test", "1", TransactionRequest.Type.GO_LANG);

        for (ProposalResponse response : collection) {
            ChainCodeResponse.Status status = response.getStatus();

            Assert.assertEquals(status, ChainCodeResponse.Status.SUCCESS);
        }

        //初始化数据
        Collection<ProposalResponse> responses = clientService.instantiate(user, chain, chainCodeID, new String[]{"a", "500", "b", ""}, "/Users/zouwei/projects/alopex/src/test/resources/chaincodeendorsementpolicy.yaml");

        for (ProposalResponse response : responses) {
            ChainCodeResponse.Status status = response.getStatus();

            Assert.assertEquals(status, ChainCodeResponse.Status.SUCCESS);
        }

    }

}
