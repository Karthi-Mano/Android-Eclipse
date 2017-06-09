package com.example.entity;

public class SelectInformation {

	private String CCODE;//资产编码
	private String CNAME;//资产名称
	private String CPCTEL;//联系电话
	private String DBEGIN;//使用日期
	private String FVALUE;//资产原值
	private String ICLASS_NAME;//资产类别
	private String ICUSTOMER_NAME;//服务厂商
	
	private String IDEPARTMENT_NAME;//机构网点
	private String IPCPERSON_NAME;//联系人员

	public String getCCODE() {
		return CCODE;
	}

	public void setCCODE(String cCODE) {
		CCODE = cCODE;
	}

	public String getCNAME() {
		return CNAME;
	}

	public void setCNAME(String cNAME) {
		CNAME = cNAME;
	}

	public String getICLASS_NAME() {
		return ICLASS_NAME;
	}

	public void setICLASS_NAME(String iCLASS_NAME) {
		ICLASS_NAME = iCLASS_NAME;
	}

	public String getIDEPARTMENT_NAME() {
		return IDEPARTMENT_NAME;
	}

	public void setIDEPARTMENT_NAME(String iDEPARTMENT_NAME) {
		IDEPARTMENT_NAME = iDEPARTMENT_NAME;
	}

	public String getDBEGIN() {
		return DBEGIN;
	}

	public void setDBEGIN(String dBEGIN) {
		DBEGIN = dBEGIN;
	}

	public String getFVALUE() {
		return FVALUE;
	}

	public void setFVALUE(String fVALUE) {
		FVALUE = fVALUE;
	}


	public String getICUSTOMER_NAME() {
		return ICUSTOMER_NAME;
	}

	public void setICUSTOMER_NAME(String iCUSTOMER_NAME) {
		ICUSTOMER_NAME = iCUSTOMER_NAME;
	}

	public String getIPCPERSON_NAME() {
		return IPCPERSON_NAME;
	}

	public void setIPCPERSON_NAME(String iPCPERSON_NAME) {
		IPCPERSON_NAME = iPCPERSON_NAME;
	}

	public String getCPCTEL() {
		return CPCTEL;
	}

	public void setCPCTEL(String cPCTEL) {
		CPCTEL = cPCTEL;
	}

}
