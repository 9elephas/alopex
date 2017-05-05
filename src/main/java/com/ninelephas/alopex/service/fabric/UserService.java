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
import com.ninelephas.fabric.sdk.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 阿斯蒂芬
 * Fabric 用户 Service 类
 *
 * @author roamer
 * @create 2017-05-2017/5/3  下午6:03
 */
@Log4j2
@Service("com.ninelephas.alopex.service.fabric.UserService")
public class UserService {

    /**
     * 通过spring配置构造函数方式实现IOC
     */
    @Autowired
    private ClientService clientService;


    public void register(FabricUser fabricUser) throws Exception {
        Configuration configuration = ConfigHelper.getConfig();
        String secret = configuration.getString("Fabric.Admin.Secret");

        //clientService = new ClientServiceImpl(caUrl);

        //创建用户
        String mspID = fabricUser.getMspID();
        UserEntity admin = new UserEntity("admin", mspID);
        admin.setSecret(secret);
        UserEntity user = new UserEntity(fabricUser.getUserName(), mspID);
        user.setAffiliation(fabricUser.getAffiliation());
        user.setAdmin(admin);
        clientService.regist(user);

    }
}
