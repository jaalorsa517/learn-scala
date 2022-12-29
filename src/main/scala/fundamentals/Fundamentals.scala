package fundamentals

object Fundamentals extends App{
  val myFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }

  println(myFunction(2))

  val lifted = myFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(5000))

  val elseFun = myFunction.orElse[Int, Int] {
    case 60 => 9000
  }
  println(elseFun(5))
  println(elseFun(60))
//  println(elseFun(457))  //Exception

  type ReceiveFunction = PartialFunction[Any, Unit]
  def receive: ReceiveFunction = {
    case 1 => println("hello")
    case _ => println("confused...")
  }
  receive(1)
  receive(2)

  implicit val timeout = 3000
  def setTimeout(f: () => Unit)(implicit timeout: Int) = {
    println("timeout = " + timeout)
    f()
  }
  setTimeout(() => println("timeout"))(5000) // compiler does sweet nothing: Int => Unit
  setTimeout(() => println("timeout")) // compiler does sweet nothing: Int => Unit

  implicit class Person(name: String) {
    def greet = println(s"Hi, my name is $name")
  }
  "Peter".greet
}
