package model;

public class StuVO extends ProVO {
	//여기에 pname이 없어도 extends를 했기 때문에 StuDAO에서 Pname을 setting하는 것이 가능하다.
	private String dept;
	private String sname;
	private String scode;
	private String year;
	private String advisor;
	private String birthday;
	@Override
	public String toString() {
		return "StuVO [dept=" + dept + ", sname=" + sname + ", scode=" + scode + ", year=" + year + ", advisor="
				+ advisor + ", birthday=" + birthday + "]";
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAdvisor() {
		return advisor;
	}
	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	

}
