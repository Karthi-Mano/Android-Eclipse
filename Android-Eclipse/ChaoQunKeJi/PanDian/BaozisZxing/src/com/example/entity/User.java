package com.example.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private String canswer;
	private String ccode;
	private String cemail;
	private String cmobile1;
	private String cname;
	private String corpname;
	private String cqq;
	private String cquestion;
	private String cusecode;
	private String cusepassword;
	private String icorp;
	private String idepartment;
	private String iid;
	private String iuhfconfig;

	public String getCanswer() {
		return canswer;
	}

	public void setCanswer(String canswer) {
		this.canswer = canswer;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getCemail() {
		return cemail;
	}

	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	public String getCmobile1() {
		return cmobile1;
	}

	public void setCmobile1(String cmobile1) {
		this.cmobile1 = cmobile1;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public String getCqq() {
		return cqq;
	}

	public void setCqq(String cqq) {
		this.cqq = cqq;
	}

	public String getCquestion() {
		return cquestion;
	}

	public void setCquestion(String cquestion) {
		this.cquestion = cquestion;
	}

	public String getCusecode() {
		return cusecode;
	}

	public void setCusecode(String cusecode) {
		this.cusecode = cusecode;
	}

	public String getCusepassword() {
		return cusepassword;
	}

	public void setCusepassword(String cusepassword) {
		this.cusepassword = cusepassword;
	}

	public String getIcorp() {
		return icorp;
	}

	public void setIcorp(String icorp) {
		this.icorp = icorp;
	}

	public String getIdepartment() {
		return idepartment;
	}

	public void setIdepartment(String idepartment) {
		this.idepartment = idepartment;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getIuhfconfig() {
		return iuhfconfig;
	}

	public void setIuhfconfig(String iuhfconfig) {
		this.iuhfconfig = iuhfconfig;
	}

	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			User u = new User();
			u.canswer = source.readString();
			u.ccode = source.readString();
			u.cemail = source.readString();
			u.cmobile1 = source.readString();
			u.cname = source.readString();
			u.corpname = source.readString();
			u.cqq = source.readString();
			u.cquestion = source.readString();
			u.cusecode = source.readString();
			u.cusepassword = source.readString();
			u.icorp = source.readString();
			u.idepartment = source.readString();
			u.iid = source.readString();
			u.iuhfconfig = source.readString();
			return u;
		}

		@Override
		public User[] newArray(int size) {
			// TODO Auto-generated method stub
			return new User[size];
		}

	};

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(canswer);
		dest.writeString(ccode);
		dest.writeString(cemail);
		dest.writeString(cmobile1);
		dest.writeString(cname);
		dest.writeString(corpname);
		dest.writeString(cqq);
		dest.writeString(cquestion);
		dest.writeString(cusecode);
		dest.writeString(cusepassword);
		dest.writeString(icorp);
		dest.writeString(idepartment);
		dest.writeString(iid);
		dest.writeString(iuhfconfig);

	}

}
