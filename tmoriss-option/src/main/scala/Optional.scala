// Scala version 2.8.0.final
// http://scala-tools.org/repo-releases/org/scala-tools/testing/scalacheck_2.8.0/1.7/scalacheck_2.8.0-1.7.jar
 
 
/*
 
  PART 1
  ======
  Below are 15 exercises numbered 1 to 15. The task is to emulate the scala.Option API
  without using Some/None subtypes, but instead using a fold (called a
  catamorphism).
 
  A couple of functions are already done (map, get)
  to be used as an example. ScalaCheck tests are given below to
  verify the work. The desired result is to have all tests passing.
 
  The 15th exercise is not available in the existing Scala API so
  instructions are given in the comments.
 
 
  Part 2
  ======
 
  Below are 10 exercises numbered 16 to 25. The task is to implement additional
  methods for the Optional data type. These methods are not provided in the
  scala.Option API so to determine the correct result requires reading the method
  type signature and ensuring that the tests pass.
 
  The 25th exercise is notable in that its signature says nothing about
  scala.Option yet it is usable for Option (see the test for example).
 
 
  Revision History
  ================
 
  23/08/2010
  * Initial revision
 
  ----------------
 
  23/08/2010
  * Fixed prop_getOrElse. Thanks Michael Bayne.
 
  ----------------
 
  26/08/2010
  * Add lazy annotation to orElse method.
 
  ----------------
 
  01/09/2010
  Added Part 2
 
  02/09/2010
  * Fixed mapOptionals test (why wasn't it failing?). Thanks Alec Zorab.
  * Added comments including *** special note ***
 
*/
package tmoriss.option
 
trait Optional[A] {
  // single abstract method
  def fold[X](some: A => X, none: => X): X
 
  import Optional._
 
  // Done for you.
  def map[B](f: A => B): Optional[B] =
    fold(f andThen some, none[B])
 
  // Done for you.
  // WARNING: undefined for None
  def get: A =
    fold(a => a, error("None.get"))
 
  // Exercise 1
  def flatMap[B](f: A => Optional[B]): Optional[B] =
    error("todo")
 
  // Exercise 2
  // Rewrite map but use flatMap, not fold.
  def mapAgain[B](f: A => B): Optional[B] =
    error("todo")
 
  // Exercise 3
  def getOrElse(e: => A): A =
    error("todo")
 
  // Exercise 4
  def filter(p: A => Boolean): Optional[A] =
    error("todo")
 
  // Exercise 5
  def exists(p: A => Boolean): Boolean =
    error("todo")
 
  // Exercise 6
  def forall(p: A => Boolean): Boolean =
    error("todo")
 
  // Exercise 7
  def foreach(f: A => Unit): Unit =
    error("todo")
 
  // Exercise 8
  def isDefined: Boolean =
    error("todo")
 
  // Exercise 9
  def isEmpty: Boolean =
    error("todo")
 
  // Exercise 10
  def orElse(o: => Optional[A]): Optional[A] =
    error("todo")
 
  // Exercise 11
  def toLeft[X](right: => X): Either[A, X] =
    error("todo")
 
  // Exercise 12
  def toRight[X](left: => X): Either[X, A] =
    error("todo")
 
  // Exercise 13
  def toList: List[A] =
    error("todo")
 
  // Exercise 14
  def iterator: Iterator[A] =
    error("todo")
 
  // Exercise 15 The Clincher!
  // Return a none value if either this or the argument is none.
  // Otherwise apply the function to the argument in some.
  // Don't be afraid to use functions you have written.
  // Better style, more points!
  def applic[B](f: Optional[A => B]): Optional[B] =
    error("todo")
 
  // Utility
  def toOption: Option[A] = fold(Some(_), None)
 
  // Utility
  override def toString = 
    fold("some[" + _ + "]", "none")
 
  // Utility
  override def equals(o: Any) =
    o.isInstanceOf[Optional[_]] && {
      val q = o.asInstanceOf[Optional[_]]
      fold(a => q.exists(a == _),
           q.isEmpty)
	   }
}
 
object Optional {
  // Done for you
  def none[A]: Optional[A] = new Optional[A] {
    def fold[X](some: A => X, none: => X) = none
  }
 
  // Done for you
  def some[A](a: A): Optional[A] = new Optional[A] {
    def fold[X](some: A => X, none: => X) = some(a)
  }
 
  // Utility
  def fromOption[A](o: Option[A]): Optional[A] = o match {
    case None    => none
    case Some(a) => some(a)
  }
 
  // *** Special note ***
  // Some of these functions are likely to be familiar List functions,
  // but with one specific distinction: in every covariant value appearing in
  // the type signature, this value is wrapped in Optional.
  // For example, the unwrapped:
  // filter:          (A => Boolean) => List[A] => List[A]
  // and the wrapped:
  // filterOptionals: (A => Optional[Boolean]) => List[A] => Optional[List[A]]
  // 
  // There are other functions of a similar nature below.
 
  // Exercise 16
  // If a none is encountered, then return a none, otherwise,
  // accumulate all the values in Optional.
  def mapOptionals[A, B](f: A => Optional[B], a: List[A]): Optional[List[B]] =
    error("todo")
 
  // Exercise 17
  // If a none is encountered, then return a none, otherwise,
  // accumulate all the values in Optional.
  def sequenceOptionals[A](a: List[Optional[A]]): Optional[List[A]] =
    error("todo")
 
  // Exercise 18
  // Use sequenceOptionals
  def mapOptionalsAgain[A, B](f: A => Optional[B], a: List[A]): Optional[List[B]] =
    error("todo")
 
  // Exercise 19 
  // Use mapOptionals
  def sequenceOptionalsAgain[A](a: List[Optional[A]]): Optional[List[A]] =
    error("todo")
 
  // Exercise 20
  // If a none is encountered, return none, otherwise,
  // flatten/join by one level.
  def joinOptionals[A](a: Optional[Optional[A]]): Optional[A] =
    error("todo")
 
  // Exercise 21
  def filterOptionals[A](p: A => Optional[Boolean], a: List[A]): Optional[List[A]] =
    error("todo")
 
  // Exercise 22
  def fillOptionals[A](n: Int, a: Optional[A]): Optional[List[A]] =
    error("todo")
 
  // Exercise 23
  // Use sequenceOptionals
  def fillOptionalsAgain[A](n: Int, a: Optional[A]): Optional[List[A]] =
    error("todo")
 
  // Exercise 24
  // Methods mentioning Optional in the type signature are prohibited, except applic and map
  def mapOptionalsYetAgain[A, B](f: A => Optional[B], a: List[A]): Optional[List[B]] =
    error("todo")
 
  // Consider: def joinOptional[A](a: Optional[Optional[A]]): Optional[A]
  // This function "flattens" the Optional into a Some value if possible.
  // It is not possible to write this using only applic and map (try it!).
 
  // Bye bye Option-specificity!
  // (setting up for Exercise 25)
  trait Applic[F[_]] {
    def point[A](a: A): F[A]
    def applic[A, B](f: F[A => B], a: F[A]): F[B]
 
    final def map[A, B](f: A => B, a: F[A]): F[B] =
      applic(point(f), a)
  }
 
  object Applic {
    implicit val OptionalApplic: Applic[Optional] = new Applic[Optional] {
      def point[A](a: A): Optional[A] = some(a)
      def applic[A, B](f: Optional[A => B], a: Optional[A]): Optional[B] = a applic f
    }
  }
 
  // Exercise 25
  // The Double-Clincher!
  def mapWhatever[A, B, F[_]](f: A => F[B], a: List[A])(implicit z: Applic[F]): F[List[B]] =
    error("todo")
}
