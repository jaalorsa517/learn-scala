package actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorsFundamentals extends  App{

  val actorsSystem = ActorSystem("FirstActorSystem")

  class wordCounterActor extends Actor{
    def receive: PartialFunction[Any, Unit] = {
      case "Hi!" => context.sender() ! "hola, como estas?" // or sender() ! ""
      case message: String => println(message)
      case msg => println("Otro mensaje" + msg.toString)
      case SendMessage(message) =>
        self ! message
      case SendMsgActor(ref) => ref ! "Hi!"
    }
  }
  case class SendMessage(message: String)
  case class SendMsgActor(actor: ActorRef)

  val bob = actorsSystem.actorOf(Props[wordCounterActor], "bobActor")
  val alice = actorsSystem.actorOf(Props[wordCounterActor], "AliceActor")

  bob ! SendMsgActor(alice)

//  val wordCounter = actorsSystem.actorOf(Props[wordCounterActor], "wordCounter")
//  wordCounter ! "hola"



//  wordCounter ! "I am learning Akka and it's pretty damn cool!"
//  wordCounter ! SendMessage("Este mensaje es para ver el self")
//
//
//
//  object Person {
//    def props(name: String): Props = Props(new Person(name))
//  }
//
//  class Person(name: String) extends Actor{
//    override def receive: Receive = {
//      case "hi" => println(s"[${context.self}] Hi, my name is $name")
//      case _ =>
//    }
//  }
//
//  val person = actorsSystem.actorOf(Person.props("Bob"))
//  person ! "hi"
}
