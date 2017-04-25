package com.ninelephas.fabric.sdk.service.impl;

import com.ninelephas.fabric.sdk.entity.UserEntity;
import org.hyperledger.fabric.sdk.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Created by zouwei on 2017/4/21.
 */
public interface ClientService {

    /**
     * 用户注册（包括管理员）
     * @param user
     * @return
     * @throws Exception
     */
    public boolean regist(UserEntity user)throws Exception;


    /**
     * 创建一个新的peer
     * @param peerName
     * @param peerLocation
     * @param properties
     * @return
     * @throws Exception
     */
    public Peer newPeer(String peerName, String peerLocation, Properties properties) throws Exception;


    /**
     * 创建一个新的orderer
     * @param orderName
     * @param orderLocation
     * @param properties
     * @return
     * @throws Exception
     */
    public Orderer newOrder(String orderName, String orderLocation, Properties properties) throws Exception;


    /**
     * 创建一个新的EventHub
     * @param eventHubName
     * @param eventHubLocation
     * @param properties
     * @return
     * @throws Exception
     */
    public EventHub newEventHub(String eventHubName, String eventHubLocation, Properties properties) throws Exception;

    /**
     * 创建一个chain
     * @param admin
     * @param chainName
     * @param chainConfiguration
     * @param orders
     * @param peers
     * @param eventHubs
     * @return
     * @throws Exception
     */
    public Chain newChainAndInitialize(UserEntity admin, String chainName, ChainConfiguration chainConfiguration, Collection<Orderer> orders, Collection<Peer> peers, Collection<EventHub> eventHubs) throws Exception;


    /**
     * install
     * @param chain
     * @param chainCodeID
     * @param chainCodeSourceLocation
     * @param version
     * @param type
     * @return
     * @throws Exception
     */
    public Collection<ProposalResponse> install(UserEntity user, Chain chain, ChainCodeID chainCodeID, String chainCodeSourceLocation, String version, TransactionRequest.Type type) throws Exception;


    /**
     * 数据初始化
     * @param user
     * @param chain
     * @param chainCodeID
     * @param args
     * @param fromYamlFilePath
     * @return
     * @throws Exception
     */
    public CompletableFuture<BlockEvent.TransactionEvent> instantiate(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args, String fromYamlFilePath) throws Exception;

    /**
     * 数据更新
     * @param user
     * @param chain
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public CompletableFuture<BlockEvent.TransactionEvent> update(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args) throws Exception;

    /**
     * 数据查询
     * @param user
     * @param chain
     * @param chainCodeID
     * @param args
     * @return
     * @throws Exception
     */
    public Collection<ProposalResponse> query(UserEntity user, Chain chain, ChainCodeID chainCodeID, String[] args) throws Exception;

}
