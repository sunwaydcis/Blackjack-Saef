package ch.makery.blackjack.logic

import scala.util.Random

class Deck {

  private val suits = List("clubs", "diamonds", "hearts", "spades")
  private val faceValues = List("jack", "queen", "king")
  private var deck: List[Card] = suits.flatMap { suit =>
    (2 to 10).map { value =>
      Card(value, suit, s"${value}_of_$suit.png")
    } ++ faceValues.map { face =>
      Card(10, suit, s"${face}_of_$suit.png")
    } ++ List(Card(11, suit, s"ace_of_$suit.png"))
  }

  def shuffle(): Unit = {
    deck = Random.shuffle(deck)
  }

  def dealOneCard(): Card = {
    val card = deck.head
    deck = deck.tail
    card
  }

  def dealMultipleCards(number: Int): List[Card] = {
    val dealtCards = deck.take(number)
    deck = deck.drop(number)
    dealtCards
  }
}
