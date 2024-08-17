package ch.makery.blackjack.controller

import scalafx.Includes._
import scalafx.scene.control.Button
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafx.stage.Stage // Use ScalaFX Stage
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml

@sfxml
class MainMenuController(val StartGameButton: Button, val StartGameButton1: Button) {

  StartGameButton.onAction = handle {
    println("Hello")
  }

  StartGameButton1.onAction = handle {
    val resource = getClass.getResource("/interface/HowToPlay.fxml")
    if (resource == null) {
      throw new RuntimeException("Could not load FXML file for How To Play")
    }

    val root = FXMLView(resource, NoDependencyResolver)
    val scalaRoot = new AnchorPane(root.asInstanceOf[javafx.scene.layout.AnchorPane])

    val stage = StartGameButton1.getScene.getWindow.asInstanceOf[javafx.stage.Stage]
    val scalaStage = new Stage(stage) // Wrap JavaFX Stage in ScalaFX Stage
    scalaStage.scene = new Scene(scalaRoot, 800, 550)
  }
}
