class BlackjackGame {
  val deck = new Deck()
  val player = new HumanPlayer("Player")
  val dealer = new Dealer()

  def startGame(): Unit = {
    deck.shuffle()
    player.hand.addCard(deck.dealCard().get)
    player.hand.addCard(deck.dealCard().get)
    dealer.hand.addCard(deck.dealCard().get)
    dealer.hand.addCard(deck.dealCard().get)

    // Show player's hand
    println(s"Player's hand: ${player.hand}")
    // Show both of the dealer's cards
    println(s"Dealer's hand: ${dealer.hand.getCards.head}, [Hidden Card]")

    player.playTurn(deck)

    // Check if the player has busted
    if (player.hand.value > 21) {
      println("Player busts! Dealer wins.")
    } else {
      dealer.playTurn(deck)
      determineWinner()
    }
  }

  def determineWinner(): Unit = {
    val playerValue = player.hand.value
    val dealerValue = dealer.hand.value

    println(s"Final Player hand value: $playerValue")
    println(s"Final Dealer hand value: $dealerValue")

    if (playerValue > 21) {
      println("Player busts! Dealer wins.")
    } else if (dealerValue > 21) {
      println("Dealer busts! Player wins.")
    } else if (playerValue > dealerValue) {
      println("Player wins!")
    } else if (playerValue < dealerValue) {
      println("Dealer wins!")
    } else {
      println("It's a tie!")
    }
  }
}
