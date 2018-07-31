package com.example.e_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.e_dnevnik.entities.Administrator;

public interface AdminRepository extends CrudRepository<Administrator, Integer> {

}
