/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.service.fabric;

import com.ninelephas.alopex.controller.pojo.FabricUser;
import com.ninelephas.common.configer.ConfigHelper;
import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.service.impl.ClientService;
import com.ninelephas.fabric.sdk.service.impl.ClientServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.springframework.stereotype.Service;

/**
 * 阿斯蒂芬
 * Fabric 用户 Service 类
 *
 * @author roamer
 * @create 2017-05-2017/5/3  下午6:03
 */
@Log4j2
@Service(" com.ninelephas.alopex.service.fabric.UserService")
public class UserService {

    private ClientService clientService;

    public void register(FabricUser fabricUser) throws Exception {
        Configuration configuration = ConfigHelper.getConfig();
        //CA url
        String caUrl = configuration.getString("Fabric.CA.URL");
        String secret = configuration.getString("Fabric.Admin.Secret");

        clientService = new ClientServiceImpl(caUrl);
        //创建用户
        String mspID = "Org1MSP";
        UserEntity admin = new UserEntity(fabricUser.getUserName(), mspID);
        admin.setSecret(secret);
        UserEntity user = new UserEntity("user1", mspID);
        user.setAffiliation("org1.department1");
        user.setAdmin(admin);
        boolean res = clientService.regist(user);
    }
}
