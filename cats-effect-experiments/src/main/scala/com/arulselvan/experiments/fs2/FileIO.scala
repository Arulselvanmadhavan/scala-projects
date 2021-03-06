package com.arulselvan.experiments.fs2

import cats.effect.{Concurrent, ExitCode, IO, IOApp, Resource}
import cats.implicits._
import fs2.{io, text, Stream}
import java.nio.file.Paths
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService}

object Converter extends IOApp {

  private val blockingExecutionContext: Resource[IO, ExecutionContextExecutorService] =
    Resource.make(IO(ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))))(ec =>
      IO(ec.shutdown()))

  val converter: Stream[IO, Unit] = Stream.resource(blockingExecutionContext).flatMap {
    blockingEC =>
      def fahrenheitToCelsius(f: Double): Double = (f - 32.0) * (5.0 / 9.0)

      io.file
        .readAll[IO](Paths.get("/Users/arul.madhavan/dev/scala-projects/cats-effect-experiments/src/main/scala/com/arulselvan/experiments/fs2/testdata/fahrenheit.txt"), blockingEC, 4096)
        .through(text.utf8Decode)
        .through(text.lines)
        .filter(s => !s.trim.isEmpty && !s.startsWith("//"))
        .map(line => fahrenheitToCelsius(line.toDouble).toString)
        .intersperse("\n")
        .through(text.utf8Encode)
        .through(io.file.writeAll(Paths.get("/Users/arul.madhavan/dev/scala-projects/cats-effect-experiments/src/main/scala/com/arulselvan/experiments/fs2/testdata/celsius.txt"), blockingEC))
  }

  def run(args: List[String]): IO[ExitCode] =
    converter.compile.drain.as(ExitCode.Success)
}
