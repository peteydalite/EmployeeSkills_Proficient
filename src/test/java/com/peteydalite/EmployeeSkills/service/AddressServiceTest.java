package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.model.Address;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.Assert.*;

public class AddressServiceTest extends DaoIntergrationTest{
    private AddressService addressServive;
    private JdbcTemplate jdbc;
    private DataSource dataSource;
    @Before
    public void setup() {
        dataSource = this.getDataSource();
        jdbc = new JdbcTemplate(dataSource);
        addressServive = new AddressService(this.jdbc);
    }

    @Test
    public void addAddress() {
        Address test = new Address();
        test.setStreet("123 Fake St");
        test.setCity("Chitty");
        test.setRegion("OH");
        test.setPostal(12345L);
        test.setCountry("United States");

        boolean result = this.addressServive.addAddress(test);

        assertEquals(true, result);
    }
    @Test
    public void getAllAddress() {

        Address test = new Address();
        test.setStreet("123 Fake St");
        test.setCity("Chitty");
        test.setRegion("OH");
        test.setPostal(12345L);
        test.setCountry("United States");

        this.addressServive.addAddress(test);
        List<Address> beforeAdd = this.addressServive.getAllAddress();

        this.addressServive.addAddress(test);
        List<Address> afterAdd = this.addressServive.getAllAddress();

        assertEquals(beforeAdd.size() + 1, afterAdd.size());
    }

    @Test
    public void getAddressById() {
        Address test = new Address();
        test.setStreet("123 Fake St");
        test.setCity("Chitty");
        test.setRegion("OH");
        test.setPostal(12345L);
        test.setCountry("United States");

        List<Address> listBefore = this.addressServive.getAllAddress();
        this.addressServive.addAddress(test);
        List<Address> addressList = this.addressServive.getAllAddress();

        assertEquals(listBefore.size() + 1, addressList.size());

        Address result = this.addressServive.getAddressById(addressList.get(0).getAddress_id());

        assertEquals(addressList.get(0).getAddress_id(), result.getAddress_id());
    }

    @Test
    public void updateAddress() {
        Address test = new Address();
        test.setStreet("123 Fake St");
        test.setCity("Test");
        test.setRegion("MD");
        test.setPostal(12345L);
        test.setCountry("United States");

        this.addressServive.addAddress(test);
        List<Address> addressList = this.addressServive.getAllAddress();
        Address toChange = addressList.get(0);

        toChange.setCountry("TEST");
        this.addressServive.updateAddress(toChange);

        Address changed = this.addressServive.getAddressById(toChange.getAddress_id());
        System.out.println(changed.toString());
        assertEquals(toChange.getCountry().trim(), changed.getCountry().trim());
    }

    @Test
    public void deleteAddress() {
        Address test = new Address();
        test.setStreet("123 Fake St");
        test.setCity("Chitty");
        test.setRegion("OH");
        test.setPostal(12345L);
        test.setCountry("United States");

        this.addressServive.addAddress(test);
        List<Address> addressList = this.addressServive.getAllAddress();
        boolean result = this.addressServive.deleteAddress(addressList.get(0));

        assertEquals(true, result);



    }


}