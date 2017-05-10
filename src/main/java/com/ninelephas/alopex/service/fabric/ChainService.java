package com.ninelephas.alopex.service.fabric;

import com.ninelephas.fabric.sdk.service.ClientService;
import org.hyperledger.fabric.sdk.Chain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zouwei on 2017/5/10.
 */
@Service("com.ninelephas.alopex.service.fabric.ChainService")
public class ChainService {

    private static final String FABRIC_ADMIN_SECRET = "Fabric.Admin.Secret";
    private static final String FABRIC_ADMIN_ADMIN_NAME = "Fabric.Admin.AdminName";

    @Autowired
    private ClientService clientService;

    public Chain getChain(){

        return null;
    }
}
