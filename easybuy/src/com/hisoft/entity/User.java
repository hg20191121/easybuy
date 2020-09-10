package com.hisoft.entity;

import com.hisoft.util.EncryptionUtil;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;

public class User {

    private static Logger logger = Logger.getLogger(User.class);
    //id,主键
    private Integer u_id;
    //用户名
    private String  u_loginName;
    //用户真实姓名
    private String u_userName;
    //密码
    private String u_password;
    //性别,1为男,0为女
    private Character u_sex;
    //身份证号
    private String u_identityCode;
    //邮箱
    private String u_email;
    //电话
    private String u_mobile;
    //1为普通用户,2为管理员
    private int u_type;


    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getU_loginName() {
        return u_loginName;
    }

    public void setU_loginName(String u_loginName) {
        this.u_loginName = u_loginName;
    }

    public String getU_userName() {
        return u_userName;
    }

    public void setU_userName(String u_userName) {
        this.u_userName = u_userName;
    }

    public String getU_password() {
        return u_password;
    }

    /**
     * 设置密码的方法会默认加密
     * @param u_password
     */
    public void setU_password(String u_password) {
        try {
            this.u_password = EncryptionUtil.str2Md5(u_password,"utf-8");
        } catch (NoSuchAlgorithmException e) {
            logger.error("给用户密码加密失败");
            this.u_password = u_password;
            e.printStackTrace();
            this.u_password = u_password;
        }
    }

    public Character getU_sex() {
        return u_sex;
    }

    public void setU_sex(Character u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_identityCode() {
        return u_identityCode;
    }

    public void setU_identityCode(String u_identityCode) {
        this.u_identityCode = u_identityCode;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_mobile() {
        return u_mobile;
    }

    public void setU_mobile(String u_mobile) {
        this.u_mobile = u_mobile;
    }

    public int getU_type() {
        return u_type;
    }

    public void setU_type(int u_type) {
        this.u_type = u_type;
    }

    public User() {
    }

    public User(String u_password) {
        this.u_password = u_password;
    }

    public User(Integer u_id, String u_loginName, String u_userName, String u_password, Character u_sex, String u_identityCode, String u_email, String u_mobile, int u_type) {
        this.u_id = u_id;
        this.u_loginName = u_loginName;
        this.u_userName = u_userName;
        this.u_password = u_password;
        this.u_sex = u_sex;
        this.u_identityCode = u_identityCode;
        this.u_email = u_email;
        this.u_mobile = u_mobile;
        this.u_type = u_type;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id=" + u_id +
                ", u_loginName='" + u_loginName + '\'' +
                ", u_userName='" + u_userName + '\'' +
                ", u_password='" + u_password + '\'' +
                ", u_sex=" + u_sex +
                ", u_identityCode='" + u_identityCode + '\'' +
                ", u_email='" + u_email + '\'' +
                ", u_mobile='" + u_mobile + '\'' +
                ", u_type=" + u_type +
                '}';
    }
}
