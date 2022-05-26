package com.azka.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azka.demo.model.Return;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {

	List<Return> findAllByTokenAndSku(String token, String sku);
}
