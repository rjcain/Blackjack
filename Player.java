import java.text.NumberFormat;

public class Player
{
    public String name;
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    public double moneyAmt;
    static int totalHands = 0;
    public int bet;
    public int handTotal;
    
    public Player(String nm) {
        this.name = nm;
        this.moneyAmt = 100;
    }
    
    public void bet(int amt, int result) {
        if(result == 1) moneyAmt += amt;
        if(result == -1) moneyAmt -= amt;
    }
    
    public Card hit(Deck deck) {
        return deck.deal();
    }
    
    public String handTotal(Card c) {
        if(c.value!=1){handTotal += c.value; return "Total: "+ handTotal;}
        else {handTotal += 11; return (handTotal-10) + " or " + handTotal;}
    }
    
    public String toString() {
        return name + "'s Info\nAction: " + formatter.format(moneyAmt) +
        "\nRound: " + totalHands;
    }
}
