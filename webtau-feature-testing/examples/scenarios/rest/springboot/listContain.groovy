package scenarios.rest.springboot

import static com.twosigma.webtau.WebTauGroovyDsl.*

scenario("list Customers and assert that it contains a specified entry") {
    http.get("/customers") {
        body.should contain([firstName: 'FN1', lastName: 'LN1'])
    }
}