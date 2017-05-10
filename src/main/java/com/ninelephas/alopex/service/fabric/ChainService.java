package com.ninelephas.alopex.service.fabric;

import com.ninelephas.common.configer.ConfigHelper;
import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.service.ClientService;
import org.apache.commons.configuration2.Configuration;
import org.hyperledger.fabric.sdk.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by zouwei on 2017/5/10.
 */
@Service("com.ninelephas.alopex.service.fabric.ChainService")
public class ChainService {

    private static final String FABRIC_ADMIN_SECRET = "Fabric.Admin.Secret";
    private static final String FABRIC_ADMIN_ADMIN_NAME = "Fabric.Admin.AdminName";
    private static final String FABRIC_ADMIN_MSPID = "Fabric.Admin.MspID";

    private static final String CHAIN_CONFIGURATION_FILE_PATH = "Fabric.ChainConfiguration.FilePath";

    private static final String FABRIC_ORDERER = "Fabric.Orderer.List";
    private static final String FABRIC_PEER = "Fabric.Peer.List";
    private static final String FABRIC_EVENTHUB = "Fabric.EventHub.List";

    private static Configuration configuration = ConfigHelper.getConfig();

    static {

    }

    @Autowired
    private ClientService clientService;

    /**
     * 获取一个已经存在的chain
     * @param chainName
     * @return
     */
    public Chain getChain(String chainName) {

        return clientService.getChain(chainName);
    }

    /**
     * 创建一个默认的chain
     * @return
     * @throws Exception
     */
    public Chain defaultChain() throws Exception {
        String chainName = "defaultChain";
        Chain defaultChain = getChain(chainName);
        if (defaultChain == null) {
            String secret = configuration.getString(FABRIC_ADMIN_SECRET);
            String adminName = configuration.getString(FABRIC_ADMIN_ADMIN_NAME);
            String mspID = configuration.getString(FABRIC_ADMIN_MSPID);
            String configPath = configuration.getString(CHAIN_CONFIGURATION_FILE_PATH);
            UserEntity admin = new UserEntity(adminName, mspID);
            admin.setSecret(secret);

            String[] orderers = configuration.getString(FABRIC_ORDERER).split(",");
            String[] peers = configuration.getString(FABRIC_PEER).split(",");
            String[] eventHubs = configuration.getString(FABRIC_EVENTHUB).split(",");
            Collection<Orderer> ordererCollection = newOrderers(orderers);
            Collection<Peer> peerCollection = newPeers(peers);
            Collection<EventHub> eventHubCollection = newEventHubs(eventHubs);

            defaultChain = clientService.newChainAndInitialize(admin, chainName, new ChainConfiguration(new File(configPath)), ordererCollection, peerCollection, eventHubCollection);
        }
        return defaultChain.isInitialized() ? defaultChain : defaultChain.initialize();
    }

    /**
     * 批量创建orderer
     * @param orderers
     * @return
     * @throws Exception
     */
    private Collection<Orderer> newOrderers(String[] orderers) throws Exception {
        Collection<Orderer> ordererCollection = new LinkedList<Orderer>();
        for (int i = 0; i < orderers.length; i++) {
            ordererCollection.add(clientService.newOrder("orderer" + i, orderers[i], null));
        }
        return ordererCollection;
    }

    /**
     * 批量创建peer
     * @param peers
     * @return
     * @throws Exception
     */
    private Collection<Peer> newPeers(String[] peers) throws Exception {
        Collection<Peer> peerCollection = new LinkedList<Peer>();
        for (int i = 0; i < peers.length; i++) {
            peerCollection.add(clientService.newPeer("peer" + i, peers[i], null));
        }
        return peerCollection;
    }

    /**
     * 批量创建eventHub
     * @param eventHubs
     * @return
     * @throws Exception
     */
    private Collection<EventHub> newEventHubs(String[] eventHubs) throws Exception {
        Collection<EventHub> eventHubCollection = new LinkedList<EventHub>();
        for (int i = 0; i < eventHubs.length; i++) {
            eventHubCollection.add(clientService.newEventHub("eventHub" + i, eventHubs[i], null));
        }
        return eventHubCollection;
    }
}
