routesGenerator := InjectedRoutesGenerator

val jacksonVersion = "2.8.2"
val logbackVersion = "1.1.7"

libraryDependencies ++= Seq(
  javaWs,
  filters,

  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % jacksonVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  specs2 % Test
)

unmanagedResourceDirectories in Test <+= baseDirectory ( _ /"target/web/public/test" )

