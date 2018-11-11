package example.app.form;

import java.io.Serializable;
import java.util.List;

import example.app.dto.ProperDataDto;

public class LoginForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;
	
	private List<ProperDataDto> properBaseData;
	
	public LoginForm(String a, String b) {
		this.username = a;
		this.password = b;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ProperDataDto> getProperBaseData() {
		return properBaseData;
	}

	public void setProperBaseData(List<ProperDataDto> properBaseData) {
		this.properBaseData = properBaseData;
	}


}
