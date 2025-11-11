package com.test.classes;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pom.classes.Homepagepomclass;
import com.pom.classes.Loginpagepomclass;

import Testbase.Baseclass;
import utility.Dataprovider;

public class TC_LoginDDT extends Baseclass {
	
	@Test(dataProvider="LoginData", dataProviderClass =Dataprovider.class, groups="Datadriven")
	public void LoginDDT(String username, String pass, String exp) throws InterruptedException
	
	{
		
		System.out.println("***DDT Login Test case start again***");
		try
		{
			
		
		Loginpagepomclass login =new Loginpagepomclass(driver);
		login.clickusername(username);
		login.clickpassword(pass);
		login.clickloginbtn();
		Thread.sleep(3000);
		
		Homepagepomclass home =new Homepagepomclass(driver);
		boolean myaccpage=home.isheading();
		
		
		/*Conditions
		Data is valid = Login sucess- Test case pass = Logout
						=Login Failed- Test case fail
						
		Data is Invalid = Login sucess- Test case fail = Logout
						=Login Failed- Test case pass		*/			
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(myaccpage==true)
			{
				home.clicdropdown();
				home.clicLogout();
				
				Thread.sleep(2000);
				Assert.assertTrue(true);
				
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(myaccpage==true)
			{
				home.clicdropdown();
				home.clicLogout();
				Thread.sleep(2000);
				Assert.assertTrue(false);
				
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		System.out.println("*** DDT Login Test case end***");
		
	}

}
