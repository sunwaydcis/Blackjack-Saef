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
    println(s"Dealer's hand: ${dealer.hand}")

    player.playTurn(deck)
    dealer.playTurn(deck)

    determineWinner()
  }
  def determineWinner(): Unit = {
    if (player.hand.value > 21) println("Player busts! Dealer wins.")
    else if (dealer.hand.value > 21 || player.hand.value > dealer.hand.value) println("Player wins!")
    else if (player.hand.value == dealer.hand.value) println("It's a tie!")
    else println("Dealer wins!")
  }
}
