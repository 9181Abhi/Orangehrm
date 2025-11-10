package utility;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Testbase.Baseclass;



public class ExtentReportManager implements  ITestListener  {

	
	
	public ExtentReports extent;
    public ExtentSparkReporter sparkReporter;
    public ExtentTest test;
    
    String repName;

    // --- 1. Report Initialization (onStart) ---

    public void onStart(ITestContext testContext) {
        
        // 1. Generate timestamp and report name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Specify report location
        
        // 2. Configure Spark Reporter
        sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title
        sparkReporter.config().setReportName("opencart Functional Testing"); // Name of the report
        sparkReporter.config().setTheme(Theme.STANDARD);
        
        // 3. Initialize ExtentReports and attach reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // 4. Set Static System Information (from first screenshots)
        extent.setSystemInfo("Application", "Orangehrm");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");

        // 5. Set Dynamic System Information (from second screenshot)
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environemnt", "QA");

        // Get 'os' parameter from TestNG XML
       
        extent.setSystemInfo("Operating System", "Window");

        // Get 'browser' parameter from TestNG XML
        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        // Get included groups from TestNG XML
        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    // --- 2. Test Success (onTestSuccess) ---

   
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // To display groups in report
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    // --- 3. Test Failure (onTestFailure) ---

    
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
        
        try {
        	   Baseclass base = (Baseclass) result.getInstance();
            // Assumes 'BaseClass' is implemented and contains the captureScreen method
            String imgPath = base.captureScreen(result.getName()); 
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    // --- 4. Test Skipped (onTestSkipped) ---

    
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    // --- 5. Report Finalization (onFinish) ---

  
    public void onFinish(ITestContext testContext) {
        
        // Write/Flush all buffered report data to file
        extent.flush(); 

        // Automatically open report in default browser using java.awt.Desktop
        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
