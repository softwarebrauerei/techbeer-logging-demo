package ch.sobr.techbeer.logging


import org.slf4j.MDC

class RequestIdInterceptor {
      RequestIdInterceptor() {
            matchAll()
      }

      boolean before() {
            Map<String, Object> context = new HashMap<String, Object>()
            context.put("requestId", UUID.randomUUID().toString())
            context.put("startTime", new Date())
            context.put("remoteAddr", request.getRemoteAddr())
            MDC.put("requestId", context.get("requestId").toString())
            MDC.put("remoteAddr", context.get("remoteAddr").toString())
            MDC.put("startTime", context.get("startTime").toString())
            request.setAttribute("requestId",context.get("requestId"))
            request.setAttribute("context", context)
            log.info("invoked ${request.getMethod()} ${request.getRequestURI()}")
            return true
      }

      boolean after() {
            Date startTime = request.getAttribute("context").get("startTime")
            Date endTime = new Date()
            long duration = endTime.getTime() - startTime.getTime()
//            if (duration > 5) {
//                  log.warn("Request ${request.getAttribute("requestId")} took more than 5ms to process")
//            }
            log.info("processed and took ${duration}ms")
            response.addHeader("X-Request-Id", request.getAttribute("requestId"))
            return true
      }

      void afterView() {
            Date startTime = request.getAttribute("context").get("startTime")
            Date endTime = new Date()
            long duration = endTime.getTime() - startTime.getTime()
            log.info("rendered and took ${duration}ms")
            MDC.clear()
      }

}