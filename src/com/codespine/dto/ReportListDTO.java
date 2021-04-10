package com.codespine.dto;

import java.util.List;

public class ReportListDTO {
	private List<String> columnNames; 
	private List<ReportOutputDTO> reportOutputDTOs;
	public List<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}
	public List<ReportOutputDTO> getReportOutputDTOs() {
		return reportOutputDTOs;
	}
	public void setReportOutputDTOs(List<ReportOutputDTO> reportOutputDTOs) {
		this.reportOutputDTOs = reportOutputDTOs;
	}
	

}
