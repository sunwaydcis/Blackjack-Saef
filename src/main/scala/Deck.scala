import scala.util.Random

class Deck {
  private val suits = List("Hearts", "Diamonds", "Clubs", "Spades")
  private val ranks = List("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace")
  private var cards: List[Card] = for {
    suit <- suits
    rank <- ranks
  } yield new Card(rank, suit)

  def shuffle(): Unit = {
    cards = Random.shuffle(cards)
  }

  def dealCard(): Option[Card] = {
    cards match {
      case Nil => None
      case head :: tail =>
        cards = tail
        Some(head)
    }
  }
}
