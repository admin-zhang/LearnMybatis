/**
 * Copyright (C), 2015-2020
 * FileName: Country
 * Author:   Administrator
 * Date:     2020/1/9 19:45
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.simple.model;

public class Country {
    private Long id;
    private String countryname;
    private String countrycode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}
