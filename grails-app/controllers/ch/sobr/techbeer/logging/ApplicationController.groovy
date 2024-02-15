package ch.sobr.techbeer.logging

import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware
import org.slf4j.MDC

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        response.setHeader("Content-Type", "application/json")
        log.info("processing done")
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }

    def auth() {
        String username = request.JSON.username

        if (username == "admin") {
            MDC.put("username", username)
            response.status = 200
            log.info("User \"${username}\" authenticated successfully")
            return [message: "Hello admin!"]
        }
        else if (username == "wrong") {
            response.status = 403
            log.info("User \"${username}\" failed to authenticate due wrong credentials")
            return [message: "Forbidden"]
        }
        else if (username == "notfound") {
            response.status = 404
            log.warn("Failed login attempt. Reason \"${username}\" not found")
            return [message: "Not Found"]
        }
        else {
            log.error("Unable to authenticate user \"${username}\". Reason: Missing Database connection")
            response.status = 401
            return [message: "Unauthorized"]
        }

    }
}
