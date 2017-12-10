package clrs.chapter12.exercises

sealed trait BinarySearchTree[+T]

case class Node[+T](value: T, left: BinarySearchTree[T], right: BinarySearchTree[T])
    extends BinarySearchTree[T]

case object Empty extends BinarySearchTree[Nothing]

object BinarySearchTree {

  type HeightWithNode[T] = (Int, Node[T])

  def find[T: Ordering](t: BinarySearchTree[T])(x: T): Option[Node[T]] =
    findWithHeight(t)(x).map(_._2)

  def findWithHeight[T: Ordering](t: BinarySearchTree[T])(x: T): Option[HeightWithNode[T]] = {
    def loop(t: BinarySearchTree[T])(h: Int): Option[HeightWithNode[T]] = {
      t match {
        case Node(_, Empty, Empty)                                => None
        case tt @ Node(v, l @ Node(lval, _, _), _) if (lval == x) => Some((h, tt))
        case tt @ Node(v, _, r @ Node(rval, _, _)) if (rval == x) => Some((h, tt))
        case Node(v, l, r)                                        => loop(l)(h + 1).orElse(loop(r)(h + 1))
      }
    }
    loop(t)(0)
  }

  def lca[T: Ordering](t: BinarySearchTree[T])(d1: T, d2: T): Option[T] = {
    val result1 = findWithHeight(t)(d1)
    val result2 = findWithHeight(t)(d2)
    (result1, result2) match {
      case (Some((h1, t1)), Some((h2, t2))) =>
        h1.compare(h2) match {
          case 0  => Some(t1.value)
          case -1 => Some(d1)
          case _  => Some(d2)
        }
      case _ => None
    }
  }

  def isValid[T: Ordering](t: BinarySearchTree[T]): Boolean = {
    val ordering = implicitly[Ordering[T]]
    t match {
      case Empty                 => true
      case Node(_, Empty, Empty) => true
      case Node(v, l @ Node(lval, _, _), Empty) =>
        ordering.lteq(lval, v) && isValid(l: BinarySearchTree[T])
      case Node(v, Empty, r @ Node(rval, _, _)) =>
        ordering.gt(rval, v) && isValid(r: BinarySearchTree[T])
      case Node(v, l @ Node(lval, _, _), r @ Node(rval, _, _)) =>
        ordering.lteq(lval, v) && ordering.gt(rval, v) && isValid(l: BinarySearchTree[T]) && isValid(
          r: BinarySearchTree[T])
    }
  }

  def insert[T: Ordering](t: BinarySearchTree[T], value: T): BinarySearchTree[T] = {
    val ordering = implicitly[Ordering[T]]
    t match {
      case Empty => Node(value, Empty, Empty)
      case Node(v, l, r) =>
        ordering.compare(value, v) match {
          case 1 => Node(v, l, insert(r, value))
          case _ => Node(v, insert(l, value), r)
        }
    }
  }

  def apply[T: Ordering](x: T*): BinarySearchTree[T] = {
    x.foldLeft(Empty: BinarySearchTree[T])(insert)
  }
}
