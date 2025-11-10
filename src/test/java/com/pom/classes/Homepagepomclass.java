package com.pom.classes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepagepomclass {

private WebDriver driver; 
	
	public Homepagepomclass(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module']")
	 WebElement heading;
	
	
	@FindBy(xpath="//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")
	WebElement outdropdown;
	
	@FindBy(xpath="//a[text()='Logout']")
	WebElement Logout;
	
	
	public boolean isheading()
	{
		try
		{
			return (heading.isDisplayed());
		}
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	
	public void clicdropdown()
	{
		outdropdown.click();
	}
	
	
	public void clicLogout()
	{
		Logout.click();
	}
	
	
	

}
