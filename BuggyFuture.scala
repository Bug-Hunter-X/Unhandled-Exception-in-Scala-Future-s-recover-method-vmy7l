```scala
import scala.concurrent.{ExecutionContext, Future}

class MyClass {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  def myMethod(i: Int): Future[Int] = Future {
    if (i == 0) throw new Exception("Zero is not allowed")
    i * 2
  }

  def myOtherMethod(i: Int): Future[Int] = {
    myMethod(i).recover {
      case e: Exception => 
        println("Error in myMethod: " + e.getMessage) //This will print the exception and continue.
        0 //Returning 0 which isn't always desirable behavior. 
    }
  }
}


object Main extends App {
  val myClass = new MyClass
  myClass.myOtherMethod(0).foreach(println)
  myClass.myOtherMethod(5).foreach(println)
}
```