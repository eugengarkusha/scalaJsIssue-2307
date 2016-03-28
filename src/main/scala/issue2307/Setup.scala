package issue2307

import shapeless._, record._, syntax.singleton._

trait Crud[L <: HList,K, R] extends Serializable {
  type Out <: HList
  type V
  def apply(t: L, u: V=>R): (Out,V)
}

class UnsafeCrud(i: Int) extends Crud[HList, Any,Any] {
  type V = Any
  type Out = HList

  def unsafeUpdate(l: HList, i: Int, f: Any => Any): (HList,Any) = {
    def loop(l: HList, i: Int, prefix: List[Any]): (List[Any], HList, Any) =
      l match {
        case hd :: (tl: HList) => if (i == 0) (prefix, f(hd) :: tl, hd) else loop(tl, i - 1, hd :: prefix)
        case _ => throw new Exception("...")
      }

    val (prefix, suffix, v) = loop(l, i, Nil)
    prefix.foldLeft(suffix) { (tl, hd) => hd :: tl } -> v
  }

  def apply(l: HList, f: V => Any): (HList, Any) = unsafeUpdate(l, i, f)
}

object  Setup {

  def main = {
    type R = Record.`'i -> Int, 's -> String, 'c -> Char, 'j -> Int`.T
    val r = 'i ->> 10 :: 's ->> "foo" :: 'c ->> 'x' :: 'j ->> 42 :: HNil
    println(new UnsafeCrud(0).asInstanceOf[Crud[R,Witness.`'i`.T, String]].apply(r, _ => "newStr"))
  }

}
