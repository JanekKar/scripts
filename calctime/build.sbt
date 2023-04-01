scalaVersion := "2.13.8"

name := "calctime"
organization := "com.example"
version := "1.0"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-nop" % "2.0.0",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1",
  "com.github.oshi" % "oshi-core" % "6.4.0",
)

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("module-info.class") => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
