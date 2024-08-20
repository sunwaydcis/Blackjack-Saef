package ch.makery.blackjack.controller

import ch.makery.blackjack.logic.{Deck, Player, Dealer, Gamble}
import scalafx.Includes._
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafx.stage.Stage
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.util.Duration
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@sfxml
class GameController(val quitGameButton: Button,
                     val hitButton: Button,
                     val standButton: Button,
                     val bet1Button: Button,
                     val bet10Button: Button,
                     val allInButton: Button,
                     val playerCard1: ImageView,
                     val playerCard2: ImageView,
                     val playerCard3: ImageView,
                     val playerCard4: ImageView,
                     val playerCard5: ImageView,
                     val dealerShownCard2: ImageView,
                     val dealerShownCard3: ImageView,
                     val dealerShownCard4: ImageView,
                     val dealerShownCard5: ImageView,
                     val dealerHiddenCard: ImageView,
                     val dealerHiddenCardExposed: ImageView,
                     val playerWinsLabel: Label,
                     val dealerWinsLabel: Label,
                     val creditLabel: Label,
                     val betLabel: Label) {

  private val player = new Player()
  private val dealer = new Dealer()
  private val gamble = new Gamble(initialCredits = 50)
  private var deck: Deck = _
  private var playerCardViews: List[ImageView] = _
  private var dealerCardViews: List[ImageView] = _
  private var bettingAllowed: Boolean = true //checks if betting allowed

  //starts new game
  def startNewGame(): Unit = {
    deck = new Deck()
    deck.shuffle()

    playerCardViews = List(playerCard1, playerCard2, playerCard3, playerCard4, playerCard5)
    dealerCardViews = List(dealerShownCard2, dealerShownCard3, dealerShownCard4, dealerShownCard5)

    startNewRound()
  }

  //starts new round
  def startNewRound(): Unit = {
    clearTable()
    player.resetHand()
    dealer.resetHand()

    bettingAllowed = true
    gamble.resetBet()  //resets bet at start of new round
    updateBetLabel()   //updates bet label to clear it

    player.receiveCard(deck.dealOneCard())
    player.receiveCard(deck.dealOneCard())

    dealer.receiveCard(deck.dealOneCard())
    dealer.receiveCard(deck.dealOneCard())

    updateUI()
    printHandValues()
    updateCredits()
  }

  //clears the cards
  def clearTable(): Unit = {
    playerCardViews.foreach(_.image = null)
    dealerCardViews.foreach(_.image = null)
    dealerHiddenCardExposed.visible = false
    dealerHiddenCard.visible = true

    //hides the labels
    playerWinsLabel.visible = false
    dealerWinsLabel.visible = false
  }

  //updates ui to show player and dealer hand
  def updateUI(): Unit = {
    playerCardViews.zip(player.getCards).foreach { case (imageView, card) =>
      setImageToImageView(imageView, card.imagePath)
    }
    setImageToImageView(dealerShownCard2, dealer.getCards.head.imagePath)
    setImageToImageView(dealerHiddenCard, "card back red.png")
  }

  //set an image to imageview from the image path
  def setImageToImageView(imageView: ImageView, imagePath: String): Unit = {
    val imageResource = getClass.getResourceAsStream(s"/images/$imagePath")
    if (imageResource == null) {
      throw new RuntimeException(s"Image resource not found: /images/$imagePath")
    } else {
      imageView.image = new Image(imageResource)
    }
  }

  //prints the value of the hands in console
  def printHandValues(): Unit = {
    println(s"Player's hand value: ${player.handValue}")
    println(s"Dealer's shown card value: ${dealer.getCards.head.value}")
  }

  //shows the current credits player has
  def updateCredits(): Unit = {
    creditLabel.text = s"Credits: ${gamble.getCredits}"
    updateBetLabel()
  }

  //shows the current bet
  def updateBetLabel(): Unit = {
    betLabel.text = s"Bet: ${gamble.getCurrentBet}"
  }

  //player to bet 1 credit if clicked
  bet1Button.onAction = handle {
    if (bettingAllowed && gamble.placeBet(1)) {
      updateCredits()
      println(s"Player bet 1 credit.")
    } else {
      println("Betting is not allowed at this time.") //prints in console when betting not allowed
    }
  }

  //player bet 10 credits if clicked
  bet10Button.onAction = handle {
    if (bettingAllowed && gamble.placeBet(10)) {
      updateCredits()
      println(s"Player bet 10 credits.")
    } else {
      println("Betting is not allowed at this time.")
    }
  }

  //player bet all their credits if clicked
  allInButton.onAction = handle {
    if (bettingAllowed && gamble.placeBet(gamble.getCredits)) {
      updateCredits()
      println(s"Player went all in with ${gamble.getCurrentBet} credits.")
    } else {
      println("Betting is not allowed at this time.")
    }
  }

  //player draws card if clicked
  hitButton.onAction = handle {
    if (player.getCards.size < 5) { //button only works if player less than 5 cards
      player.receiveCard(deck.dealOneCard())
      updateUI()
      printHandValues()

      if (player.isBusted) {
        println("Player busts!")
        bettingAllowed = false //stops betting when player busts
        resolveRound(isPlayerBust = true)
      }
    }
  }

  //ends player turn if clicked
  standButton.onAction = handle {
    bettingAllowed = false //stops betting after player stands
    dealerTurn()
  }

  //handles dealers turn
  def dealerTurn(): Unit = {
    setImageToImageView(dealerHiddenCardExposed, dealer.getCards(1).imagePath)
    dealerHiddenCardExposed.visible = true //shows dealer hidden card
    dealerHiddenCard.visible = false //hides the initial red card

    println(s"Dealer's hand value: ${dealer.handValue}")

    val drawActions = dealerCardViews.drop(dealer.getCards.size - 1).zipWithIndex.map { case (cardView, index) =>
      KeyFrame(Duration(700 * (index + 1)), onFinished = _ => { //adds delay for dealer actions
        //draws dealer card if their hadn value less than 17 and less than 5 cards
        if (dealer.shouldDrawCard && dealer.getCards.size < 5) {
          val newCard = deck.dealOneCard()
          dealer.receiveCard(newCard)
          setImageToImageView(cardView, newCard.imagePath) //display new card
          println(s"Dealer's hand value: ${dealer.handValue}") //prints new value to console if dealer draw card
        }
      })
    }

    val dealerDrawsTimeline = new Timeline {
      keyFrames = drawActions ++ Seq(
        KeyFrame(Duration(700 * (dealer.getCards.size + 1)), onFinished = _ => {
          val dealerHandValue = dealer.handValue //calculate value of dealer hand at end of round
          val playerHandValue = player.handValue //value of player hand at end of round
          if (dealerHandValue > 21) {
            println("Dealer busts! Player wins!")
            resolveRound(isPlayerWin = true)
          } else if (dealerHandValue > playerHandValue) {
            println("Dealer wins!")
            resolveRound(isPlayerWin = false)
          } else if (dealerHandValue == playerHandValue) {
            println("Push!")
            resolveRound(isPush = true)
          } else {
            println("Player wins!")
            resolveRound(isPlayerWin = true)
          }
        })
      )
    }
    dealerDrawsTimeline.play()
  }

  def resolveRound(isPlayerWin: Boolean = false, isPlayerBust: Boolean = false, isPush: Boolean = false): Unit = {

    //hides labels after win
    playerWinsLabel.visible = false
    dealerWinsLabel.visible = false

    if (isPlayerWin) {
      gamble.winBet() //doubles the amount player bet and returns it to their credits
      println("Player wins!")
    } else if (isPush) {
      gamble.pushBet() //returns bet to the player
      println("Push!")
    } else if (isPlayerBust) { //player loses money :(
      println("Player busts!")
    } else {
      println("Dealer wins!")
    }
    updateCredits()

    //delay for labels
    Future {
      Thread.sleep(200)
      scalafx.application.Platform.runLater {
        if (isPlayerWin) {
          playerWinsLabel.visible = true
        } else if (!isPush && !isPlayerBust) {
          dealerWinsLabel.visible = true
        }
        //forces layout to update
        playerWinsLabel.layout()
        dealerWinsLabel.layout()
      }
    }

    //delay before start of new round
    Future {
      Thread.sleep(1000)
      scalafx.application.Platform.runLater {
        if (gamble.isGameOver) {
          println("Game over! You have no credits left.")
          endGame()
        } else {
          println("Round over. Starting a new round.")
          startNewRound()
        }
      }
    }
  }

//sends player to game over after they lose
  def endGame(): Unit = {
    val resource = getClass.getResource("/interface/GameOver.fxml")
    if (resource == null) {
      throw new RuntimeException("Could not load FXML file for Game Over")
    }

    val root = FXMLView(resource, NoDependencyResolver)
    val scalaRoot = new AnchorPane(root.asInstanceOf[javafx.scene.layout.AnchorPane])

    val stage = quitGameButton.getScene.getWindow.asInstanceOf[javafx.stage.Stage]
    val scalaStage = new Stage(stage)
    scalaStage.scene = new Scene(scalaRoot, 800, 550)
  }
  quitGameButton.onAction = handle {
    endGame()
  }
  startNewGame()
}
