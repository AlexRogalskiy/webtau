/*
 * Copyright 2020 webtau maintainers
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.testingisdocumenting.webtau.browser;

import org.testingisdocumenting.webtau.browser.documentation.BrowserDocumentation;
import org.testingisdocumenting.webtau.browser.driver.CurrentWebDriver;
import org.testingisdocumenting.webtau.browser.driver.WebDriverCreator;
import org.testingisdocumenting.webtau.browser.navigation.BrowserPageNavigation;
import org.testingisdocumenting.webtau.browser.page.*;
import org.testingisdocumenting.webtau.browser.page.path.PageElementPath;
import org.testingisdocumenting.webtau.cache.Cache;
import org.testingisdocumenting.webtau.utils.UrlUtils;
import org.openqa.selenium.OutputType;

import static org.testingisdocumenting.webtau.cfg.WebTauConfig.getCfg;
import static org.testingisdocumenting.webtau.reporter.IntegrationTestsMessageBuilder.*;
import static org.testingisdocumenting.webtau.reporter.WebTauStep.createAndExecuteStep;
import static org.testingisdocumenting.webtau.reporter.TokenizedMessage.tokenizedMessage;

public class Browser {
    private static final String DEFAULT_URL_CACHE_KEY = "current";

    private final AdditionalBrowserInteractions additionalBrowserInteractions;

    public static final Browser browser = new Browser();
    public final CurrentWebDriver driver = CurrentWebDriver.INSTANCE;

    public final BrowserCookies cookies = new BrowserCookies(driver);
    public final BrowserLocalStorage localStorage = new BrowserLocalStorage(driver);
    public final BrowserNavigation navigation = new BrowserNavigation(driver);
    public final BrowserDocumentation doc = new BrowserDocumentation(driver);

    public final PageUrl url = new PageUrl(driver::getCurrentUrl);

    public final BrowserKeys keys = new BrowserKeys();

    public final PageElementValue<String> title = new PageElementValue<>(BrowserContext.INSTANCE,
            "title", this::extractPageTitle);

    private Browser() {
        additionalBrowserInteractions = new BrowserInjectedJavaScript(driver);
    }

    public void open(String url) {
        String fullUrl = createFullUrl(url);

        String currentUrl = driver.getCurrentUrl();
        boolean sameUrl = fullUrl.equals(currentUrl);

        createAndExecuteStep(tokenizedMessage(action("opening"), urlValue(fullUrl)),
                () -> tokenizedMessage(action(sameUrl ? "staying at" : "opened"), urlValue(fullUrl)),
                () -> {
                    if (!sameUrl) {
                        BrowserPageNavigation.open(driver, url, fullUrl);
                    }
                });
    }

    public void reopen(String url) {
        String fullUrl = createFullUrl(url);

        createAndExecuteStep(tokenizedMessage(action("re-opening"), urlValue(fullUrl)),
                () -> tokenizedMessage(action("opened"), urlValue(fullUrl)),
                () -> BrowserPageNavigation.open(driver, url, fullUrl));
    }

    public void refresh() {
        createAndExecuteStep(tokenizedMessage(action("refreshing current page")),
                () -> tokenizedMessage(action("refreshed current page")),
                () -> BrowserPageNavigation.refresh(driver));
    }

    public void close() {
        createAndExecuteStep(tokenizedMessage(action("closing browser")),
                () -> tokenizedMessage(action("browser is closed")),
                driver::quit);
    }

    public void back() {
        createAndExecuteStep(
                tokenizedMessage(action("browser going"), classifier("back")),
                () -> tokenizedMessage(action("browser went"), classifier("back")),
                () -> driver.navigate().back());
    }

    public void forward() {
        createAndExecuteStep(
                tokenizedMessage(action("browser going"), classifier("forward")),
                () -> tokenizedMessage(action("browser went"), classifier("forward")),
                () -> driver.navigate().forward());
    }

    public void restart() {
        String currentUrl = driver.getCurrentUrl();

        createAndExecuteStep(tokenizedMessage(action("restarting browser")),
                () -> tokenizedMessage(action("browser is restarted")),
                () -> {
                    close();
                    browser.open(currentUrl);
                });
    }

    public void saveCurrentUrl() {
        saveCurrentUrl(DEFAULT_URL_CACHE_KEY);
    }

    public void saveCurrentUrl(String key) {
        createAndExecuteStep(tokenizedMessage(action("saving current url as"), stringValue(key)),
                () -> tokenizedMessage(action("saved current url as"), stringValue(key)),
                () -> Cache.cache.put(makeCacheKey(key), driver.getCurrentUrl()));
    }

    public void openSavedUrl() {
        openSavedUrl(DEFAULT_URL_CACHE_KEY);
    }

    public void openSavedUrl(String key) {
        createAndExecuteStep(tokenizedMessage(action("opening url saved as"), stringValue(key)),
                () -> tokenizedMessage(action("opened url saved as"), stringValue(key)),
                () -> {
                    Object url = Cache.cache.get(makeCacheKey(key));
                    if (url == null) {
                        throw new IllegalStateException("no previously saved url found");
                    }

                    reopen(url.toString());
                });
    }

    public PageElement $(String css) {
        return new GenericPageElement(driver, additionalBrowserInteractions, PageElementPath.css(css), false);
    }

    public boolean hasActiveBrowsers() {
        return WebDriverCreator.hasActiveBrowsers();
    }

    public String takeScreenshotAsBase64() {
        return driver.getScreenshotAs(OutputType.BASE64);
    }

    public String extractPageTitle() {
        return driver.getTitle();
    }

    private String createFullUrl(String url) {
        if (UrlUtils.isFull(url)) {
            return url;
        }

        if (!BrowserConfig.getBrowserUrl().isEmpty()) {
            return UrlUtils.concat(BrowserConfig.getBrowserUrl(), url);
        }

        return UrlUtils.concat(getCfg().getBaseUrl(), url);
    }

    private static String makeCacheKey(String givenKey) {
        return "url_" + givenKey;
    }
}
