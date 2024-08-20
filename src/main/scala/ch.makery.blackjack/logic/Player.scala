package ch.makery.blackjack.logic

//class for player
class Player {
  private var cards: List[Card] = List()

  //adds card to player hand
  def receiveCard(card: Card): Unit = {
    cards = cards :+ card
  }

  //counts value of player hand
  def handValue: Int = {
    var sum = 0
    var aceCount = 0

    for (card <- cards) {
      sum += card.value
      if (card.value == 11) aceCount += 1
    }

    //changes value of ace from 11 to 1 if player or dealer hand more than 21
    while (sum > 21 && aceCount > 0) {
      sum -= 10
      aceCount -= 1
    }
    sum
  }

  //checks if player bust
  def isBusted: Boolean = handValue > 21

  //reset player hand
  def resetHand(): Unit = cards = List()

  //return list of card
  def getCards: List[Card] = cards
}
