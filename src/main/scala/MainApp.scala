package ch.makery.blackjack

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafx.scene.layout.AnchorPane

object MainApp extends JFXApp {

  //loads the fxml file
  val resource = getClass.getResource("/interface/MainMenu.fxml")
  if (resource == null) {
    throw new RuntimeException("Could not load FXML file")
  }

  val root = FXMLView(resource, NoDependencyResolver)

  //convert anchorpane from java to scalafx
  val scalaRoot = new AnchorPane(root.asInstanceOf[javafx.scene.layout.AnchorPane])

  stage = new PrimaryStage {
    title = "HIGH ROLLA BLACKJACK GAMBLER"
    scene = new Scene(scalaRoot, 800, 550)
  }
}
