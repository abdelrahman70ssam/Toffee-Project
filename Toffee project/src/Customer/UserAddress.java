package Customer;

public class UserAddress {

    private String governorate;
    private String district;
    private String street;
    private String buildingNo;

    public UserAddress(String governorate,String district , String street , String buildingNo)
    {
        this.governorate = governorate;
        this.district = district;
        this.street = street;
        this.buildingNo = buildingNo;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    @Override
    public String toString()
    {
        return "governorate" + ' ' + getGovernorate() + ' ' + "District" + ' ' +getDistrict() + ' ' + "building no" + ' ' + getBuildingNo()+ "\n";
    }

}
