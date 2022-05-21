package com.example.codesharingplatform.code;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findTop10ByIsTimeRestrictedFalseAndIsViewRestrictedFalseOrderByDateDesc();
}
