
public class Card
{
    public int value;
    public String suit;
    public String rank;
    
    public Card(int val, String st, String rk) {
         value = val;
         suit = st;
         rank = rk;
    }

    public String getSuit() {
        return suit;
    }
    
    public String getRank() {
        return rank;
    }
    
    public String toString() {
        return this.rank + " of " + this.suit;
    }
    
}
