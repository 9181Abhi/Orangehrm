package com.pom.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Loginpagepomclass {
	
	private WebDriver driver; 
	
	public Loginpagepomclass(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[@name='username']")
	private WebElement Username;
	
	
	@FindBy(xpath="//input[@name='password']")
	private WebElement Password;
	
	@FindBy(xpath="//button[@class='oxd-button oxd-button--medium oxd-button--main orangehrm-login-button']")
	private WebElement Login;
	
	
	
	public void clickusername(String user)
	{
		Username.sendKeys(user);
	}
	
	
	public void clickpassword(String pass)
	{
		Password.sendKeys(pass);
	}
	
	public void clickloginbtn()
	{
		Login.click();
	}
}	



