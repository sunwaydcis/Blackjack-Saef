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
  }
}
