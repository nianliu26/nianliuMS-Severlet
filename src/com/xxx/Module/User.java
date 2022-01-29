package com.xxx.Module;

public class User {
    static int userID;
    static String userName;
    static String userPWD;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        User.userID = userID;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static String getUserPWD() {
        return userPWD;
    }

    public static void setUserPWD(String userPWD) {
        User.userPWD = userPWD;
    }
}
