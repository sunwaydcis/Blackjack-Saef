package ch.makery.blackjack.logic

//class to handle all the betting logic for the game
class Gamble(initialCredits: Int) {
  private var credits: Int = initialCredits
  private var currentBet: Int = 0

  //allows the player to place bets minusing from their starting credits and adding to the bet
  def placeBet(amount: Int): Boolean = {
    if (credits >= amount) {
      currentBet += amount
      credits -= amount
      true
    } else {
      false
    }
  }

  //gives the player x2 of whatever they bet if they win
  def winBet(): Unit = {
    credits += currentBet * 2
    resetBet()
  }

  //gives the player their money back if its a draw
  def pushBet(): Unit = {
    credits += currentBet
    resetBet()
  }

  def getCredits: Int = credits

  def resetBet(): Unit = {
    currentBet = 0
  }

  def getCurrentBet: Int = currentBet

  //check if the player no more money meaning game over
  def isGameOver: Boolean = credits <= 0
}
