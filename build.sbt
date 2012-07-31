name := "google code jam 2012"

version := "0.1-SNASPSHOT"

libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.8.2" % "test",
							"com.github.scala-incubator.io" %% "scala-io-core" % "0.4.0",
							"com.github.scala-incubator.io" %% "scala-io-file" % "0.4.0")

resolvers += "Typesafe" at "http://repo.typesafe.com/typesafe/releases"
