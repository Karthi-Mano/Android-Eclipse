package com.example.entity;


public class Information {
	private  String CPOSITION;
	private String CCUSTOMER;
	private String CRFID;
	private String DEPARTMENT ;
	private String CCLTYPE;
	private String CCLCODE;
	private String CWHSTATE;
	
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
	public Information(String cPOSITION, String cCUSTOMER, String cRFID,
			String dEPARTMENT, String cCLTYPE, String cCLCODE) {
		super();
		CPOSITION = cPOSITION;
		CCUSTOMER = cCUSTOMER;
		CRFID = cRFID;
		DEPARTMENT = dEPARTMENT;
		CCLTYPE = cCLTYPE;
		CCLCODE = cCLCODE;
	}
	public Information() {
		super();
	}
	
	
	
	

}
