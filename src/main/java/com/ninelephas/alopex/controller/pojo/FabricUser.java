/*
 * Copyright (c) 2017. 九象网络科技 - All Rights Reserved.
 * @author 徐泽宇
 *
 *
 */

package com.ninelephas.alopex.controller.pojo;

import lombok.Data;
import org.hyperledger.fabric.sdk.Enrollment;

import java.util.ArrayList;

/**
 * 阿斯蒂芬
 * Fabric 的用户类
 *
 * @author roamer
 * @create 2017-05-2017/5/3  下午4:08
 */
@Data
public class FabricUser {
    private String mspID;
    private String userName ;
    private String affiliation;
    private String publicKey;
    private String cert;
    private String format;
    private String algorithm;
    private byte[] encoded;
    private ArrayList<String> roles;
}
