package org.oneandone.qxwebdriver.widget;

import java.util.List;

import org.oneandone.qxwebdriver.QxWebDriver;
import org.openqa.selenium.WebElement;

public class Menu extends Widget implements Selectable {
	
	//TODO: Menu with overflow (SlideBar)

	public Menu(WebElement element, QxWebDriver webDriver) {
		super(element, webDriver);
	}
	
	public void selectItem(Integer index) {
		getSelectableItem(index).click();
	}
	
	@Override
	public void selectItem(String label) {
		getSelectableItem(label).click();
	}

	@Override
	public Widget getSelectableItem(Integer index) {
		List<Widget> children = getChildren();
		return children.get(index);
	}

	@Override
	public Widget getSelectableItem(String label) {
		String locator = "[@label=" + label + "]";
		return findWidget(org.oneandone.qxwebdriver.By.qxh(locator));
	}

}