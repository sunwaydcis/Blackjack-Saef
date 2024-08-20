package ch.makery.blackjack.logic

//handles dealer logic extending from player making sure dealer draws a card if their hand value less than 17
class Dealer extends Player {
  def shouldDrawCard: Boolean = handValue < 17
}
