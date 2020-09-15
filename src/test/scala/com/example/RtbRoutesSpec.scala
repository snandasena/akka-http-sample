package com.example


import java.util.UUID

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


class RtbRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {


  // the Akka HTTP route testkit does not yet support a typed actor system (https://github.com/akka/akka-http/issues/2036)
  // so we have to adapt for now
  lazy val testKit = ActorTestKit()

  implicit def typedSystem = testKit.system

  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.toClassic

  // Here we need to implement all the abstract members of UserRoutes.
  // We use the real UserRegistryActor to test it while we hit the Routes,
  // but we could "mock" it by implementing it in-place or by using a TestProbe
  // created with testKit.createTestProbe()
  val rtbRegistry = testKit.spawn(RtbRegistry())
  lazy val routes = new RtbRoutes(rtbRegistry).bidRoutes

  // use the json formats to marshal and unmarshall objects in the test

  import JsonFormats._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  "BidRoutes" should {
    "be able to bid without impressions (POST /authorized_buyers) and invalid site id" in {
      val bidRequest = BidRequest(UUID.randomUUID().toString,
        Option(List(
          Impression(UUID.randomUUID().toString, Option.empty, Option.empty, Option(1000), Option.empty, Option.empty,
            Option(200), Option(1.0), UUID.randomUUID().toString))),
        Site(10, "test.com"),
        Option.empty, Option.empty)

      val bidEntity = Marshal(bidRequest).to[MessageEntity].futureValue // futureValue is from ScalaFutures

      // using the RequestBuilding DSL:
      val request = Post("/authorized_buyers").withEntity(bidEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.NoContent)

        // we expect the response to be json:
        contentType should ===(ContentTypes.NoContentType)

        // and we know what message we're expecting back:
        entityAs[String] should ===("")
      }
    }

    "be able to bid without impressions (POST /authorized_buyers) and valid site id" in {
      val bidRequest = BidRequest(UUID.randomUUID().toString, Option.empty, Site(1, "test.com"),
        Option.empty, Option.empty)
      val bidEntity = Marshal(bidRequest).to[MessageEntity].futureValue // futureValue is from ScalaFutures

      // using the RequestBuilding DSL:
      val request = Post("/authorized_buyers").withEntity(bidEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        // we expect the response to be json:
        contentType should ===(ContentTypes.`application/json`)
        entityAs[String] should !==("")
      }
    }

  }

}


