package $organization$

import indigo._
import indigo.scenes._
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame")
object $module_name$ extends IndigoGame[Unit, Unit, Unit, Unit] {

  def initialScene(bootData: Unit): Option[SceneName] =
    None

  def scenes(bootData: Unit): NonEmptyList[Scene[Unit, Unit, Unit]] =
    NonEmptyList(EmptyScene)

  val eventFilters: EventFilters =
    EventFilters.Permissive

  def boot(flags: Map[String, String]): Outcome[BootResult[Unit]] =
    Outcome(
      BootResult.noData(
        GameConfig.default
          .withViewport($window_start_width$, $window_start_height$)
      )
    )

  def initialModel(startupData: Unit): Outcome[Unit] =
    Outcome(())

  def initialViewModel(startupData: Unit, model: Unit): Outcome[Unit] =
    Outcome(())

  def setup(bootData: Unit, assetCollection: AssetCollection, dice: Dice): Outcome[Startup[Unit]] =
    Outcome(Startup.Success(()))

  def updateModel(context: FrameContext[Unit], model: Unit): GlobalEvent => Outcome[Unit] =
    _ => Outcome(model)

  def updateViewModel(context: FrameContext[Unit], model: Unit, viewModel: Unit): GlobalEvent => Outcome[Unit] =
    _ => Outcome(viewModel)

  def present(context: FrameContext[Unit], model: Unit, viewModel: Unit): Outcome[SceneUpdateFragment] =
    Outcome(SceneUpdateFragment.empty)

}

object EmptyScene extends Scene[Unit, Unit, Unit] {

  type SceneModel     = Unit
  type SceneViewModel = Unit

  val name: SceneName =
    SceneName("empty")

  val modelLens: Lens[Unit, Unit] =
    Lens.keepLatest

  val viewModelLens: Lens[Unit, Unit] =
    Lens.keepLatest

  val eventFilters: EventFilters =
    EventFilters.Permissive

  val subSystems: Set[SubSystem] =
    Set()

  def updateModel(context: FrameContext[Unit], model: Unit): GlobalEvent => Outcome[Unit] =
    _ => Outcome(model)

  def updateViewModel(
      context: FrameContext[Unit],
      model: Unit,
      viewModel: Unit
  ): GlobalEvent => Outcome[Unit] =
    _ => Outcome(viewModel)

  def present(context: FrameContext[Unit], model: Unit, viewModel: Unit): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment(
        Shape
          .Box(
            Rectangle(0, 0, 60, 60),
            Fill.LinearGradient(Point(0), RGBA.Magenta, Point(45), RGBA.Cyan)
          )
          .withRef(30, 30)
          .moveTo(100, 100)
          .rotateTo(Radians.fromSeconds(context.running * 0.25))
      )
    )

}
