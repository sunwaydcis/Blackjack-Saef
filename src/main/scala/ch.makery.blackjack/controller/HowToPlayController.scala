package ch.makery.blackjack.controller

import scalafx.Includes._
import scalafx.scene.control.Button
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafx.stage.Stage
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml

@sfxml
class HowToPlayController(val mainMenuButton: Button) {

  mainMenuButton.onAction = handle {
    val resource = getClass.getResource("/interface/MainMenu.fxml")
    if (resource == null) {
      throw new RuntimeException("Could not load FXML file for Main Menu")
    }

    val root = FXMLView(resource, NoDependencyResolver)

    val scalaRoot = new AnchorPane(root.asInstanceOf[javafx.scene.layout.AnchorPane])

    val stage = mainMenuButton.getScene.getWindow.asInstanceOf[javafx.stage.Stage]
    val scalaStage = new Stage(stage)
    scalaStage.scene = new Scene(scalaRoot, 800, 550)
  }
}

