package ch.makery.blackjack.logic

import scala.util.Random

//class for the deck of cards used in the game
class Deck {
  private var cards: List[Card] = createDeck()

  //create the deck for the game
  private def createDeck(): List[Card] = {
    val suits = List("Hearts", "Diamonds", "Clubs", "Spades")
    val ranks = List("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace")
    for {
      suit <- suits
      rank <- ranks
    } yield {
      val value = rank match {
        case "Ace"   => 11
        case "King" | "Queen" | "Jack" => 10
        case _       => rank.toInt  // Convert the string rank to an integer value
      }
      Card(value, suit, s"${rank}_of_${suit}.png")
    }
  }

  //shuffles the deck
  def shuffle(): Unit = {
    cards = Random.shuffle(cards)
  }

  //deals cards
  def dealOneCard(): Card = {
    if (cards.isEmpty) {
      println("Reshuffling the deck...")
      cards = createDeck()
      shuffle()
    }
    val card = cards.head
    cards = cards.tail
    card
  }
}
