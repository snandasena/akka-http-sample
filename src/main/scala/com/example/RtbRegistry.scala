package com.example

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.example.Algorithm._

object RtbRegistry {

  // actor protocol
  sealed trait Command

  final case class CreateBidResponse(bidRequest: BidRequest, replyTo: ActorRef[Option[BidResponse]]) extends Command

  def apply(): Behavior[Command] = registry();

  def registry(): Behavior[Command] =
    Behaviors.receiveMessage {
      case CreateBidResponse(bidRequest, replyTo) =>
        replyTo ! bid(bidRequest)
        //        replyTo ! Option.empty
        Behaviors.same
    }

}
