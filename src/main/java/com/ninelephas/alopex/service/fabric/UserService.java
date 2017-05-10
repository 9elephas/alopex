/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.service.fabric;

import com.ninelephas.alopex.controller.pojo.FabricUser;
import com.ninelephas.common.configer.ConfigHelper;
import com.ninelephas.common.helper.JsonUtilsHelper;
import com.ninelephas.fabric.sdk.entity.UserEntity;
import com.ninelephas.fabric.sdk.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.hyperledger.fabric.sdk.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

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


    private static final String FABRIC_ADMIN_SECRET = "Fabric.Admin.Secret";
    private static final String FABRIC_ADMIN_ADMIN_NAME = "Fabric.Admin.AdminName";

    @Autowired
    private ClientService clientService;


    /**
     *
     * @param params
     * {"mspID":"mspID","userName":"userName","affiliation":"affiliation"}
     *
     *
     * @return
     * @throws Exception
     */
    public FabricUser register(String params) throws Exception {
        Configuration configuration = ConfigHelper.getConfig();
        String secret = configuration.getString(FABRIC_ADMIN_SECRET);
        String adminName = configuration.getString(FABRIC_ADMIN_ADMIN_NAME);

        FabricUser fabricUser = JsonUtilsHelper.parseStringToObject(params,FabricUser.class);

        //创建用户
        String mspID = fabricUser.getMspID();
        UserEntity admin = new UserEntity(adminName, mspID);
        admin.setSecret(secret);
        UserEntity user = new UserEntity(fabricUser.getUserName(), mspID);
        user.setAffiliation(fabricUser.getAffiliation());
        user.setAdmin(admin);
        if(clientService.regist(user)){
            fabricUser.setFormat(user.getEnrollment().getKey().getFormat());
            fabricUser.setEncoded(user.getEnrollment().getKey().getEncoded());
            fabricUser.setPublicKey(user.getEnrollment().getPublicKey());
            fabricUser.setCert(user.getEnrollment().getCert());
            fabricUser.setAlgorithm(user.getEnrollment().getKey().getAlgorithm());
            fabricUser.setRoles(user.getRoles());


            //返回注册后的用户
            return fabricUser;
        }
        return null;
    }
}
