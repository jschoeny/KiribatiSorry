import java.util.LinkedList;
import java.util.Collections;

public class Deck
{
   private LinkedList<Card> deck;
   private final int DECK_SIZE = 54;
   
   public Deck()
   {
      deck = new LinkedList();
   }
   
   public void populate()
   {
      for(int i = 0; i < DECK_SIZE; i++)
      {
         int suit = i % 4;
         int num = (i / 4) + 1;
         if(num != 6 && num != 9)
         {
            Card temp = new Card(suit, num);
            //System.out.println(temp);
            deck.add(temp);
         }
      }
   }
   
   public void shuffle()
   {
      Collections.shuffle(deck);
   }
   
   public int size()
   {
      return deck.size();
   }
   
   public Card drawCard()
   {
      return deck.remove();
   }
   
   public void returnCard(Card c)
   {
      deck.add(c);
   }
}