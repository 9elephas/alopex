package com.ninelephas.fabric.sdk.service.impl;

import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.util.ClientUtil;
import org.hyperledger.fabric.protos.peer.Query;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.File;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zouwei on 2017/4/21.
 */
public class ClientServiceImpl implements ClientService {

    private HFClient client;

    private HFCAClient ca;

    public ClientServiceImpl(String caUrl) {
        super();
        try {
            client = ClientUtil.getClient();
            ca = new HFCAClient(caUrl, null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //private static HFCAClient ca;

//    static {
//        try {
//            ca = new HFCAClient("http://192.168.2.13:7054", null);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//    }

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
            enroll(admin);
            if (!user.isRegistered()) {
                user.setSecret(ca.register(new RegistrationRequest(user.getName(), user.getAffiliation()), admin));
            }
        }
        return enroll(user);
    }

    private boolean enroll(UserEntity user) throws Exception {
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
    public Collection<ProposalResponse> instantiate(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args, String fromYamlFilePath) throws Exception {
        if (!user.isRegistered()) {
            throw new Exception("用户没有被注册");
        }
        if (!chain.isInitialized()) {
            throw new Exception("chain没有初始化");
        }
        client.setUserContext(user);

        Collection<ProposalResponse> responses = chain.sendInstantiationProposal(newInstantiateProposalRequest(chainCodeID, args, fromYamlFilePath), chain.getPeers());


        chain.sendTransaction(responses, chain.getOrderers()).get(120, TimeUnit.SECONDS);

        return responses;
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
    public Collection<ProposalResponse> update(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args) throws Exception {
        if (!user.isRegistered()) {
            throw new Exception("用户没有被注册");
        }
        if (!chain.isInitialized()) {
            throw new Exception("chain没有初始化");
        }
        client.setUserContext(user);

        Collection<ProposalResponse> responses = chain.sendTransactionProposal(newTransactionProposalRequest(chainCodeID, args), chain.getPeers());
        chain.sendTransaction(responses, chain.getOrderers());
        return responses;
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


    /**
     *
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    private UpgradeProposalRequest newUpgradeProposalRequest(ChainCodeID chainCodeID, String[] args) throws Exception {
        UpgradeProposalRequest upgradeProposalRequest = client.newUpgradeProposalRequest();
        upgradeProposalRequest.setArgs(args);
        upgradeProposalRequest.setChaincodeID(chainCodeID);
        upgradeProposalRequest.setFcn("invoke");
        return upgradeProposalRequest;
    }


    private Chain newChainNotInitialize(UserEntity admin, String chainName, ChainConfiguration chainConfiguration, Collection<Orderer> orders, Collection<Peer> peers, Collection<EventHub> eventHubs) throws Exception {
        if (admin.getEnrollment() == null) {
            regist(admin);
        }
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

    public Chain newChainAndInitialize(UserEntity admin, String chainName, ChainConfiguration chainConfiguration, Collection<Orderer> orders, Collection<Peer> peers, Collection<EventHub> eventHubs) throws Exception {
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

    /**
     * 获取peer下所有的channelId
     * @param peer
     * @return
     * @throws Exception
     */
    public Set<String> queryChannels(Peer peer)throws Exception{
        return client.queryChannels(peer);
    }


    public static void main(String[] args) throws Exception {

    }
}
