package com.example.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.address.entity.AddressBook;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
}