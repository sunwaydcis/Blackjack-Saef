import scala.io.StdIn.readLine

abstract class Player(val name: String) {
  val hand: Hand = new Hand()

  def playTurn(deck: Deck): Unit
}

class HumanPlayer(name: String) extends Player(name) {
  override def playTurn(deck: Deck): Unit = {
    var continue = true

    while (continue && hand.value < 21) {
      println(s"Your current hand: ${hand} (Value: ${hand.value})")
      println("Do you want to 'hit' or 'stand'?")

      readLine().toLowerCase match {
        case "hit" =>
          hand.addCard(deck.dealCard().get)
          println(s"You drew a card: ${hand.getCards.head}")
          if (hand.value > 21) {
            println(s"Bust! The value of your hand is ${hand.value}.")
            continue = false
          }

        case "stand" =>
          continue = false
          println(s"You chose to stand. The value of your hand value is ${hand.value}.")

        case _ =>
          println("Invalid choice, please type 'hit' or 'stand'.")
      }
    }
  }
}

class Dealer extends Player("Dealer") {
  override def playTurn(deck: Deck): Unit = {
  }
}
