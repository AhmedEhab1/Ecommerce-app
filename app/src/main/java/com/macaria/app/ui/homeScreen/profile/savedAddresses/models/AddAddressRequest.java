package com.macaria.app.ui.homeScreen.profile.savedAddresses.models;

public class AddAddressRequest {
    String address, zip_code, country_id, city_id, building_number, apartment_no,
            flat_no, first_name, last_name, phone;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public void setBuilding_number(String building_number) {
        this.building_number = building_number;
    }

    public void setApartment_no(String apartment_no) {
        this.apartment_no = apartment_no;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
