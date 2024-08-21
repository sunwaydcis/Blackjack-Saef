package ch.makery.blackjack.logic

//handles dealer logic extending from player making sure dealer draws a card if their hand value less than 17
class Dealer extends Player {

  //override receivecard to include specific logic for dealer
  override def receiveCard(card: Card): Unit = {
    println(s"Dealer receives card: $card")
    super.receiveCard(card)
  }

  def shouldDrawCard: Boolean = handValue < 17
}
