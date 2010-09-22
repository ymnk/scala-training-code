package tmoriss.option

import junit.framework.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import scalaexamples.EmptyTest

import Optional._

@RunWith(classOf[JUnit4])
class OptionTest extends EmptyTest {

  val optionals:List[Option[Int]] = List(None, Some(1))
  implicit def o2o[A](o: Option[A]) = new {
    def opt = fromOption(o) 
  }
  implicit def _o2o[A](o: Optional[A]): Option[A] = o.toOption
  
  // @Test
  def ex1 {
    for(o <- optionals){
      val f: Int => Option[String] = x => Option(x.toString) 
      val left = o flatMap f
      val right: Option[String] = o.opt flatMap (f(_).opt)
      assertEquals(left, right)
    }
  }

  // @Test
  def ex2 {
    for(o <- optionals){
      val f: Int => String = x => x.toString
      val left = o map f
      val right: Option[String] = o.opt mapAgain f
      assertEquals(left, right)
    }
  }

  // @Test
  def ex3 {
    for(o <- optionals){
      val left = o getOrElse 0
      val right = o.opt getOrElse 0
      assertEquals(left, right)
    }
  }

  // @Test
  def ex4 {
    val fl: List[Int => Boolean] = List( _ => true, _ => false)
    for(o <- optionals; f <- fl){
      val left = o filter f
      val right: Option[Int] = (o.opt filter f)
      assertEquals(left, right)
    }
  }

  // @Test
  def ex5 {
    val fl: List[Int => Boolean] = List( _ => true, _ => false)
    for(o <- optionals; f <- fl){
      val left = o exists f
      val right = o.opt exists f
      assertEquals(left, right)
    }
  }

  // @Test
  def ex6 {
    val fl: List[Int => Boolean] = List( _ => true, _ => false)
    for(o <- optionals; f <- fl){
      val left = o forall f
      val right = o.opt forall f
      assertEquals(left, right)
    }
  }

  // @Test
  def ex7 {
    val f: Int => Unit = _ => ()
    for(o <- optionals){
      val left = o foreach f
      val right = o.opt foreach f
      assertEquals(left, right)
    }
  }

  // @Test
  def ex8 {
    for(o <- optionals){
      val left = o isDefined
      val right = o.opt.isDefined
      assertEquals(left, right)
    }
  }

  // @Test
  def ex9 {
    for(o <- optionals){
      val left = o isEmpty
      val right = o.opt.isEmpty
      assertEquals(left, right)
    }
  }

  // @Test
  def ex10 {
    for(o1 <- optionals; o2 <- optionals){
      val left = o1 orElse o2
      val right: Option[Int] = o1.opt orElse o2.opt
      assertEquals(left, right)
    }
  }

  // @Test
  def ex11 {
    for(o <- optionals){
      val left = o toLeft "right"
      val right = o.opt toLeft "right"
      assertEquals(left, right)
    }
  }

  // @Test
  def ex12 {
    for(o <- optionals){
      val left = o toRight "left"
      val right = o.opt toRight "left"
      assertEquals(left, right)
    }
  }

  // @Test
  def ex13 {
    for(o <- optionals){
      val left = o toList
      val right = o.opt.toList
      assertEquals(left, right)
    }
  }

  // @Test
  def ex14 {
    for(o <- optionals){
      val left = o.iterator.toList
      val right = o.opt.iterator.toList
      assertEquals(left, right)
    }
  }

  // @Test
  def ex15 {
    val f: Int => String = x => x.toString
    val fl: List[Option[Int=>String]] = List(None, Some(f))
 
    for(o <- optionals; f <- fl){
      val left = (o.opt applic f.opt).toOption
      val right = for(f <- f; o <- o) yield f(o)
      assertEquals(left, right)
    }
  }

  // @Test
  def ex16 {
    val a = (0 to 10).toList
    val fl: List[Int => Optional[String]] = List(
       x => some(x.toString),
       _ => none[String],
       x => if(x%2==0) none else some(x.toString),
       x => if(x==10) none else some(x.toString)
    )

    for(f <- fl){
      val left = mapOptionals(f, a)
      val l = a map f
      val right = if(l forall (_.isDefined)) some(l map (_.get)) else none
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex17 {
    val i = (0 to 10).toList
    val al: List[List[Optional[Int]]] = List(
      i map ( _ => none[Int]),
      i map some[Int],
      i map (x => if(x%2==0) none[Int] else some(x)),
      i map ( x => if(x==10) none[Int] else some(x))
    )

    for(a <- al){
      val left = sequenceOptionals(a)
      val right = if(a exists (_.isEmpty)) none else some(a map (_.get))
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex18 {
    val a = (0 to 10).toList
    val fl: List[Int => Optional[String]] = List(
       x => some(x.toString),
       _ => none[String],
       x => if(x%2==0) none else some(x.toString),
       x => if(x==10) none else some(x.toString)
    )

    for(f <- fl){
      val left = mapOptionalsAgain(f, a)
      val right = mapOptionals(f, a)
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex19 {
    val i = (0 to 10).toList
    val al: List[List[Optional[Int]]] = List(
      i map ( _ => none[Int]),
      i map some[Int],
      i map (x => if(x%2==0) none[Int] else some(x)),
      i map ( x => if(x==10) none[Int] else some(x))
    )

    for(a <- al){
      val left = sequenceOptionalsAgain(a)
      val right = sequenceOptionals(a)
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex20 {
    val ol: List[Optional[Optional[Int]]] = 
      List(none[Optional[Int]], some(none[Int]), some(some(0)))

    for(o <- ol){
      val left = joinOptionals(o)
      val right = if(o.isDefined && o.get.isDefined) o.get else none
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex21 {
    val a = (0 to 10).toList
    val fl: List[Int => Optional[Boolean]] = List(
       x => some(true),
       x => some(false),
       _ => none[Boolean],
       x => if(x%2==0) none else some(x<5),
       x => if(x==10) none else some(x<5)
    )

    for(f <- fl){
      val left = filterOptionals(f, a)
      val right = if(a exists (f(_).isEmpty)) none else  some(a filter (f(_).get))
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex22 {
    val nl = (0 to 10).toList
    val al = List(some(0), none[Int])

    for(n <- nl; a <- al){
      val left = fillOptionals(n, a)
      val right = if(n <= 0) some(Nil) else (a map (List.fill(n)(_)))
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex23 {
    val nl = (0 to 10).toList
    val al = List(some(0), none[Int])

    for(n <- nl; a <- al){
      val left = fillOptionalsAgain(n, a)
      val right = fillOptionals(n, a)
      assertEquals(left, right)
    } 
  }


  // @Test
  def ex24 {
    val a = (0 to 10).toList
    val fl: List[Int => Optional[String]] = List(
       x => some(x.toString),
       _ => none[String],
       x => if(x%2==0) none else some(x.toString),
       x => if(x==10) none else some(x.toString)
    )

    for(f <- fl){
      val left = mapOptionalsYetAgain(f, a)
      val right = mapOptionals(f, a)
      assertEquals(left, right)
    } 
  }

  // @Test
  def ex25 {
    val a = (0 to 10).toList
    val fl: List[Int => Optional[String]] = List(
       x => some(x.toString),
       _ => none[String],
       x => if(x%2==0) none else some(x.toString),
       x => if(x==10) none else some(x.toString)
    )

    for(f <- fl){
      val left = mapWhatever(f, a)
      val right = mapOptionals(f, a)
      assertEquals(left, right)
    } 
  }

}
