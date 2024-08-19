package ch.makery.blackjack.logic

class Dealer extends Player {
  def shouldDrawCard: Boolean = handValue < 17
}
