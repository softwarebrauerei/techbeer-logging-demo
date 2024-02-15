package ch.sobr.techbeer.logging

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class RequestIdInterceptorSpec extends Specification implements InterceptorUnitTest<RequestIdInterceptor> {

    void "test interceptor matching"() {
        when:
        withRequest(controller: "requestidinterceptor")

        then:
        interceptor.doesMatch()
    }
}
