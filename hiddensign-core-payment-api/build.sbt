
val reflectionsVersion = "0.9.10"
val guiceVersion = "4.1.0"
val ebeanormVersion = "8.4.1"
val ebeanormQueryVersion = "8.4.1"
val ebeanormAgentVersion = "8.1.1"
val ebeanAgentLoaderVersion = "2.1.2"
val hikaryVersion = "2.5.1"
val commonsLangVersion = "3.4"
val commonsCodecVersion = "1.10"
val commonsIoVersion = "2.5"
val jodaTimeVersion = "2.9.4"
val h2DatabaseVersion = "1.4.193"

libraryDependencies ++= Seq(
  "org.reflections" % "reflections" % reflectionsVersion,
  "com.zaxxer" % "HikariCP" % hikaryVersion,
  "com.google.inject" % "guice" % guiceVersion,
  "org.avaje.ebean" % "ebean" % ebeanormVersion,
  "org.avaje.ebean" % "ebean-querybean" % ebeanormQueryVersion,
  "org.avaje.ebean" % "ebean-agent" % ebeanormAgentVersion,
  "org.avaje" % "avaje-agentloader" % ebeanAgentLoaderVersion,
  "org.apache.commons" % "commons-lang3" % commonsLangVersion,
  "commons-io" % "commons-io" % commonsIoVersion,
  "commons-codec" % "commons-codec" % commonsCodecVersion,
  "joda-time" % "joda-time" % jodaTimeVersion,
  "com.h2database" % "h2" % h2DatabaseVersion
)
