class Card(val rank: String, val suit: String) {
  override def toString: String = s"$rank of $suit"
}
