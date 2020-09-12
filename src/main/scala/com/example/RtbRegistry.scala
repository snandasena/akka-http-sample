package com.example

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object RtbRegistry {

  // actor protocol
  sealed trait Command

  final case class CreateBidResponse(bidRequest: BidRequest, replyTo: ActorRef[Option[BidResponse]]) extends Command

  def apply(): Behavior[Command] = registry();

  def registry(): Behavior[Command] =
    Behaviors.receiveMessage {
      case CreateBidResponse(bidRequest, replyTo) =>
        replyTo ! Option(BidResponse("id_from_our_end", bidRequest.id, 0.001, Option("1234"), Option.empty))
        //        replyTo ! Option.empty
        Behaviors.same
    }

}
