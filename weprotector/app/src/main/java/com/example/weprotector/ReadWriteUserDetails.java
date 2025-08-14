package com.example.weprotector;

public class ReadWriteUserDetails {

    public String NAME,PHONE,EMAIL,PASSWORD;

    public ReadWriteUserDetails()
    {

    };


    public ReadWriteUserDetails(String textName,String textPhone,String textEmail,String textPassword)
    {
        this.NAME = textName;
        this.PHONE = textPhone;
        this.EMAIL = textEmail;
        this.PASSWORD = textPassword;
    }

}
