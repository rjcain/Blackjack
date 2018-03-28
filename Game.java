import java.util.Scanner;

public class Game
{
    public boolean aceHand; 
    public boolean dealerAceHand;
    public boolean pChangeTotal;
    public boolean dChangeTotal;
    public static int playerWins;
    public static int dealerWins;
    public static int draws;
    public int bet;
    public int roundResult;
      
    
    public static void main(String args[]) {
        Game game = new Game();
        game.play();
    }
    
    public void play() {
        boolean isPlaying = true;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Welcome to Blackjack. " +
        "Enter your name to begin...\n");
        String pName = scan.next();
        
        Player p1 = new Player(pName);
        Dealer d = new Dealer("Dealer");
        Deck deck = new Deck();
        
        //Shuffle deck
        deck.shuffle();
        
        while(isPlaying == true) {
            p1.totalHands++;
            System.out.println("\n" + p1 + "\n");
            String hitOrStand = "h";
            
            bet = 100000;
            while(bet > p1.moneyAmt) {
                System.out.println("Enter bet: ");
                bet = scan.nextInt();
            }
            
            //Deal cards to player and dealer
            Card pcard1 = deck.deal();
            Card pcard2 = deck.deal();
            Card dcard1 = deck.deal();
            Card dcard2 = deck.deal();
            
            //Add cards to total hand value
            p1.handTotal = 0;
            d.handTotal = 0;
            p1.handTotal(pcard1); 
            p1.handTotal(pcard2);
            d.handTotal(dcard1);
            d.handTotal(dcard2);
            
            //Check dealt cards for aces
            if(pcard1.value == 1 || pcard2.value == 1) aceHand = true;
            if(dcard1.value == 1 || dcard2.value == 1) dealerAceHand = true;
            
            //Play player's hand based on prescence of aces
            if(aceHand) {
                acePlay(pcard1, pcard2, dcard1, p1, deck); 
            }
            else {
                play(pcard1, pcard2, dcard1, p1, deck);
            }
            
            //Play dealer's hand based on prescence of aces
            if(dealerAceHand) {
                dealerAcePlay(dcard1, dcard2, d, deck);
            }
            //Plays dealer hand with aces
            else {
                dealerPlay(dcard1, dcard2, d, deck);
            }
            
            //Display round totals and winner
            
            System.out.println("\nPlayer total: " + p1.handTotal);
            System.out.println("Dealer total: " + d.handTotal);
            pChangeTotal = false;
            dChangeTotal = false;
            
            //Set bust values to -1 so comparisons can be made
            //then determine winner
            if(p1.handTotal > 21) p1.handTotal = -1;
            if(d.handTotal > 21) d.handTotal = -1;
            System.out.println(determineWinner(p1, d));
            
            //Output bet results
            p1.bet(bet, roundResult);
                                                                                                               
            //Output overall results
            System.out.println("Player Wins: " + playerWins + "\nDealer wins: "
            + dealerWins + "\nDraws: " + draws);
            
            //Determine if the player wants to continue playing
            System.out.println("\n\nEnter \"q\" to quit, \"n\" for next round.");
            String q = scan.next();
            if(q.equals("q") || p1.moneyAmt == 0) isPlaying = false;
            else isPlaying = true;
            
        } 
    }
    
    public String determineWinner(Player p1, Dealer d) {
        if(p1.handTotal > d.handTotal) {
            roundResult = 1; 
            playerWins++; 
            return "\nRound win.";
        }
        if(p1.handTotal < d.handTotal) {
            roundResult = -1;
            dealerWins++; 
            return "\nRound loss.";
        }
        if(p1.handTotal == d.handTotal) {
            draws++; 
            roundResult = 0;
            return "\nRound draw.";
        }
        return null;
    }
    
    public void play(Card a, Card b, Card c, Player p1, Deck deck) {
        Scanner scan = new Scanner(System.in);
        String hitOrStand = "h";
        
        //Print cards and total
        System.out.println("Your Cards:\n1. " + a + "\n2. " + b);
        System.out.println("Total: " + p1.handTotal);
        System.out.println("\nDealer's Cards:\n1. " + c + "\n2. ----------\n");
        
        while(hitOrStand.equals("h")) {
            System.out.println("Hit or Stand? (h/s)");
            hitOrStand = scan.next();
            if(hitOrStand.equals("h")) {
                 Card cardHit = p1.hit(deck); //Deal new card as cardHit
                 System.out.println("\nHit: " + cardHit); 
                 System.out.println(p1.handTotal(cardHit)); //Add to total
                 if(cardHit.value == 1) {acePlay(a, b, c, p1, deck); break;}
                 if(p1.handTotal > 21) {
                    System.out.println("Bust.");
                    hitOrStand = "";
                 }
            }
        }
    }
        
    public void acePlay(Card a, Card b, Card c, Player p1, Deck deck) {
        Scanner scan = new Scanner(System.in);
        String hitOrStand = "h";
        
        //Print cards and total
        System.out.println("Your Cards:\n1. " + a + "\n2. " + b);
        System.out.println("Total: "+(p1.handTotal-10) + " or " + p1.handTotal);
        System.out.println("\nDealer's Cards:\n1. " + c + "\n2. ----------\n");
        
        while(hitOrStand.equals("h")) {
            System.out.println("Hit or Stand? (h/s)");
            hitOrStand = scan.next();
            if(hitOrStand.equals("h")) {
                Card cardHit = p1.hit(deck);
                System.out.println("Hit: " + cardHit);
                p1.handTotal(cardHit);
                if(p1.handTotal > 21) {
                    System.out.println("Total: " + (p1.handTotal-10));
                    pChangeTotal = true;
                }
                else {
                    System.out.println("Total: " + (p1.handTotal-10) +
                    " or " + p1.handTotal);
                }
                if(p1.handTotal >= 32) {
                   System.out.println("Bust.");
                   hitOrStand = "";
                }
            }
        }
        
        //If the 11 value busted, change to 1 value
        if(pChangeTotal) p1.handTotal -= 10;
        aceHand = false;
        pChangeTotal = false;
    }
    
    public void dealerPlay(Card a, Card b, Dealer d1, Deck deck) {
        String hitOrStand = "h";
        System.out.println("\nDealer's turn...");
        while(d1.handTotal < 17) {
            Card cardHit = deck.deal(); //Deal new card as cardHit
            System.out.println("\nDealer Hit: " + cardHit); 
            d1.handTotal(cardHit);
            if(cardHit.value == 1) {dealerAcePlay(a, b, d1, deck); break;}
            if(d1.handTotal > 21) {
            System.out.println("Bust.");
            hitOrStand = "";
            }
        }
    }
    
    public void dealerAcePlay(Card a, Card b, Dealer d1, Deck deck) {
        String hitOrStand = "h";
        System.out.println("\nDealer's turn...");
        while(d1.handTotal < 28) {
            Card cardHit = d1.hit(deck);
            System.out.println("Dealer Hit: " + cardHit);
            d1.handTotal(cardHit);
            if(d1.handTotal > 21) {
                System.out.println("Total: " + (d1.handTotal-10));
                dChangeTotal = true;
            }
            else {
                System.out.println("Total: " + (d1.handTotal-10) + 
                " or " + d1.handTotal);
            }
            if(d1.handTotal > 32) {
               System.out.println("Dealer Bust.");
               hitOrStand = "";
            }
        }
        //If the 11 value busted, change to 1 value
        if(pChangeTotal) d1.handTotal -= 10;
        dealerAceHand = false;
        dChangeTotal = false;
    }
    
    public void endGame() {
        
        }
}
