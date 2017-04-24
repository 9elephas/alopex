package com.ninelephas.fabric.sdk.service.impl;

import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.util.ClientUtil;
import org.apache.commons.io.IOUtils;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * Created by zouwei on 2017/4/19.
 */
public class ClientServiceImpl {


    private HFClient client = ClientUtil.getClient();


    /**
     * 用户和管理员都必须要做的操作
     *
     * @param ca
     * @param user
     * @return
     * @throws Exception
     */
    private Boolean enroll(HFClient client, HFCAClient ca, UserEntity user) throws Exception {
        if (!user.isEnrolled()) {
            user.setEnrollment(ca.enroll(user.getName(), user.getSecret()));
        }
        return user.isEnrolled();
    }

    /**
     * 用户注册,一定要设置name，affiliation和admin的值
     *
     * @param ca
     * @param user
     * @return
     * @throws Exception
     */
    public Boolean regist(HFClient client, HFCAClient ca, UserEntity user) throws Exception {
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

    /**
     * @param client
     * @param chain
     * @param chainCodeID
     * @param chainCodeSourceLocation
     * @param version
     * @param type
     * @return
     * @throws Exception
     */
    public Collection<ProposalResponse> install(HFClient client, Chain chain, ChainCodeID chainCodeID, String chainCodeSourceLocation, String version, TransactionRequest.Type type) throws Exception {
        return chain.sendInstallProposal(newInstallProposalRequest(client, chainCodeID, chainCodeSourceLocation, version, type), chain.getPeers());
    }

    /**
     * 数据初始化
     *
     * @param client
     * @param chain
     * @param chainCodeID
     * @param args
     * @param fromYamlFilePath
     * @return
     * @throws Exception
     */
    public CompletableFuture<BlockEvent.TransactionEvent> instantiate(HFClient client, Chain chain, ChainCodeID chainCodeID, String[] args, String fromYamlFilePath) throws Exception {
        return chain.sendTransaction(chain.sendInstantiationProposal(newInstantiateProposalRequest(client, chainCodeID, args, fromYamlFilePath), chain.getPeers()), chain.getOrderers());
    }

    /**
     * 查询
     *
     * @param client
     * @param chain
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public Collection<ProposalResponse> query(HFClient client, Chain chain, ChainCodeID chainCodeID, String[] args) throws Exception {
        return chain.queryByChaincode(newQueryByChaincodeRequest(client, chainCodeID, args), chain.getPeers());
    }

    /**
     * 更新
     *
     * @param client
     * @param chain
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public CompletableFuture<BlockEvent.TransactionEvent> update(HFClient client, Chain chain, ChainCodeID chainCodeID, String[] args) throws Exception {
        return chain.sendTransaction(chain.sendTransactionProposal(newTransactionProposalRequest(client, chainCodeID, args), chain.getPeers()), chain.getOrderers());
    }

    /**
     * 获取已经存在的chain，不存在就创建一个新的
     *
     * @param client
     * @param chainName
     * @return
     * @throws InvalidArgumentException
     */
    public Chain getChain(HFClient client, String chainName) throws InvalidArgumentException {
        Chain chain = client.getChain(chainName);
        if (chain == null) {
            chain = client.newChain(chainName);
        }
        return chain;
    }


    public Chain newChainNotInitialize(HFClient client, User admin, String chainName, ChainConfiguration chainConfiguration, Collection<Orderer> orders, Collection<Peer> peers, Collection<EventHub> eventHubs) throws Exception {
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

    /**
     * 获取chain
     *
     * @param admin
     * @param chainName
     * @param chainConfiguration
     * @param orders
     * @param peers
     * @param eventHubs
     * @return
     * @throws Exception
     */
    public Chain newChainAndInitialize(HFClient client, User admin, String chainName, ChainConfiguration chainConfiguration, Collection<Orderer> orders, Collection<Peer> peers, Collection<EventHub> eventHubs) throws Exception {
        return newChainNotInitialize(client, admin, chainName, chainConfiguration, orders, peers, eventHubs).initialize();
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
    public Peer newPeer(HFClient client, String peerName, String peerLocation, Properties properties) throws Exception {
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
    public Orderer newOrder(HFClient client, String orderName, String orderLocation, Properties properties) throws Exception {
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
    public EventHub newEventHub(HFClient client, String eventHubName, String eventHubLocation, Properties properties) throws Exception {
        return client.newEventHub(eventHubName, eventHubLocation, properties);
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
    public InstallProposalRequest newInstallProposalRequest(HFClient client, ChainCodeID chainCodeID, String chainCodeSourceLocation, String version, TransactionRequest.Type type) throws Exception {
        InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
        installProposalRequest.setChaincodeID(chainCodeID);
        //For GO language and serving just a single user, chaincodeSource is mostly likely the users GOPATH
        installProposalRequest.setChaincodeSourceLocation(new File(chainCodeSourceLocation));
        installProposalRequest.setChaincodeVersion(version);
        installProposalRequest.setChaincodeLanguage(type);
        return installProposalRequest;
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
    public InstantiateProposalRequest newInstantiateProposalRequest(HFClient client, ChainCodeID chainCodeID, String[] args, String fromYamlFilePath) throws Exception {
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


    public TransactionProposalRequest newTransactionProposalRequest(HFClient client, ChainCodeID chainCodeID, String[] args) throws Exception {
        TransactionProposalRequest transactionProposalRequest = client.newTransactionProposalRequest();
        transactionProposalRequest.setChaincodeID(chainCodeID);
        transactionProposalRequest.setFcn("invoke");
        transactionProposalRequest.setArgs(args);
        return transactionProposalRequest;
    }

    public QueryByChaincodeRequest newQueryByChaincodeRequest(HFClient client, ChainCodeID chainCodeID, String[] args) {
        QueryByChaincodeRequest queryByChaincodeRequest = client.newQueryProposalRequest();
        queryByChaincodeRequest.setArgs(args);
        queryByChaincodeRequest.setFcn("invoke");
        queryByChaincodeRequest.setChaincodeID(chainCodeID);
        return queryByChaincodeRequest;
    }

//    public static void main(String[] args) throws Exception {
//        ClientServiceImpl clientService = new ClientServiceImpl();
//        HFClient client = ClientUtil.getClient();
//        String mspID = "Org1MSP";
//        UserEntity admin = new UserEntity("admin", mspID);
//        admin.setSecret("adminpw");
//        HFCAClient ca = new HFCAClient("http://192.168.2.13:7054", null);
//        clientService.regist(client,ca,admin);
//        //client.setMemberServices(ca);
//        client.setUserContext(admin);
//        Chain chain = clientService.getChain(client,"foo");
//        chain.isInitialized();
//
//
//
//
//    }
public static void main(String[] args)throws Exception{

    InputStream is = new FileInputStream(new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/foo.tx"));
    String n  = IOUtils.toString(is);
    System.out.print(n);
}

//    public static void main(String[] args) throws Exception {
//        ClientServiceImpl clientService = new ClientServiceImpl();
//        HFClient client = ClientUtil.getClient();
//        //创建用户
//        String mspID = "Org1MSP";
//        UserEntity admin = new UserEntity("admin", mspID);
//        admin.setSecret("adminpw");
//        UserEntity user = new UserEntity("user1", mspID);
//        user.setAffiliation("org1.department1");
//        user.setAdmin(admin);
//        //UserServiceImpl userService = new UserServiceImpl();
//        //创建CA
//        HFCAClient ca = new HFCAClient("http://192.168.2.13:7054", null);
//
//        //clientService.enroll(client,ca,admin);
//        //注册用户
//        clientService.regist(client, ca, user);
//        //创建chain
//
//        ChainConfiguration chainConfiguration = new ChainConfiguration(new File("src/test/fixture/sdkintegration/e2e-2Orgs/channel/foo.tx"));
//        Set<Peer> peers = new HashSet<>(2);
//
//        peers.add(clientService.newPeer(client, "peer0", "grpc://192.168.2.13:7051", null));
//        peers.add(clientService.newPeer(client, "peer1", "grpc://192.168.2.13:7056", null));
//
//        Set<Orderer> orders = new HashSet<>(1);
//        orders.add(clientService.newOrder(client, "orderer0", "grpc://192.168.2.13:7050", null));
//
//        Set<EventHub> eventHubs = new HashSet<>(1);
//        eventHubs.add(clientService.newEventHub(client, "eventhub0", "grpc://192.168.2.13:7053", null));
//
//        Chain chain = clientService.newChainAndInitialize(client, user.getAdmin(), "foo", chainConfiguration, orders, peers, eventHubs);
//        //创建ChainCodeID
//        ChainCodeID chainCodeID = ChainCodeID.newBuilder().setName("example_cc_go").setPath("github.com/example_cc").setVersion("1").build();
//        //install
//        Collection<ProposalResponse> installResponse = clientService.install(client, chain, chainCodeID, "src/test/fixture/sdkintegration/gocc/sample1", "1", TransactionRequest.Type.GO_LANG);
//        for (ProposalResponse res : installResponse) {
//            ChainCodeResponse.Status status = res.getStatus();
//            if (status.equals(ChainCodeResponse.Status.SUCCESS)) {
//                System.out.println("Successful install proposal response Txid: " + res.getTransactionID() + " from peer " + res.getPeer().getName());
//            }
//        }
//
//        //初始化数据
//
//        clientService.instantiate(client, chain, chainCodeID, new String[]{"a", "500", "b", "400"}, "src/test/fixture/sdkintegration/chaincodeendorsementpolicy.yaml");
//
//        //查询数据
//        Collection<ProposalResponse> queryResponse = clientService.query(client, chain, chainCodeID, new String[]{"query", "b"});
//
//        for (ProposalResponse res : queryResponse) {
//            ChainCodeResponse.Status status = res.getStatus();
//            if (status.equals(ChainCodeResponse.Status.SUCCESS)) {
//                String payload = res.getProposalResponse().getResponse().getPayload().toStringUtf8();
//                System.out.println("Successful install proposal response Txid: " + res.getTransactionID() + " from peer " + res.getPeer().getName());
//            }
//        }
//
//    }
}
