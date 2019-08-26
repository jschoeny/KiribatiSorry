import java.util.Scanner;

public class KiribatiSorry
{
   public static Deck deck;
   public static Hand hand;
   public static Board board;
   public static int piecesInHome = 0;
   public static int piecesInStart = 0;
   public static int currentPlayer = 0;
   public static Scanner scan;
   
   public static void playCard(int player, int pos)
   {
      Card c = hand.removeCard(pos);
      useCard(player, c);
      deck.returnCard(c);
      hand.addCard(deck.drawCard());
   }
   
   public static void useCard(int player, Card c)
   {
      int suit = c.getSuit();
      int num = c.getNum();
      int pieceNum = 0;
      int numMoves = 0;
      boolean extraTurn = false;
      boolean leaveBase = false;
      boolean swapPlayers = false;
      boolean divideMoves = false;
      
      if(num == 1)
      {
         if(board.getPiecesInBase(player) > 0)
            leaveBase = true;
         else
            numMoves = 1;
      }
      else if(num == 2)
      {
         if(board.getPiecesInBase(player) > 0)
            leaveBase = true;
         else
            numMoves = 2;
            
         extraTurn = true;
      }
      else if(num == 4)
      {
         numMoves = -4;
      }
      else if(num == 7)
      {
         numMoves = 7;
         if(board.getPiecesOut(player) > 0)
            divideMoves = true;
      }
      else if(num == 10)
      {
         numMoves = 10;
         if(board.getPiecesOut(player) > 0)
         {
            System.out.print("Move back 1 space? (y/n): ");
            String input = scan.next();
            if(input.equals("y"))
               numMoves = -1;
         }
      }
      else if(num == 11)
      {
         numMoves = 11;
         int numOutOthers = 0;
         for(int i = 1; i < 4; i++)
         {
            numOutOthers += board.getPiecesOut((player + i) % 4);
         }
         
         if(numOutOthers > 0 && board.getPiecesOut(player) > 0)
         {
            System.out.print("Swap with other player? (y/n): ");
            String input = scan.next();
            if(input.equals("y"))
               numMoves = 0;
         }
      }
      else if(num == 13)
      {
         if(board.getPiecesInHome(player) > 0)
         {
            System.out.println("Sorry not yet implemented.");
         }
      }
      else if(num == 14)
      {
         if(board.getPiecesInBase(player) > 0)
            leaveBase = true;
         else
            numMoves = 1;
      }
      else
      {
         numMoves = num;
      }
      
      if(leaveBase)
      {
         board.leaveBase(player);
      }
      else
      {
         if(numMoves != 0)
         {
            if(board.getPiecesOut(player) > 1)
            {
               System.out.println(board.printPieceNumbers(player));
               if(divideMoves)
               {
                  boolean gettingInput = true;
                  System.out.print("Choose a piece to move (1, 2, 3, 4): ");
                  while(gettingInput)
                  {
                     int input = scan.nextInt();
                     if(board.pieceIsOut(player, input - 1))
                     {
                        pieceNum = input - 1;
                        gettingInput = false;
                     }
                     else
                     {
                        System.out.print("Piece not available to move. Try again: ");
                     }
                  }
                  
                  gettingInput = true;
                  System.out.print("Choose an amount to move (1-7): ");
                  while(gettingInput)
                  {
                     int input = scan.nextInt();
                     if(input > 0 && input <= 7)
                     {
                        numMoves = input;
                        gettingInput = false;
                     }
                     else
                     {
                        System.out.print("Invalid amount. Try again: ");
                     }
                  }
               }
               else
               {
                  boolean gettingInput = true;
                  System.out.print("Choose a piece to move (1, 2, 3, 4): ");
                  while(gettingInput)
                  {
                     int input = scan.nextInt();
                     if(board.pieceIsOut(player, input - 1))
                     {
                        pieceNum = input - 1;
                        gettingInput = false;
                     }
                     else
                     {
                        System.out.print("Piece not available to move. Try again: ");
                     }
                  }
               }
            }
            board.moveSpaces(player, pieceNum, numMoves);
            
            if(divideMoves)
            {
               System.out.println();
               System.out.println(board.printPieceNumbers(player));
               
               boolean gettingInput = true;
               System.out.print("Choose a piece to move " + (7 - numMoves) + " spaces (1, 2, 3, 4): ");
               while(gettingInput)
               {
                  int input = scan.nextInt();
                  if(board.pieceIsOut(player, input - 1))
                  {
                     pieceNum = input - 1;
                     board.moveSpaces(player, pieceNum, 7 - numMoves);
                     gettingInput = false;
                  }
                  else
                  {
                     System.out.print("Piece not available to move. Try again: ");
                  }
               }
            }
         }
      }
      System.out.println("Played " + c);
   }
   
   public static void main(String[] args)
   {
      boolean gameRunning = true;
      scan = new Scanner(System.in);
      
      deck = new Deck();
      deck.populate();
      deck.shuffle();
      
      hand = new Hand();
      for(int i = 0; i < 5; i++)
      {
         hand.addCard(deck.drawCard());
      }
      
      board = new Board();
      System.out.println("--Kiribati Sorry!--");
      System.out.println();
      
      while(gameRunning)
      {
         System.out.println(board);
         System.out.println("Pieces in base: " + board.getPiecesInBase(currentPlayer));
         System.out.println("Pieces in home: " + board.getPiecesInHome(currentPlayer));
         System.out.println();
         System.out.println(hand);
         System.out.print("Choose a card to play: ");
         int input = scan.nextInt();
         if(input == -1)
         {
            gameRunning = false;
            System.out.println("You quit the game.");
         }
         else
         {
            playCard(currentPlayer, input);
            System.out.println();
            if(board.getPiecesInHome(currentPlayer) == 4)
            {
               gameRunning = false;
               System.out.println("You win!");
            }
         }
      }
      
      
      scan.close();
   }
}