import java.util.Vector;

public class Hand
{
   private Vector<Card> hand;
   public Hand()
   {
      hand = new Vector();
   }
   
   public void addCard(Card c)
   {
      hand.add(c);
   }
   
   public Card removeCard(int pos)
   {
      return hand.remove(pos);
   }
   
   public int size()
   {
      return hand.size();
   }
   
   public String toString()
   {
      String output = "Hand: (Size = " + hand.size() + ")\n";
      for(int i = 0; i < hand.size(); i++)
      {
         output += "(" + i + "): " + hand.get(i) + "\n";
      }
      return output;
   }
}