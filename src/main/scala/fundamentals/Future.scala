package fundamentals

import scala.concurrent.ExecutionContext.Implicits.global // needed for Future
import scala.concurrent.Future
import scala.util.{Failure, Success}

case object FutureObj extends App {

  val hilo1 = new Thread(new Runnable{
    override def run(): Unit = {
      println("Hello from thread")
    }
  })

  val hilo2 = new Thread(() => println("Hello from thread 2"))

  hilo1.start()
  hilo2.start()
  // join is needed to wait for the thread to finish
  hilo1.join()

  //volatile es una variable que se puede leer y escribir desde diferentes hilos
  class BankAccount(@volatile private var amount: Int) {
    override def toString: String = "" + amount
    def withdraw(money: Int) = this.amount -= money

    //synchronized es un bloque de código que se ejecuta de forma atómica
    def safeWithdraw(money: Int) = this.synchronized {
      this.amount -= money
    }
  }

  val bankAccount = new BankAccount(2000)
  for (_ <- 1 to 1000) {
    new Thread(() => bankAccount.withdraw(1)).start()
    new Thread(() => bankAccount.safeWithdraw(-1)).start()
  }
  println(bankAccount)

  val sum = Future{
    45
  }

  sum.map(_ + 1).foreach(println)

  sum.onComplete{
    case Success(value) => println(s"Success: $value")
    case Failure(error)=> println(error)
  }
}
