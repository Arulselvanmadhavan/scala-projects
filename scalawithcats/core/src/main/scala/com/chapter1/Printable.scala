package com.chapter1

trait Printable[A] {
  def format(value:A):String
}

object PrintableInstances {

  implicit val intPrintable = new Printable[Int] {
    def format(value:Int):String =
      value.toString
  }

  implicit val stringPrintable = new Printable[String] {
    def format(value:String):String =
      value.toString
  }

}

object Printable {

  def format[A](value:A)(implicit p:Printable[A]):String = {
    p.format(value)
  }

  def print[A](value:A)(implicit p:Printable[A]):Unit = {
    println(Printable.format(value))
  }

}
