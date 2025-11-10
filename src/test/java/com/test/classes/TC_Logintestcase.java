package com.test.classes;

import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pom.classes.Homepagepomclass;
import com.pom.classes.Loginpagepomclass;

import Testbase.Baseclass;

public class TC_Logintestcase extends Baseclass{

	@Test(groups={"Sanity", "Master"})
	public void logintestclass() throws InterruptedException
	{
		
		System.out.println("***Login Test case Start ***");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		try
		{
		Loginpagepomclass login =new Loginpagepomclass(driver);
		login.clickusername(p.getProperty("Username"));
		login.clickpassword(p.getProperty("Password"));
		login.clickloginbtn();
		Thread.sleep(5000);
		
		Homepagepomclass home =new Homepagepomclass(driver);
		boolean myaccpage=home.isheading();
		Assert.assertTrue(myaccpage);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		System.out.println("***Login Test case Finish***");
	}
}
