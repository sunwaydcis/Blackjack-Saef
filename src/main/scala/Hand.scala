class Hand {
  private var cards: List[Card] = List()

  def addCard(card: Card): Unit = {
    cards = card :: cards
  }

  def value: Int = {
    val values = cards.map {
      case card if card.rank == "Ace"   => 11
      case card if card.rank == "King" ||
                card.rank == "Queen" ||
                card.rank == "Jack"  => 10
      case card                         => card.rank.toInt
    }

    val total = values.sum
    if (total > 21 && cards.exists(_.rank == "Ace")) total - 10 else total
  }
  
  def getCards: List[Card] = cards
  
  override def toString: String = cards.mkString(", ")
}
