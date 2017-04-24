package com.ninelephas.fabric.sdk.service.impl;

import com.ninelephas.fabric.sdk.entity.UserEntity;

/**
 * Created by zouwei on 2017/4/21.
 */
public interface ClientService {

    boolean regist(UserEntity user)throws Exception;
}
