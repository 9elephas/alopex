package com.ninelephas.fabric.sdk.entity;

import io.netty.util.internal.StringUtil;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zouwei on 2017/4/18.
 * 必须要实现fabric的User接口
 */
public class UserEntity implements User, Serializable {

    private static final long serialVersionUID = 8077132186383604355L;
    /**
     * 用户名，唯一确定一个用户，相当于id
     */
    private String name;
    /**
     * 用户角色
     */
    private ArrayList<String> roles;
    /**
     *
     */
    private String account;
    /**
     * 部门
     */
    private String affiliation;
    /**
     * 注册后的用户该字段不能为空
     */
    private Enrollment enrollment;

    private String mspID;
    /**
     * 注册后的用户该字段不能为空
     */
    private String secret;
    /**
     * 指定该用户所属管理员，该管理员必须已经存在
     */
    private UserEntity admin;

    public UserEntity(){
        super();
    }

    public UserEntity(String name, String mspID) {
        super();
        this.name = name;
        this.mspID = mspID;
    }

    public boolean isEnrolled() {
        return this.enrollment != null;
    }

    public boolean isRegistered() {
        return !StringUtil.isNullOrEmpty(this.secret);
    }

    public void setAdmin(UserEntity admin) {
        this.admin = admin;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public void setMspID(String mspID) {
        this.mspID = mspID;
    }

    public String getSecret() {
        return secret;
    }

    public UserEntity getAdmin() {
        return this.admin;
    }

    public Boolean isAdmin() {
        return this.admin == null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ArrayList<String> getRoles() {
        return this.roles;
    }

    @Override
    public String getAccount() {
        return this.account;
    }

    @Override
    public String getAffiliation() {
        return this.affiliation;
    }

    @Override
    public Enrollment getEnrollment() {
        return this.enrollment;
    }

    @Override
    public String getMSPID() {
        return this.mspID;
    }


}
