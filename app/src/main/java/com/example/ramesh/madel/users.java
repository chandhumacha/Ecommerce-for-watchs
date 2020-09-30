package com.example.ramesh.madel;

public class users
{
    private String name,password,phone;
    public users()
    {

    }
    public users(String name,String password,String phone)
    {
        this.name=name;
        this.password=password;
        this.phone=phone;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
