abstract class Player(val name: String) {
  val hand: Hand = new Hand()

  def playTurn(deck: Deck): Unit
}

class HumanPlayer(name: String) extends Player(name) {
  override def playTurn(deck: Deck): Unit = {
  }
}

class Dealer extends Player("Dealer") {
  override def playTurn(deck: Deck): Unit = {
  }
}
