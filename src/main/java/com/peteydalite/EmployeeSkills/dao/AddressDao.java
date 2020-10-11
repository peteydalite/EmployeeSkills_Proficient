package com.peteydalite.EmployeeSkills.dao;

import com.peteydalite.EmployeeSkills.model.Address;

import java.util.List;
import java.util.UUID;

public interface AddressDao {
    List<Address> getAllAddress();
    Address getAddressById(UUID address_id);
    boolean updateAddress(Address updateAddress);
    boolean deleteAddress(Address address);
    boolean addAddress(Address newAddress);
}
