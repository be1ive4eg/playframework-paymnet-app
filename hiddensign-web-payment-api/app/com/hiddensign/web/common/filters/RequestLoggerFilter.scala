package com.hiddensign.web.common.filters

/**
* @author Nikolay Denisenko
* @version 2016 /11/12
*/
import javax.inject.{Inject, Singleton}

import akka.util.ByteString
import play.api.Logger
import play.api.libs.streams.Accumulator
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class RequestLoggerFilter @Inject() (implicit ec: ExecutionContext) extends EssentialFilter {
  private val accessLogger: Logger = Logger("access")
  private val X_REQUEST_TIME: String = "X-Request-Time"

  def apply(next: EssentialAction) = new EssentialAction {

    def apply(request: RequestHeader) = {

      Logger.info(s"[>>>][${request.id}] just before ${request.method} ${request.uri}")

      val startTime = System.currentTimeMillis

      val accumulator: Accumulator[ByteString, Result] = next(request)

      accumulator.map { result =>
        val endTime = System.currentTimeMillis
        val requestTime = endTime - startTime

        Logger.info(s"[<<<][${request.id}] ${request.method} ${request.uri} " +
          s"took $requestTime ms and returned ${result.header.status}")

        accessLogger.info(s"[<<<][${request.id}] ${request.method} ${request.uri} " +
          s"took $requestTime ms and returned ${result.header.status}")

        result.withHeaders(X_REQUEST_TIME -> requestTime.toString)

      }
    }
  }


}