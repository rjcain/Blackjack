import java.util.Random;
public class Deck
{
    Random rand = new Random();
    public Card[] defaultDeck = new Card[52];
    public Card[] shuffledDeck = new Card[52];
    public int dCard = -1;
    
    public Deck() {
        for(int n = 0; n < 52; n++) defaultDeck[n] = new Card(0,"","");
        for(int s = 0; s < 4; s++) {
            for(int i = 0; i < 13; i++) {
                if(i < 9) defaultDeck[s*13+i].value = i+1;
                else defaultDeck[s*13+i].value = 10;
                
                if(s == 0) defaultDeck[s*13+i].suit = "Clubs";
                if(s == 1) defaultDeck[s*13+i].suit = "Diamonds";
                if(s == 2) defaultDeck[s*13+i].suit = "Hearts";
                if(s == 3) defaultDeck[s*13+i].suit = "Spades";
                
                if(i == 0) defaultDeck[s*13+i].rank = "Ace";
                if(i == 1) defaultDeck[s*13+i].rank = "Two";
                if(i == 2) defaultDeck[s*13+i].rank = "Three";
                if(i == 3) defaultDeck[s*13+i].rank = "Four";
                if(i == 4) defaultDeck[s*13+i].rank = "Five";
                if(i == 5) defaultDeck[s*13+i].rank = "Six";
                if(i == 6) defaultDeck[s*13+i].rank = "Seven";
                if(i == 7) defaultDeck[s*13+i].rank = "Eight";
                if(i == 8) defaultDeck[s*13+i].rank = "Nine";
                if(i == 9)defaultDeck[s*13+i].rank = "Ten";
                if(i == 10)defaultDeck[s*13+i].rank = "Jack";
                if(i == 11)defaultDeck[s*13+i].rank = "Queen";
                if(i == 12)defaultDeck[s*13+i].rank = "King";
            }
        }
    }
    
    public void shuffle() {
        for(int n = 0; n < 52; n++) shuffledDeck[n] = new Card(0,"","");
        for(int s = 0; s < 4; s++) {
            for(int i = 0; i < 13; i++) {
                boolean isUniqueCard = false;
                while(isUniqueCard == false) {
                    shuffledDeck[s*13+i] = defaultDeck[rand.nextInt(52)];
                    isUniqueCard = true;
                    for(int c = (s*13+i)-1; c > -1; c--) {
                        if(shuffledDeck[s*13+i].equals(shuffledDeck[c])) {
                            isUniqueCard  = false;
                        }
                    }
                }
            }
        }
    }
    
    public Card deal() {
        if(dCard == 51) {
            shuffle(); 
            dCard = -1;
        }
        dCard++;
        return shuffledDeck[dCard];
    }
    
}
