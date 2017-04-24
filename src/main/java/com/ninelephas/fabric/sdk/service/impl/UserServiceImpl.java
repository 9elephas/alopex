package com.ninelephas.fabric.sdk.service.impl;

import com.ninelephas.fabric.sdk.entity.UserEntity;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

/**
 * Created by zouwei on 2017/4/19.
 */
public class UserServiceImpl {

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
     * 用户注册
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

}
