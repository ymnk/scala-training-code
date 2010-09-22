package tmoriss.list

import junit.framework.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import scalaexamples.EmptyTest

import scala.{List => SList}
import Exercises._

@RunWith(classOf[JUnit4])
class ListTest extends EmptyTest {
  import Implicits._

  val sl = SList((0 to 10): _*)
  val el: List[Int] = sl

  // @Test
  def ex1 {
    val n1 = 3
    val n2 = 4
    val left: Int = add(n1:Natural, n2:Natural)
    val right = n1 + n2
    assertEquals(left, right)
  }

  // @Test
  def ex2 {
    assertEquals(sum(el), sl.sum)
  }

  // @Test
  def ex3 {
    assertEquals(length(el), sl.length)
  }

  // @Test
  def ex4 {
    val left: SList[Int] = map[Int, Int](el, 1 + _ )
    val right = sl.map(_+1)
    assertEquals(left, right)
  }

  // @Test
  def ex5 {
    val left: SList[Int] = filter[Int](el, _%2==0 )
    val right = sl.filter(_%2==0)
    assertEquals(left, right)
  } 

  // @Test
  def ex6 {
    val left: SList[Int] = append[Int](el, el)
    val right = sl ::: sl
    assertEquals(left, right)
  } 

  // @Test
  def ex7 {
    val sll: SList[SList[Int]] = SList(sl, sl)
    val ell: List[List[Int]] = Cons(el, Cons(el, Empty))
    val left: SList[Int] = flatten[Int](ell)
    val right = sll.flatten
    assertEquals(left, right)
  }

  // @Test
  def ex8 {
    val left: SList[Int] = flatMap[Int, Int](el, l => Cons(l, Empty))
    val right = sl.flatMap( _ :: Nil)
    assertEquals(left, right)
  }

  // @Test
  def ex9 {
    val left = maximum(el)
    val right = sl.max
    assertEquals(left, right)
  }

  // @Test
  def ex10 {
    val left: SList[Int] = reverse(el)
    val right = sl.reverse
    assertEquals(left, right)
  }
}

private object Implicits {
  implicit def toSList[A](l: List[A]): SList[A] =  l match {
    case Empty => Nil
    case Cons(h, t) => h :: toSList(t)
  } 
  implicit def toList[A](l: SList[A]): List[A] =  l match {
    case Nil => Empty
    case h :: t => Cons(h, toList(t))
  } 

  implicit def toInt(n: Natural): Int =  n match {
    case Zero => 0
    case Succ(n) => 1 + toInt(n)
  } 
  implicit def toNatural(n: Int): Natural =  n match {
    case 0 => Zero
    case n => Succ(toNatural(n-1))
  } 
}
