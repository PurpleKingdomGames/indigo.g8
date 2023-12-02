import scala.sys.process._
import scala.language.postfixOps

import sbtwelcome._
import indigoplugin._

Global / onChangedBuildSource := ReloadOnSourceChanges

Test / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }

lazy val gameOptions: IndigoOptions =
  IndigoOptions.defaults
    .withTitle("$game_title$")
    .withWindowSize($window_start_width$, $window_start_height$)
    .withBackgroundColor("black")
    .withAssetDirectory("assets")
    .excludeAssets {
      case p if p.endsWith(os.RelPath.rel / ".gitkeep") => true
      case _                                            => false
    }

lazy val $project_name$ =
  (project in file("$project_name$"))
    .enablePlugins(ScalaJSPlugin, SbtIndigo)
    .settings( // Normal SBT settings
      name         := "$project_name$",
      version      := "$project_version$",
      scalaVersion := "3.3.1",
      organization := "$organization$",
      libraryDependencies ++= Seq(
        "org.scalameta" %%% "munit" % "0.7.29" % Test
      ),
      testFrameworks += new TestFramework("munit.Framework"),
      scalafixOnCompile  := true,
      semanticdbEnabled  := true,
      semanticdbVersion  := scalafixSemanticdb.revision,
    )
    .settings( // Indigo specific settings
      indigoOptions := gameOptions,
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo-json-circe" % "0.15.2",
        "io.indigoengine" %%% "indigo"            % "0.15.2",
        "io.indigoengine" %%% "indigo-extras"     % "0.15.2"
      ),
      Compile / sourceGenerators += Def.task {
        IndigoGenerators("$organization$.generated")
          .generateConfig("Config", gameOptions)
          .toSourceFiles((Compile / sourceManaged).value)
      }
    )

lazy val indigo =
  (project in file("."))
    .settings(
      code := {
        val command = Seq("code", ".")
        val run = sys.props("os.name").toLowerCase match {
          case x if x contains "windows" => Seq("cmd", "/C") ++ command
          case _                         => command
        }
        run.!
      }
    )
    .settings(
      logo := "$game_title$ (v" + version.value.toString + ")",
      usefulTasks := Seq(
        UsefulTask("runGame", "Run the game").noAlias,
        UsefulTask("buildGame", "Build web version").noAlias,
        UsefulTask("runGameFull", "Run the fully optimised game").noAlias,
        UsefulTask("buildGameFull", "Build the fully optimised web version").noAlias,
        UsefulTask("code", "Launch VSCode").noAlias
      ),
      logoColor        := scala.Console.MAGENTA,
      aliasColor       := scala.Console.YELLOW,
      commandColor     := scala.Console.CYAN,
      descriptionColor := scala.Console.WHITE
    )
    .aggregate($project_name$)

addCommandAlias(
  "buildGame",
  List(
    "$project_name$/compile",
    "$project_name$/fastLinkJS",
    "$project_name$/indigoBuild"
  ).mkString(";", ";", "")
)
addCommandAlias(
  "buildGameFull",
  List(
    "$project_name$/compile",
    "$project_name$/fullLinkJS",
    "$project_name$/indigoBuildFull"
  ).mkString(";", ";", "")
)
addCommandAlias(
  "runGame",
  List(
    "$project_name$/compile",
    "$project_name$/fastLinkJS",
    "$project_name$/indigoRun"
  ).mkString(";", ";", "")
)
addCommandAlias(
  "runGameFull",
  List(
    "$project_name$/compile",
    "$project_name$/fullLinkJS",
    "$project_name$/indigoRunFull"
  ).mkString(";", ";", "")
)

lazy val code =
  taskKey[Unit]("Launch VSCode in the current directory")
