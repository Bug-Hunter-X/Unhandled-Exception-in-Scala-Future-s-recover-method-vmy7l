```scala
import scala.concurrent.{ExecutionContext, Future}

class MyClass {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  def myMethod(i: Int): Future[Int] = Future {
    if (i == 0) throw new Exception("Zero is not allowed")
    i * 2
  }

  def myOtherMethod(i: Int): Future[Int] = {
    myMethod(i).recoverWith {
      case e: Exception => 
        println("Error in myMethod: " + e.getMessage) // Log the error
        Future.failed(e) // Propagate the exception as a failed future
    }
  }
}

object Main extends App {
  val myClass = new MyClass
  myClass.myOtherMethod(0).foreach(println) //This will print an error
  myClass.myOtherMethod(5).foreach(println) //this will print 10
}
```

