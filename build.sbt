lazy val root = project
  .in(file("."))
  .settings(
    name := "proc-lang",
    version := "0.1.0",

    scalaVersion := "3.0.0",

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-parse" % "0.3.4",
      "com.disneystreaming" %% "weaver-cats" % "0.7.3" % Test
    ),

    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )
