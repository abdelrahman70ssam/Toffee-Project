package Customer;
import java.util.Scanner;
import java.lang.System;
import java.util.ArrayList;

public class Voucher {
    private int code;

    private int value ;
    private String expiryDate;
    private ArrayList<Voucher> voucherList;
    private User user;


    public void setMoney(int value){
        this.value = 50 ;
    }

    public int getMoney() {
        return value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public ArrayList<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(ArrayList<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addVoucher(Voucher obj)
    {
        voucherList.add(obj);
    }

    @Override
    public String toString()
    {
       return "Voucher Code" + ' ' + getCode() + ' ' + "Expiry Date" + ' ' +getExpiryDate() + "\n";
    }

}
