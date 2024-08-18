package ch.makery.blackjack.controller

import scalafx.Includes._
import scalafx.scene.control.Button
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafx.stage.Stage
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml

@sfxml
class MainMenuController(val StartGameButton: Button, val StartGameButton1: Button) {
  
  StartGameButton.onAction = handle {
    val resource = getClass.getResource("/interface/Game.fxml")
    if (resource == null) {
      throw new RuntimeException("Could not load FXML file for Game")
    }

    val root = FXMLView(resource, NoDependencyResolver)
    val scalaRoot = new AnchorPane(root.asInstanceOf[javafx.scene.layout.AnchorPane])
    
    val stage = StartGameButton.getScene.getWindow.asInstanceOf[javafx.stage.Stage]
    val scalaStage = new Stage(stage) 
    scalaStage.scene = new Scene(scalaRoot, 800, 550)
  }

  StartGameButton1.onAction = handle {
    val resource = getClass.getResource("/interface/HowToPlay.fxml")
    if (resource == null) {
      throw new RuntimeException("Could not load FXML file for How To Play")
    }

    val root = FXMLView(resource, NoDependencyResolver)
    val scalaRoot = new AnchorPane(root.asInstanceOf[javafx.scene.layout.AnchorPane])

    val stage = StartGameButton1.getScene.getWindow.asInstanceOf[javafx.stage.Stage]
    val scalaStage = new Stage(stage)
    scalaStage.scene = new Scene(scalaRoot, 800, 550)
  }
}
