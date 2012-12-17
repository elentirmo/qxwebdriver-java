package org.oneandone.qxwebdriver;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.oneandone.qxwebdriver.resources.javascript.JavaScript;
import org.oneandone.qxwebdriver.widget.ComboBox;
import org.oneandone.qxwebdriver.widget.Widget;
import org.oneandone.qxwebdriver.widget.ScrollArea;
import org.oneandone.qxwebdriver.widget.SelectBox;
import org.oneandone.qxwebdriver.widget.Menu;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QxWebDriver implements WebDriver {

	public QxWebDriver(WebDriver webdriver) {
		driver = webdriver;
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	}
	
	public ExpectedCondition<Boolean> qxAppIsReady() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				Object result = jsExecutor.executeScript(JavaScript.INSTANCE.getValue("isApplicationReady"));
				Boolean isReady = (Boolean) result;
				return isReady;
			}

			@Override
			public String toString() {
				return "qooxdoo application is ready.";
			}
		};
	}
	
	public WebDriver driver;
	private JavascriptExecutor jsExecutor;
	
	public List<String> getWidgetInterfaces(WebElement element) {
		String script = JavaScript.INSTANCE.getValue("getInterfaces");
		return (List<String>) jsExecutor.executeScript(script, element);
	}
	
	public List<String> getWidgetInheritance(WebElement element) {
		String script = JavaScript.INSTANCE.getValue("getInheritance");
		return (List<String>) jsExecutor.executeScript(script, element);
	}
	
	public Widget findWidget(By by) {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		return getWidgetForElement(element);
	}
	
	public Widget getWidgetForElement(WebElement element) {
		//List<String> interfaces = getWidgetInterfaces(element);
		List<String> classes = getWidgetInheritance(element);
		
		Iterator<String> iter = classes.iterator();
		
		while(iter.hasNext()) {
			String className = iter.next();
			if (className.equals("qx.ui.form.SelectBox")) {
				return new SelectBox(element, this);
			}
			
			if (className.equals("qx.ui.form.ComboBox")) {
				return new ComboBox(element, this);
			}
			
			if (className.equals("qx.ui.menu.Menu")) {
				return new Menu(element, this);
			}
			
			if (className.equals("qx.ui.form.List")) {
				return new org.oneandone.qxwebdriver.widget.List(element, this);
			}
			
			if (className.equals("qx.ui.core.scroll.AbstractScrollArea")) {
				return new ScrollArea(element, this);
			}
		}
		
		return new Widget(element, this);
	}

	@Override
	public void close() {
		driver.close();
	}

	@Override
	public WebElement findElement(By arg0) {
		return driver.findElement(arg0);
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return driver.findElements(arg0);
	}

	@Override
	public void get(String arg0) {
		driver.get(arg0);
		new WebDriverWait(driver, 10, 250).until(qxAppIsReady());
	}

	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	@Override
	public String getPageSource() {
		return driver.getPageSource();
	}

	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	@Override
	public Options manage() {
		return driver.manage();
	}

	@Override
	public Navigation navigate() {
		return driver.navigate();
	}

	@Override
	public void quit() {
		driver.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

}