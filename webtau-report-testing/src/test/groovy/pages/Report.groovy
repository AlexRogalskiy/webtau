/*
 * Copyright 2020 webtau maintainers
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

package pages

import static org.testingisdocumenting.webtau.WebTauDsl.*

class Report {
    def fullScreenIcon = $(".fullscreen-icon")
    def collapsedHeader = $(".collapsed-http-header")
    def reportName = $(".webtau-report-name")
    def groupNames = $(".group-of-tests .navigation-entry-group-label").all()
    def testNames = $(".navigation-entry .label")
    def selectedTestLabel = $(".navigation-entry.selected .label")
    def testSummaryMetaKey = $(".test-metadata th").get("METADATA KEY")
    def allWarningsPanel = $(".webtau-all-warnings-panel")
    def warningMessage = $(".webtau-warning-message")
    def warningTestUrl = $(".webtau-warning-test-url")

    def responseData = $(".response .data")
    def jsonParseErrorMessage = $(".data.json.error")

    def step = $(".step")
    def steps = $(".step")
    def stepPersonaId = $(".step .persona-id")

    def tabNames = $(".tab-selection .tab-name")
    def stepsTab = tabNames.get("Steps")
    def httpCallsTab = tabNames.get("HTTP calls")
    def serversTab = tabNames.get("Servers")

    def cellValues = $("td").all()

    def stepsShowChildren = $(".show-children")

    private def httpCalls = $(".test-http-call")
    private def cliCalls = $(".test-cli-call")

    def stdCliOutput = $(".cli-output.standard")
    def errCliOutput = $(".cli-output.error")

    def cliPersonaIds = $(".test-cli-call .persona")

    def screenshot = $(".image img")

    def keyValuesKeys = $(".webtau-key-value-grid-key")

    def tableUrlCells = $(".webtau-url-cell").all()

    def openGroovyStandaloneReport(String reportName) {
        openReportFile(ReportLocation.groovyFeatureTestingFullUrl(reportName))
    }

    def openJunit5Report(String reportName) {
        openReportFile(ReportLocation.javaJunit5FullUrl(reportName))
    }

    def openJunit5ExampleReport() {
        openJunit5Report('com.example.tests.junit5.WeatherJavaTest.html')
    }

    def selectTest(String testName) {
        def navEntry = $('.navigation-entry .label').get(testName)
        navEntry.waitTo visible
        navEntry.click()
    }

    def selectHttpCalls() {
        httpCallsTab.click()
    }

    def selectServers() {
        serversTab.click()
    }

    def selectCliCalls() {
        selectTab('CLI calls')
    }

    def selectScreenshot() {
        selectTab('Screenshot')
    }

    def selectSteps() {
        stepsTab.click()
    }

    def selectConfiguration() {
        selectTab('Configuration')
    }

    def selectEnvVars() {
        selectTab('Environment Variables')
    }

    def tabByName(String tabName) {
        return tabNames.get(tabName)
    }

    def selectTab(String tabName) {
        return tabByName(tabName).click()
    }

    def expandHttpCall(callNumber) {
        httpCalls.waitTo visible
        httpCalls.get(callNumber).find(".collapse-toggle").click()
    }

    def expandCliCall(callNumber) {
        cliCalls.waitTo visible
        cliCalls.get(callNumber).find(".collapse-toggle").click()
    }

    private static def openReportFile(String fileName) {
        browser.open(fileName)
        $(".status-filter-area").waitTo visible
    }
}
