package com.excelfilereader.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.excelfilereader.demo.Service.ExcelReaderService;

@SpringBootApplication
public class ExcelFileReaderApplication {

	public static void main(String[] args) {
		ApplicationContext context= SpringApplication.run(ExcelFileReaderApplication.class, args);
		ExcelReaderService bean=context.getBean(ExcelReaderService.class);
		bean.readDataFromExcel();
	}
}
