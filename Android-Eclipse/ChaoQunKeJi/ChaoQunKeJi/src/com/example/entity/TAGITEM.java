package com.example.entity;

import java.util.List;

public class TAGITEM {
	private List<String> CRFID;
	private String userid;
	private String cposition;
	private String iifuncregedit;
	private String warrants_iwhstate;
	private String cmemo;
	
	
	public String getCmemo() {
		return cmemo;
	}
	public void setCmemo(String cmemo) {
		this.cmemo = cmemo;
	}
	public List<String> getCRFID() {
		return CRFID;
	}
	public void setCRFID(List<String> cRFID) {
		CRFID = cRFID;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCposition() {
		return cposition;
	}
	public void setCposition(String cposition) {
		this.cposition = cposition;
	}
	public String getIifuncregedit() {
		return iifuncregedit;
	}
	public void setIifuncregedit(String iifuncregedit) {
		this.iifuncregedit = iifuncregedit;
	}
	public String getWarrants_iwhstate() {
		return warrants_iwhstate;
	}
	public void setWarrants_iwhstate(String warrants_iwhstate) {
		this.warrants_iwhstate = warrants_iwhstate;
	}
	
	
	

}
