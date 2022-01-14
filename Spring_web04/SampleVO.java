package kr.co.vo;

public class SampleVO {
	private int num;
	private String firstName;
	private String lastName;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "SampleVO [num=" + num + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
