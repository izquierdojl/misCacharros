package com.jlib.miscacharros.modelo;

/**
 * Clase para los contactos o modelo
 */
public class Contacto
{

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String state;
    private String country;
    private String telephone;
    private String email;
    private String web;
    private GeoPunto geopunto;

    public Contacto()
    {
        this.geopunto = new GeoPunto();
    }

    public Contacto(int id, String name, String address, String postalCode, String city, String state, String country, String telephone, String email, String web, GeoPunto geopunto) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.telephone = telephone;
        this.email = email;
        this.web = web;
        this.geopunto = geopunto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public GeoPunto getGeopunto() {
        return geopunto;
    }

    public void setGeopunto(GeoPunto geopunto) {
        this.geopunto = geopunto;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", web='" + web + '\'' +
                ", geopunto=" + geopunto +
                '}';
    }
}
