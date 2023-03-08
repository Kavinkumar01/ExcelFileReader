package com.excelfilereader.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelfilereader.demo.Entity.Student;

@Repository
public interface ExcelReaderRepository extends JpaRepository<Student, String>{

}
