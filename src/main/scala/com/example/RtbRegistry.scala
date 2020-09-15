package com.example

import akka.actor.typed.{ActorRef, Behavior, SupervisorStrategy}
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import com.example.Algorithm.bidOn

import scala.concurrent.duration.DurationInt

object RtbRegistry {

  // actor protocol
  sealed trait Command

  sealed trait Event

  final case class State()

  final case class CreateBidResponse(bidRequest: BidRequest, replyTo: ActorRef[Option[BidResponse]]) extends Command

  final case class SaveStats(id: String, siteId: Int) extends Event

  final case class ActionPerformed(description: String)

  def apply(): Behavior[Command] = EventSourcedBehavior[Command, Event, State](
    persistenceId = PersistenceId.ofUniqueId("abc"),
    emptyState = State(),
    commandHandler = (state, cmd) => onCommand(state, cmd),
    eventHandler = (state, event) => applyEvent(state, event))
    .onPersistFailure(SupervisorStrategy.restartWithBackoff(1.second, 30.seconds, 0.2))

  def onCommand(state: State, cmd: Command): Effect[Event, State] = {
    cmd match {
      case CreateBidResponse(bidRequest, replyTo) =>
        replyTo ! bidOn(bidRequest)
        Effect.persist(SaveStats(bidRequest.id, bidRequest.site.id))
    }
  }

  def applyEvent(state: State, event: Event): State = {
    event match {
      case SaveStats(id, siteId) =>
        println(id)
        State()
    }
  }

  def test() = Thread.sleep(10000)
}
