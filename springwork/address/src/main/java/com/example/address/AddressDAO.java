package com.example.address;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressDAO {
	public List<AddressDTO> list();
	
	public void insert(AddressDTO dto);
	
	public AddressDTO detail(int idx);
	
	public void update(AddressDTO dto);
	
	public void delete(int idx);
}
