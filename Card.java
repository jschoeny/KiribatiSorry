

public class Card
{
   private int suit; //0 - 
   private int num; //1 - 13 (14 is joker)
   
   public Card(int userSuit, int userNum)
   {
      suit = userSuit;
      num = userNum;
   }
   
   public int getSuit()
   {
      return suit;
   }
   
   public int getNum()
   {
      return num;
   }
   
   public String toString()
   {
      String suitName = "";
      String cardNumber = "";
      
      if(num == 14)
      {
         if(suit == 0)
         {
            return "Black Joker";
         }
         else if(suit == 1)
         {
            return "Red Joker";
         }
      }
      else
      {
         switch(num)
         {
            case 1:
               cardNumber = "Ace";
               break;
            case 11:
               cardNumber = "Jack";
               break;
            case 12:
               cardNumber = "Queen";
               break;
            case 13:
               cardNumber = "King";
               break;
            default:
               cardNumber = Integer.toString(num);
               break;
         }
         
         switch(suit)
         {
            case 0:
               suitName = "Clubs";
               break;
            case 1:
               suitName = "Hearts";
               break;
            case 2:
               suitName = "Spades";
               break;
            case 3:
               suitName = "Diamonds";
               break;
         }
         
         return cardNumber + " of " + suitName;
      }
      return "";
   }
}