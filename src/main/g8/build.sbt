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
      showCursor          := $show_cursor$,
      title               := "$game_title$",
      gameAssetsDirectory := "$game_assets_directory$",
      windowStartWidth    := $window_start_width$,
      windowStartHeight   := $window_start_height$,
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo-json-circe" % "$indigo_version$",
        "io.indigoengine" %%% "indigo"            % "$indigo_version$",
        "io.indigoengine" %%% "indigo-extras"     % "$indigo_version$"
      )
    )
    .settings(
      code := { "code ." ! }
    )
    .settings(
      logo := "$game_title$ (v" + version.value.toString + ")",
      usefulTasks := Seq(
        UsefulTask("", "runGame", "Run the game (requires Electron)"),
        UsefulTask("", "buildGame", "Build web version"),
        UsefulTask("", "runGameFull", "Run the fully optimised game (requires Electron)"),
        UsefulTask("", "buildGameFull", "Build the fully optimised web version"),
        UsefulTask("", "code", "Launch VSCode")
      ),
      logoColor        := scala.Console.MAGENTA,
      aliasColor       := scala.Console.BLUE,
      commandColor     := scala.Console.CYAN,
      descriptionColor := scala.Console.WHITE
    )

addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")
addCommandAlias("buildGameFull", ";compile;fullOptJS;indigoBuildFull")
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")
addCommandAlias("runGameFull", ";compile;fullOptJS;indigoRunFull")

lazy val code =
  taskKey[Unit]("Launch VSCode in the current directory")
