package ch.makery.blackjack.logic

//class to create the playing cards with their attributes
class Card(val value: Int, val suit: String, val imagePath: String) {
  override def toString: String = s"Card(value: $value, suit: $suit, image: $imagePath)"
}

object Card {
  def apply(value: Int, suit: String, imagePath: String): Card = new Card(value, suit, imagePath)
}
