package com.example.entity;


public class Information {
	private  String CPOSITION;
	private String CCUSTOMER;//借款人
	private String CRFID;
	private String DEPARTMENT ;//机构网点
	private String CCLTYPE;//权证类型
	private String CCLCODE;//权证编号
	private String CWHSTATE;
	private String CCODE;//入账单号
	private String CCOLLATERAL;//质权人
	private boolean isclick;
	
	
	public String getCCODE() {
		return CCODE;
	}
	public void setCCODE(String cCODE) {
		CCODE = cCODE;
	}
	public String getCCOLLATERAL() {
		return CCOLLATERAL;
	}
	public void setCCOLLATERAL(String cCOLLATERAL) {
		CCOLLATERAL = cCOLLATERAL;
	}
	public boolean isIsclick() {
		return isclick;
	}
	public void setIsclick(boolean isclick) {
		this.isclick = isclick;
	}
	public String getCPOSITION() {
		return CPOSITION;
	}
	public void setCPOSITION(String cPOSITION) {
		CPOSITION = cPOSITION;
	}
	public String getCCUSTOMER() {
		return CCUSTOMER;
	}
	public void setCCUSTOMER(String cCUSTOMER) {
		CCUSTOMER = cCUSTOMER;
	}
	public String getCRFID() {
		return CRFID;
	}
	public void setCRFID(String cRFID) {
		CRFID = cRFID;
	}
	public String getDEPARTMENT() {
		return DEPARTMENT;
	}
	public void setDEPARTMENT(String dEPARTMENT) {
		DEPARTMENT = dEPARTMENT;
	}
	public String getCCLTYPE() {
		return CCLTYPE;
	}
	public void setCCLTYPE(String cCLTYPE) {
		CCLTYPE = cCLTYPE;
	}
	public String getCCLCODE() {
		return CCLCODE;
	}
	public void setCCLCODE(String cCLCODE) {
		CCLCODE = cCLCODE;
	}
	
	public String getCWHSTATE() {
		return CWHSTATE;
	}
	public void setCWHSTATE(String cWHSTATE) {
		CWHSTATE = cWHSTATE;
	}
	
	
	
	
	

}
