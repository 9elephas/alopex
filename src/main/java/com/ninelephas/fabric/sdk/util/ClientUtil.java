package com.ninelephas.fabric.sdk.util;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

/**
 * Created by zouwei on 2017/4/18.
 */
public class ClientUtil {


    private static ThreadLocal<HFClient> clientPool = new ThreadLocal<HFClient>();


    /**
     * 获取client
     *
     * @return
     * @throws Exception
     */
    public static HFClient getClient() {
        HFClient client = clientPool.get();
        try {
            if (client == null) {
                client = HFClient.createNewInstance();
                client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clientPool.set(client);
        }
        return client;
    }


    public static void closeClient() {
        HFClient client = clientPool.get();
        client = null;
        clientPool.remove();
    }

}
