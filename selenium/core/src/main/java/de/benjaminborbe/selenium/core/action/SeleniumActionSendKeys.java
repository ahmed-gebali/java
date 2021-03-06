package de.benjaminborbe.selenium.core.action;

import de.benjaminborbe.selenium.api.action.SeleniumActionConfigurationSendKeys;
import de.benjaminborbe.selenium.core.util.SeleniumCoreWebDriver;
import de.benjaminborbe.selenium.core.util.SeleniumExecutionProtocolImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SeleniumActionSendKeys implements SeleniumAction<SeleniumActionConfigurationSendKeys> {

	@Override
	public Class<SeleniumActionConfigurationSendKeys> getType() {
		return SeleniumActionConfigurationSendKeys.class;
	}

	@Override
	public boolean execute(
		final SeleniumCoreWebDriver webDriver,
		final SeleniumExecutionProtocolImpl seleniumExecutionProtocol,
		final SeleniumActionConfigurationSendKeys seleniumActionConfiguration
	) {

		final String xpathExpression = seleniumActionConfiguration.getXpath();
		final WebElement element = webDriver.findElement(By.xpath(xpathExpression));
		element.clear();
		element.sendKeys(seleniumActionConfiguration.getKeys());
		seleniumExecutionProtocol.addInfo(seleniumActionConfiguration.getMessage());

		return true;
	}
}
