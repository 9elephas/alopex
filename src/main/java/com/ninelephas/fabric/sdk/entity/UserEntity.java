package com.ninelephas.fabric.sdk.entity;

import io.netty.util.internal.StringUtil;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zouwei on 2017/4/18.
 */
public class UserEntity implements User, Serializable {

    private static final long serialVersionUID = 8077132186383604355L;

    private String name;

    private ArrayList<String> roles;

    private String account;

    private String affiliation;

    private Enrollment enrollment;

    private String mspID;

    private String secret;

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
