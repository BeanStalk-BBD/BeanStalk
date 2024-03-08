package com.beanstalk.beanstalk;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Users {
    @Id @GeneratedValue
    private int UserID;
    // @Nationalized
    private String UserName;
    private String OAuthUserName;
    private String Auth0ID;

    public Users(int UserID, String UserName, String OAuthUserName, String Auth0ID){
        this.UserID=UserID;
        this.UserName=UserName;
        this.OAuthUserName=OAuthUserName;
        this.Auth0ID=Auth0ID;

    }
    public int getUserID() {
        return this.UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getOAuthUserName() {
        return this.OAuthUserName;
    }

    public void setOAuthUserName(String OAuthUserName) {
        this.OAuthUserName = OAuthUserName;
    }

    public String getAuth0ID() {
        return this.Auth0ID;
    }

    public void setAuth0ID(String Auth0ID) {
        this.Auth0ID = Auth0ID;
    }

    @Override
    public String toString() {
        return "Users{" +
                "UserID=" + this.UserID +
                ", UserName='" + this.UserName + '\'' +
                ", OAuthUserName='" + this.OAuthUserName + '\'' +
                ", Auth0ID='" + this.Auth0ID + '\'' +
                '}';
    }

}
