package clrs.chapter6.exercises

import org.scalatest.{FunSuite, Matchers}

class BinaryHeapTests extends FunSuite with Matchers {
  test("should build a heap from an array of any number of elements") {
    BinaryHeap(4, 5, 2, 3, 1, 6, 8) should be(
      Node(4,
           Node(5, Node(3, Empty, Empty), Node(1, Empty, Empty)),
           Node(2, Node(6, Empty, Empty), Node(8, Empty, Empty))))
    BinaryHeap(4, 5, 2, 3, 1, 6) should be(
      Node(4,
           Node(5, Node(3, Empty, Empty), Node(1, Empty, Empty)),
           Node(2, Node(6, Empty, Empty), Empty)))
  }
  test("should correctly return the height of the tree") {
    val t3 = BinaryHeap(4, 2, 3)
    BinaryHeap.height(t3) should be(2)
    val t4 = BinaryHeap(4)
    BinaryHeap.height(t4) should be(1)
    val t5 = BinaryHeap()
    BinaryHeap.height(t5) should be(0)
  }
  test("should correctly calculate the height of the heap from the array length") {
    val a1 = Array(4, 5, 6, 7, 8, 1, 2, 3)
    BinaryHeap.heightFromArray(a1) should be(3)
    val a2 = new Array[Int](10)
    BinaryHeap.heightFromArray(a2) should be(3)
  }
  test("should work when heapify is called") {
    val a3 = Array(4, 5, 6, 7, 8, 1, 2, 3)
    val hp = BinaryHeap.heapify(a3)
    hp.foreach(println)
  }
}
