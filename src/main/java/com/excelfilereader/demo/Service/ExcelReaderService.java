package com.excelfilereader.demo.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelfilereader.demo.Entity.Student;
import com.excelfilereader.demo.Model.StudentInput;
import com.excelfilereader.demo.Repository.ExcelReaderRepository;

@Service
public class ExcelReaderService {
	
	@Autowired
	private ExcelReaderRepository excelReaderRepository;

	public void readDataFromExcel() {
		try {
			List<StudentInput> tempStudents= new ArrayList<>();
			FileInputStream fis = new FileInputStream(new File("C:\\Users\\kavin\\OneDrive\\Desktop\\Eclipse Workspace\\ExcelFileReader\\data.xlsx"));
			XSSFWorkbook wb= new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			
			
			for(int i=1;i<sheet.getPhysicalNumberOfRows();i++) {
				StudentInput studentInput= new StudentInput();
				XSSFRow row = sheet.getRow(i);
				
				studentInput.setId(row.getCell(0).getStringCellValue());
				studentInput.setFirstName(row.getCell(1).getStringCellValue());
				if(row.getCell(2)!=null) {
				studentInput.setLastName(row.getCell(2).getStringCellValue());
				}
				studentInput.setDepartment(row.getCell(3).getStringCellValue());
				studentInput.setCity(row.getCell(4).getStringCellValue());
				studentInput.setRoles(row.getCell(5).getStringCellValue());
				studentInput.setGrade((int)row.getCell(6).getNumericCellValue());
				tempStudents.add(studentInput);
			}
			persistdata(tempStudents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void persistdata(List<StudentInput> tempStudents) {
		for(StudentInput input: tempStudents) {
			//The roles of sutudents are A- Admin, R-Representative, S-Sportsperson, T-Topper
			//A student can have many roles and we will be creating multiple records for student having multiple roles
			//The id is the combination of StudentInput Id and Roles eg: 14ME063.R and 14ME063.A are two different records in the table
			String[] rolesArray = input.getRoles().split(",");
			for(int i=0;i<rolesArray.length;i++) {
				Student student=new Student();
//				student.setId((input.getId()+"."+rolesArray[i]));
				student.setId(input.getId()+i);
				student.setFirstName(input.getFirstName());
				student.setLastName(input.getLastName());
				student.setDepartment(input.getDepartment());
				student.setCity(input.getCity());
				student.setGrade(input.getGrade());
				excelReaderRepository.save(student);
			}
		}
	}


}
