package Testbase;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class Baseclass {
	public   WebDriver driver;
	public Properties p;
	
	
	
	@Parameters({"Os","browser"})
	@BeforeClass(groups= {"Sanity", "Master", "Regression", "Datadriven"})
	public void setup(String os ,String br) throws IOException
	{
		
	
		
		FileReader File=new FileReader("./src//test//resources//Configure.properties");
		p=new Properties();
		p.load(File);
		
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			System.out.println("Remote execution start");
			DesiredCapabilities capbilities = new DesiredCapabilities();
			
			if(os.equalsIgnoreCase("windows"))
			{
				capbilities.setPlatform(Platform.WIN11);
			}

			else if(os.equalsIgnoreCase("linux"))
			{
				capbilities.setPlatform(Platform.LINUX);
			}
			
			else if(os.equalsIgnoreCase("mac"))
			{
				capbilities.setPlatform(Platform.MAC);
			}
			
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			switch(br.toLowerCase())
			{
			case "chrome":capbilities.setBrowserName("chrome");break;
			case "firefox":capbilities.setBrowserName("firefox");break;
			case "edge":capbilities.setBrowserName("MicrosoftEdge");break;
			default:System.out.println("No matching browser");return;
			}
			
			
			try {
	            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capbilities);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("RemoteWebDriver failed to start: " + e.getMessage());
	        }

	            

		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" : driver=new ChromeDriver();break;
			case "edge" : driver=new EdgeDriver();break;
			case "firefox" : driver=new FirefoxDriver();break;
			}
			
			 if (os.equalsIgnoreCase("windows"))
			 {
			        System.out.println("Detected OS: Windows");
			      
			    } 
			    else if (os.equalsIgnoreCase("mac")) 
			    {
			        System.out.println("Detected OS: Mac");
			        
			    } 
			    else
			    {
			    	System.out.println("OS is not supportes");
			    }
			
		
		}
		
		driver.manage().window().maximize();
	       driver.get(p.getProperty("appurl"));
		 
		}
			
	
		 
	
	
	public  String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        FileUtils.copyFile(sourceFile, targetFile);

        return targetFilePath;
    }
	
	
	@AfterClass(groups= {"Sanity", "Master", "Regression", "Datadriven"})
	public void teradown()
	{
		if(driver!=null)
		{
			driver.quit(); 
		}
	}

}
