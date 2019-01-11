package com.fordperformanceclubofact.groupb.Sponsor;

public class SponsorList {

    String name,supply,phone,rate;

    public SponsorList() {
    }

    public SponsorList(String name, String supply, String phone, String rate) {
        this.name = name;
        this.supply = supply;
        this.phone = phone;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
