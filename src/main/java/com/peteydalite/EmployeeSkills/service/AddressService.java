package com.peteydalite.EmployeeSkills.service;

import com.peteydalite.EmployeeSkills.model.Address;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AddressService implements AddressDao {

    private JdbcTemplate jdbcTemplate;

    public AddressService(JdbcTemplate jdbc){
        this.jdbcTemplate = jdbc;
    }

    private Address mapRowToAddress(SqlRowSet rs){
        Address temp = new Address();
        temp.setAddress_id((java.util.UUID) rs.getObject("address_id") );
        temp.setStreet(rs.getString("street"));
        temp.setSuite(rs.getString("suite"));
        temp.setCity(rs.getString("city"));
        temp.setRegion(rs.getString("region"));
        temp.setPostal(rs.getLong("postal"));
        temp.setCountry(rs.getString("country"));

        return temp;
    }

    @Override
    public List<Address> getAllAddress() {
        List<Address> allAddress = new ArrayList<>();
        String sqlSelect = "Select * from Address ";
        try{
            SqlRowSet result = this.jdbcTemplate.queryForRowSet(sqlSelect);
            while(result.next()){
                Address address = mapRowToAddress(result);
                allAddress.add(address);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return allAddress;
    }

    @Override
    public Address getAddressById(UUID address_id) {
        Address address = new Address();
        String sqlSelect = "Select * from Address where address_id = ? ";

        try{
            SqlRowSet result = this.jdbcTemplate.queryForRowSet(sqlSelect, address_id);
            if(result.next()){
                address = mapRowToAddress(result);
            }
        }catch (Exception e){
            System.out.println(e);
        }

        return address;
    }

    @Override
    public boolean updateAddress(Address updateAddress) {
        boolean updated = false;
        String sqlUpdate= "Update Address set " +
                            "street = ?, suite = ?, city = ?, region = ?, " +
                            "postal = ?, country = ? where address_id = ?";
        try{
            updated  = this.jdbcTemplate.update(sqlUpdate, updateAddress.getStreet(),
                    updateAddress.getSuite(), updateAddress.getCity(), updateAddress.getRegion(),
                    updateAddress.getPostal(), updateAddress.getCountry(), updateAddress.getAddress_id()) ==1;

        }catch(Exception e){
            System.out.println(e);
        }
        return updated;
    }

    @Override
    public boolean deleteAddress(Address address) {
        boolean deleted = false;
        String sqlDelete = "Delete from Address where address_id = ? ";
        try{
            deleted = this.jdbcTemplate.update(sqlDelete, address.getAddress_id()) == 1;
        }catch(Exception e){
            System.out.println(e);
        }
        return  deleted;
    }

    @Override
    public boolean addAddress(Address newAddress) {
        boolean added = false;
        String sqlInsert = "Insert into Address Values(?,?,?,?,?,?,?) ";
        try{
            added = this.jdbcTemplate.update(sqlInsert, UUID.randomUUID(), newAddress.getStreet(),
                    newAddress.getSuite(), newAddress.getCity(), newAddress.getRegion(),
                    newAddress.getPostal(), newAddress.getCountry()) ==1;

        }catch(Exception e){
            System.out.println(e);
        }
        return added;
    }
}
