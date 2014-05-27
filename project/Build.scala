import sbt._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "cmn-content"
  val appVersion = "0.1-SNAPSHOT"

val appDependencies = Seq(
    javaCore,
    "net.vz.mongodb.jackson" %% "play-mongo-jackson-mapper" % "1.1.0"
)


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
  )

}