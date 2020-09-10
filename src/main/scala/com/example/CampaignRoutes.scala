package com.example

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.example.BidRequestRegistry.CreateBidResponse

import scala.concurrent.Future

//#import-json-formats
//#user-routes-class
class CampaignRoutes(bidRequestRegistry: ActorRef[BidRequestRegistry.Command])(implicit val system: ActorSystem[_]) {

  //#user-routes-class
  //#import-json-formats

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

  def createBidResponse(bidRequest: BidRequest): Future[BidResponse] =
    bidRequestRegistry.ask(CreateBidResponse(bidRequest, _))


  //#all-routes
  //#users-get-post
  //#users-get-delete
  val userRoutes: Route =
  pathPrefix("bid") {
    concat(
      //#users-get-delete
      pathEnd {
        concat(
          post {
            entity(as[BidRequest]) { bidRequest =>
              onSuccess(createBidResponse(bidRequest)) { performed =>
                complete((StatusCodes.OK, performed))
              }
            }
          })
      },
    )
    //#users-get-delete
  }
  //#all-routes
}
