package com.ninelephas.fabric.sdk.service.impl;

import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.util.ClientUtil;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by zouwei on 2017/4/21.
 */
public class Test implements ClientService {

    private HFClient client = ClientUtil.getClient();

    private static HFCAClient ca;

    static {
        try {
            ca = new HFCAClient("http://192.168.2.13:7054", null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public boolean regist(UserEntity user) throws Exception {
        client.setMemberServices(ca);
        if (!user.isAdmin()) {
            UserEntity admin = user.getAdmin();
            enroll(client, ca, admin);
            if (!user.isRegistered()) {
                user.setSecret(ca.register(new RegistrationRequest(user.getName(), user.getAffiliation()), admin));
            }
        }
        return enroll(client, ca, user);
    }

    private boolean enroll(HFClient client, HFCAClient ca, UserEntity user) throws Exception {
        if (!user.isEnrolled()) {
            user.setEnrollment(ca.enroll(user.getName(), user.getSecret()));
        }
        return user.isEnrolled();
    }

    /**
     * 执行install操作
     *
     * @param chain
     * @param chainCodeID
     * @param chainCodeSourceLocation
     * @param version
     * @param type
     * @return
     * @throws Exception
     */
    public Collection<ProposalResponse> install(UserEntity user, Chain chain, ChainCodeID chainCodeID, String chainCodeSourceLocation, String version, TransactionRequest.Type type) throws Exception {
        if (!user.isRegistered()) {
            throw new Exception("用户没有被注册");
        }
        if (!chain.isInitialized()) {
            throw new Exception("chain没有初始化");
        }
        client.setUserContext(user);
        return chain.sendInstallProposal(newInstallProposalRequest(client, chainCodeID, chainCodeSourceLocation, version, type), chain.getPeers());
    }

    /**
     * 创建一个install请求
     *
     * @param chainCodeID
     * @param chainCodeSourceLocation
     * @param version
     * @param type
     * @return
     * @throws Exception
     */
    private InstallProposalRequest newInstallProposalRequest(HFClient client, ChainCodeID chainCodeID, String chainCodeSourceLocation, String version, TransactionRequest.Type type) throws Exception {
        InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
        installProposalRequest.setChaincodeID(chainCodeID);
        //For GO language and serving just a single user, chaincodeSource is mostly likely the users GOPATH
        installProposalRequest.setChaincodeSourceLocation(new File(chainCodeSourceLocation));
        installProposalRequest.setChaincodeVersion(version);
        installProposalRequest.setChaincodeLanguage(type);
        return installProposalRequest;
    }


    /**
     * 数据初始化
     *
     * @param chain
     * @param chainCodeID
     * @param args
     * @param fromYamlFilePath
     * @return
     * @throws Exception
     */
    public CompletableFuture<BlockEvent.TransactionEvent> instantiate(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args, String fromYamlFilePath) throws Exception {
        if (!user.isRegistered()) {
            throw new Exception("用户没有被注册");
        }
        if (!chain.isInitialized()) {
            throw new Exception("chain没有初始化");
        }
        client.setUserContext(user);
        return chain.sendTransaction(chain.sendInstantiationProposal(newInstantiateProposalRequest(chainCodeID, args, fromYamlFilePath), chain.getPeers()), chain.getOrderers());
    }

    /**
     * 创建一个instantiate请求
     *
     * @param chainCodeID
     * @param args
     * @param fromYamlFilePath
     * @return
     * @throws Exception
     */
    private InstantiateProposalRequest newInstantiateProposalRequest(ChainCodeID chainCodeID, String[] args, String fromYamlFilePath) throws Exception {
        InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
        instantiateProposalRequest.setChaincodeID(chainCodeID);
        instantiateProposalRequest.setFcn("init");
        instantiateProposalRequest.setArgs(args);

    /*
      policy OR(Org1MSP.member, Org2MSP.member) meaning 1 signature from someone in either Org1 or Org2
      See README.md Chaincode endorsement policies section for more details.
    */
        ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
        chaincodeEndorsementPolicy.fromYamlFile(new File(fromYamlFilePath));
        instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);
        return instantiateProposalRequest;
    }


    /**
     * 查询
     *
     * @param chain
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public Collection<ProposalResponse> query(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args) throws Exception {
        if (!user.isRegistered()) {
            throw new Exception("用户没有被注册");
        }
        if (!chain.isInitialized()) {
            throw new Exception("chain没有初始化");
        }
        client.setUserContext(user);
        return chain.queryByChaincode(newQueryByChaincodeRequest(chainCodeID, args), chain.getPeers());
    }

    /**
     * 创建查询的请求
     *
     * @param chainCodeID
     * @param args
     * @return
     */
    private QueryByChaincodeRequest newQueryByChaincodeRequest(ChainCodeID chainCodeID, String[] args) {
        QueryByChaincodeRequest queryByChaincodeRequest = client.newQueryProposalRequest();
        queryByChaincodeRequest.setArgs(args);
        queryByChaincodeRequest.setFcn("invoke");
        queryByChaincodeRequest.setChaincodeID(chainCodeID);
        return queryByChaincodeRequest;
    }

    /**
     * 更新
     *
     * @param chain
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public CompletableFuture<BlockEvent.TransactionEvent> update(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args) throws Exception {
        if (!user.isRegistered()) {
            throw new Exception("用户没有被注册");
        }
        if (!chain.isInitialized()) {
            throw new Exception("chain没有初始化");
        }
        client.setUserContext(user);
        return chain.sendTransaction(chain.sendTransactionProposal(newTransactionProposalRequest(chainCodeID, args), chain.getPeers()), chain.getOrderers());
    }

    /**
     * 创建一个更新请求
     *
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    private TransactionProposalRequest newTransactionProposalRequest(ChainCodeID chainCodeID, String[] args) throws Exception {
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chainCodeID);
        transactionProposalRequest.setFcn("invoke");
        transactionProposalRequest.setArgs(args);
        return transactionProposalRequest;
    }


    private Chain newChainNotInitialize(User admin, String chainName, ChainConfiguration chainConfiguration, Collection<Orderer> orders, Collection<Peer> peers, Collection<EventHub> eventHubs) throws Exception {
        Chain chain = null;
        client.setUserContext(admin);
        Iterator<Orderer> iterator = orders.iterator();
        if (iterator.hasNext()) {
            chain = client.newChain(chainName, iterator.next(), chainConfiguration);
        }
        while (iterator.hasNext()) {
            chain.addOrderer(iterator.next());
        }
        for (Peer peer : peers) {
            chain.joinPeer(peer);
        }
        for (EventHub eventHub : eventHubs) {
            chain.addEventHub(eventHub);
        }
        return chain;
    }

    public Chain newChainAndInitialize(User admin, String chainName, ChainConfiguration chainConfiguration, Collection<Orderer> orders, Collection<Peer> peers, Collection<EventHub> eventHubs) throws Exception {
        return newChainNotInitialize(admin, chainName, chainConfiguration, orders, peers, eventHubs).initialize();
    }

    /**
     * 创建一个新peer
     *
     * @param peerName
     * @param peerLocation
     * @param properties
     * @return
     * @throws Exception
     */
    public Peer newPeer(String peerName, String peerLocation, Properties properties) throws Exception {
        return client.newPeer(peerName, peerLocation, properties);
    }

    /**
     * 创建一个新order
     *
     * @param orderName
     * @param orderLocation
     * @param properties
     * @return
     * @throws Exception
     */
    public Orderer newOrder(String orderName, String orderLocation, Properties properties) throws Exception {
        return client.newOrderer(orderName, orderLocation, properties);
    }

    /**
     * 创建一个新EventHub
     *
     * @param eventHubName
     * @param eventHubLocation
     * @param properties
     * @return
     * @throws Exception
     */
    public EventHub newEventHub(String eventHubName, String eventHubLocation, Properties properties) throws Exception {
        return client.newEventHub(eventHubName, eventHubLocation, properties);
    }


    public static void main(String[] args) throws Exception {
        Test test = new Test();
        HFClient client = test.client;
        //创建用户
        String mspID = "Org1MSP";
        UserEntity admin = new UserEntity("admin", mspID);
        admin.setSecret("adminpw");
        UserEntity user = new UserEntity("user1", mspID);
        user.setAffiliation("org1.department1");
        user.setAdmin(admin);

        //clientService.enroll(client,ca,admin);
        //注册用户
        test.regist(user);

        ChainConfiguration chainConfiguration = new ChainConfiguration(new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/foo.tx"));
        Set<Peer> peers = new HashSet<>(2);

        peers.add(test.newPeer("peer0", "grpc://192.168.2.13:7051", null));
        //peers.add(test.newPeer("peer1", "grpc://192.168.2.13:7056", null));

        Set<Orderer> orders = new HashSet<>(1);
        orders.add(test.newOrder("orderer0", "grpc://192.168.2.13:7050", null));

        Set<EventHub> eventHubs = new HashSet<>(1);
        eventHubs.add(test.newEventHub("eventhub0", "grpc://192.168.2.13:7053", null));

        Chain chain = test.newChainAndInitialize(user.getAdmin(), "foo", chainConfiguration, orders, peers, eventHubs);

        chain.joinPeer(test.newPeer("peer1", "grpc://192.168.2.13:7056", null));

        ChainCodeID chainCodeID = ChainCodeID.newBuilder().setName("example_cc_go").setPath("github.com/example_cc").setVersion("1").build();

        test.install(user, chain, chainCodeID, "src/test/fixture/sdkintegration/gocc/sample1", "1", TransactionRequest.Type.GO_LANG);

        test.instantiate(user, chain, chainCodeID, new String[]{"a", "500", "b", "400"}, "src/test/fixture/sdkintegration/chaincodeendorsementpolicy.yaml");

        Collection<ProposalResponse> res = test.query(user, chain, chainCodeID, new String[]{"query", "b"});

        for (ProposalResponse r : res) {
            ChainCodeResponse.Status status = r.getStatus();
            if (status.equals(ChainCodeResponse.Status.SUCCESS)) {
                String payload = r.getProposalResponse().getResponse().getPayload().toStringUtf8();
                System.out.println("Successful install proposal response Txid: " + r.getTransactionID() + " from peer " + r.getPeer().getName());
            }
        }


        Test test1 = new Test();

        HFClient client1 = test1.client;

        Chain chain1 = client1.getChain("foo");

        chain.isInitialized();

        res = test1.query(user, chain1, chainCodeID, new String[]{"query", "a"});

        for (ProposalResponse r : res) {
            ChainCodeResponse.Status status = r.getStatus();
            if (status.equals(ChainCodeResponse.Status.SUCCESS)) {
                String payload = r.getProposalResponse().getResponse().getPayload().toStringUtf8();
                System.out.println("Successful install proposal response Txid: " + r.getTransactionID() + " from peer " + r.getPeer().getName());
            }
        }
    }
}
