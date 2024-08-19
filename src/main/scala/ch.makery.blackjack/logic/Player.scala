package ch.makery.blackjack.logic

class Player {
  private var cards: List[Card] = List()

  def receiveCard(card: Card): Unit = {
    cards = cards :+ card
  }

  def handValue: Int = {
    var sum = 0
    var aceCount = 0

    for (card <- cards) {
      sum += card.value
      if (card.value == 11) aceCount += 1
    }

    while (sum > 21 && aceCount > 0) {
      sum -= 10
      aceCount -= 1
    }

    sum
  }

  def isBusted: Boolean = handValue > 21
  def resetHand(): Unit = cards = List()
  def getCards: List[Card] = cards
}
