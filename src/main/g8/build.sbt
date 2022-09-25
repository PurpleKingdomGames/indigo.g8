import scala.sys.process._
import scala.language.postfixOps

import sbtwelcome._

Global / onChangedBuildSource := ReloadOnSourceChanges

Test / scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) }

ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "$scalafix_organize_imports_version$"

lazy val mygame =
  (project in file("."))
    .enablePlugins(ScalaJSPlugin, SbtIndigo)
    .settings( // Normal SBT settings
      name         := "$project_name$",
      version      := "$project_version$",
      scalaVersion := "$scala_version$",
      organization := "$organization$",
      libraryDependencies ++= Seq(
        "org.scalameta" %%% "munit" % "$munit_version$" % Test
      ),
      testFrameworks += new TestFramework("munit.Framework"),
      scalafixOnCompile  := true,
      semanticdbEnabled  := true,
      semanticdbVersion  := scalafixSemanticdb.revision,
    )
    .settings( // Indigo specific settings
      showCursor            := $show_cursor$,
      title                 := "$game_title$",
      gameAssetsDirectory   := "$game_assets_directory$",
      windowStartWidth      := $window_start_width$,
      windowStartHeight     := $window_start_height$,
      disableFrameRateLimit := false,
      electronInstall       := indigoplugin.ElectronInstall.Latest,
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo-json-circe" % "$indigo_version$",
        "io.indigoengine" %%% "indigo"            % "$indigo_version$",
        "io.indigoengine" %%% "indigo-extras"     % "$indigo_version$"
      )
    )
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
        UsefulTask("a", "runGame", "Run the game"),
        UsefulTask("b", "buildGame", "Build web version"),
        UsefulTask("c", "runGameFull", "Run the fully optimised game"),
        UsefulTask("d", "buildGameFull", "Build the fully optimised web version"),
        UsefulTask("e", "code", "Launch VSCode")
      ),
      logoColor        := scala.Console.MAGENTA,
      aliasColor       := scala.Console.YELLOW,
      commandColor     := scala.Console.CYAN,
      descriptionColor := scala.Console.WHITE
    )

addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")
addCommandAlias("buildGameFull", ";compile;fullOptJS;indigoBuildFull")
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")
addCommandAlias("runGameFull", ";compile;fullOptJS;indigoRunFull")

lazy val code =
  taskKey[Unit]("Launch VSCode in the current directory")
