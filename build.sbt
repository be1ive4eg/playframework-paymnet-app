import sbt.Package.ManifestAttributes

scalaVersion := "2.11.8"

val webVersion = "1.2-SNAPSHOT"
val akkaVersion = "2.4.11"
val ebeanormVersion = "8.4.1"
val ebeanormQueryVersion = "8.4.1"
val ebeanormAgentVersion = "8.1.1"

val hikaryVersion = "2.5.1"

lazy val commonSettings = Seq(
	version := webVersion,
	organization := "com.hiddensign",
	packageOptions := Seq(ManifestAttributes(
			("Implementation-Vendor", "Hiddensign Ltd"),
			("Implementation-Title", "Hiddensign Web"),
			("Implementation-Version", webVersion)
		)
	),
	scalaVersion := "2.11.7",
	javacOptions ++= Seq(
		"-source", "1.8", 
		"-target", "1.8", 
		"-encoding", "UTF-8", 
		"-Xlint:-options"
	),
	resolvers ++= Seq(
		"Local Maven Repository" at "file:///" + System.getProperty("user.home") + "/.m2/repository",
	  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
	),
	dependencyOverrides += "org.avaje.ebean" % "ebean" % ebeanormVersion,
	dependencyOverrides += "org.avaje.ebean" % "ebean-querybean" % ebeanormQueryVersion,
	dependencyOverrides += "org.avaje.ebean" % "ebean-agent" % ebeanormAgentVersion,
	dependencyOverrides += "com.zaxxer" % "HikariCP" % hikaryVersion,

  dependencyOverrides += "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  dependencyOverrides +="com.typesafe.akka" %%  "akka-remote" % akkaVersion,
  dependencyOverrides += "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
)

lazy val root = project.in(file("."))
	.settings(commonSettings: _*)
	.settings(
	    name := "root"
	)
 	.dependsOn( corepaymentapi, webpaymentapi)
 	.aggregate( corepaymentapi, webpaymentapi)

// modules are configured in its own build.sbt files
lazy val corepaymentapi = (project in file("hiddensign-core-payment-api"))
	.settings(commonSettings: _*)
	.settings(
		name := "hhiddensign-core-payment-api"
	)


lazy val webpaymentapi = (project in file("hiddensign-web-payment-api"))
	.settings(commonSettings: _*)
	.settings(
		name := "hiddensign-web-payment-api"
	)
	.dependsOn(corepaymentapi)
	.enablePlugins(PlayScala)
	.enablePlugins(PlayJava)
	.disablePlugins(PlayLogback)

