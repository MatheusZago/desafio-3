package br.com.compass.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("cep")           // Mapeia o campo "cep" do JSON para zipCode
    private String zipCode;

    @SerializedName("logradouro")     // Mapeia o campo "logradouro" do JSON para street
    private String street;

    @SerializedName("complemento")    // Mapeia o campo "complemento" do JSON para complement
    private String complement;

    @SerializedName("bairro")         // Mapeia o campo "bairro" do JSON para neighborhood
    private String neighborhood;

    @SerializedName("localidade")     // Mapeia o campo "localidade" do JSON para city
    private String city;

    @SerializedName("uf")             // Mapeia o campo "uf" do JSON para state
    private String state;

    public Address(){}

    public Address(String zipCode, String street, String complement, String neighborhood, String city, String state) {
        this.zipCode = zipCode;
        this.street = street;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
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

    @Override
    public String toString() {
        return "Address{" +
                "zipCode='" + zipCode + '\'' +
                ", street='" + street + '\'' +
                ", complement='" + complement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
