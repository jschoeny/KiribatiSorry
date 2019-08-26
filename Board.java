public class Board
{
   private static final int HOME_LENGTH = 6;
   private static final int BOARD_LENGTH = 60;
   private static final int BOARD_WIDTH = 16;
   private static final int NUM_PIECES = 4;
   private static char[] board;
   private static int[][] playerPos;
   private static int[] playerHomeLocation;
   private static int[] playerStartLocation;
   private static int[] piecesInBase;
   private static int[] piecesInHome;
   
   public Board()
   {
      //O - Empty space
      //1 - P1 Start
      //2 - P2 Start
      //3 - P3 Start
      //4 - P4 Start
      //> - Ski start
      //- - Ski continue
      //X - Ski stop
      //A - P1 Home Start
      //B - P2 Home Start
      //C - P3 Home Start
      //D - P4 Home Start
      board = new char[] {
         '█', '>', 'A', '-', 'X', 'a', '█', '█', '█', '>', '-', '-', '-', 'X', '█',
         '█', '>', 'B', '-', 'X', 'b', '█', '█', '█', '>', '-', '-', '-', 'X', '█',
         '█', '>', 'C', '-', 'X', 'c', '█', '█', '█', '>', '-', '-', '-', 'X', '█',
         '█', '>', 'D', '-', 'X', 'd', '█', '█', '█', '>', '-', '-', '-', 'X', '█',
         };
         
      playerPos = new int[4][NUM_PIECES];
      for(int i = 0; i < 4; i++)
      {
         for(int j = 0; j < NUM_PIECES; j++)
         {
            playerPos[i][j] = -7;
         }
      }
      
      playerStartLocation = new int[4];
      playerStartLocation[0] = 5;
      playerStartLocation[1] = 20;
      playerStartLocation[2] = 35;
      playerStartLocation[3] = 50;
      
      playerHomeLocation = new int[4];
      playerHomeLocation[0] = 2;
      playerHomeLocation[1] = 17;
      playerHomeLocation[2] = 32;
      playerHomeLocation[3] = 47;
      
      piecesInBase = new int[4];
      for(int i = 0; i < 4; i++)
      {
         piecesInBase[i] = 4;
      }
      
      piecesInHome = new int[4];
      for(int i = 0; i < 4; i++)
      {
         piecesInHome[i] = 0;
      }
   }
   
   public void moveSpaces(int player, int pieceNum, int numSpaces)
   {
      if(piecesInBase[player] + piecesInHome[player] < NUM_PIECES)
      {
         char posAction = ' ';
         if(playerPos[player][pieceNum] >= 0)
            posAction = board[playerPos[player][pieceNum]];
         while(numSpaces != 0)
         {
            if(numSpaces > 0)
            {
               if(playerPos[player][pieceNum] >= 0)
                  playerPos[player][pieceNum]++;
               else if(-numSpaces <= playerPos[player][pieceNum] + HOME_LENGTH)
                  playerPos[player][pieceNum]--;
               else
                  numSpaces = 0;
               
               if(playerPos[player][pieceNum] >= BOARD_LENGTH)
                  playerPos[player][pieceNum] = 0;
               
               if(playerPos[player][pieceNum] >= 0)
                  posAction = board[playerPos[player][pieceNum]];
               
               if(numSpaces > 0)
                  numSpaces--;
               
               if(numSpaces <= HOME_LENGTH && playerPos[player][pieceNum] >= 0)
               {
                  char homeChar = 'A';
                  switch(player)
                  {
                     case 0:
                        homeChar = 'A';
                        break;
                     case 1:
                        homeChar = 'B';
                        break;
                     case 2:
                        homeChar = 'C';
                        break;
                     case 3:
                        homeChar = 'D';
                        break;
                  }
                  if(posAction == homeChar)
                  {
                     if(numSpaces <= HOME_LENGTH)
                     {
                        playerPos[player][pieceNum] = -1;
                        numSpaces--;
                     }
                  }
               }
            }
            else if(numSpaces < 0)
            {
               if(playerPos[player][pieceNum] > 0)
                  playerPos[player][pieceNum]--;
               else if(playerPos[player][pieceNum] == 0)
                  playerPos[player][pieceNum] = BOARD_LENGTH - 1;
               else if(playerPos[player][pieceNum] < 0)
               {
                  playerPos[player][pieceNum]++;
                  if(playerPos[player][pieceNum] == 0)
                     playerPos[player][pieceNum] = playerHomeLocation[player];
               }
               
               numSpaces++;
            }
         }
         
         if(playerPos[player][pieceNum] >= 0)
         {
            posAction = board[playerPos[player][pieceNum]];
            if(numSpaces == 0 && posAction == '>')
            {
               while(board[playerPos[player][pieceNum]] != 'X')
                  playerPos[player][pieceNum]++;
            }
         }
         else if(playerPos[player][pieceNum] == -6)
         {
            piecesInHome[player]++;
         }
      }
   }
   
   public void leaveBase(int player)
   {
      if(piecesInBase[player] > 0)
      {
         for(int i = 0; i < 4; i++)
         {
            if(playerPos[player][i] == -7)
            {
               playerPos[player][i] = playerStartLocation[player];
               i = 4;
               piecesInBase[player]--;
            }
         }
      }
   }
   
   public int getPiecesInBase(int player)
   {
      return piecesInBase[player];
   }
   
   public int getPiecesInHome(int player)
   {
      return piecesInHome[player];
   }
   
   public int getPiecesOut(int player)
   {
      return NUM_PIECES - (piecesInBase[player] + piecesInHome[player]);
   }
   
   public boolean pieceIsOut(int player, int pieceNum)
   {
      return (playerPos[player][pieceNum] > -6);
   }
   
   public String printPieceNumbers(int player)
   {
      String output = "";
      String temp = "";
      for(int y = 0; y < BOARD_WIDTH; y++)
      {
         for(int x = 0; x < BOARD_WIDTH; x++)
         {
            if(y == 0)
            {
               temp = "" + board[x];
               if(temp.equals("A"))
                  temp = "-";
               if(temp.equals("1"))
                  temp = "*";
            }
            else if(y > 0 && y < BOARD_WIDTH - 1)
            {
               if(x == 0)
               {
                  temp = "" + board[BOARD_LENGTH - y];
                  if(temp.equals("D"))
                     temp = "|";
                  if(temp.equals("-"))
                     temp = "|";
                  if(temp.equals(">"))
                     temp = "^";
               }
               else if(x == BOARD_WIDTH - 1)
               {
                  temp = "" + board[y + BOARD_WIDTH - 1];
                  if(temp.equals("B"))
                     temp = "|";
                  if(temp.equals("-"))
                     temp = "|";
                  if(temp.equals(">"))
                     temp = "V";
               }
               else
               {
                  if(x == 2 && y >= 1 && y <= 5)
                     temp = "█";
                  else if(x == BOARD_WIDTH - 3 && y <= BOARD_WIDTH - 1 && y >= BOARD_WIDTH - 5)
                     temp = "█";
                  else if(y == BOARD_WIDTH - 3 && x >= 1 && x <= 5)
                     temp = "█";
                  else if(y == 2 && x <= BOARD_WIDTH - 1 && x >= BOARD_WIDTH - 5)
                     temp = "█";
                  else if(x == 2 && y == 6)
                     temp = "A";
                  else if(x == 10 && y == 2)
                     temp = "B";
                  else if(x == 13 && y == 10)
                     temp = "C";
                  else if(x == 6 && y == 13)
                     temp = "D";
                  else
                     temp = " ";
               }
            }
            else if(y == BOARD_WIDTH - 1)
            {
               temp = "" + board[(BOARD_WIDTH - x - 1) + (BOARD_LENGTH / 2)];
               if(temp.equals("C"))
                  temp = "-";
               if(temp.equals(">"))
                  temp = "<";
            }
            output += temp + " ";
         }
         output += "\n";
      }
      
      char[] outputNew = output.toCharArray();
      for(int _player = 0; _player < 4; _player++)
      {
         char p = ' ';
         if(_player == 0)
            p = '%';
         else if(_player == 1)
            p = '{';
         else if(_player == 2)
            p = '&';
         else if(_player == 3)
            p = ']';
            
         for(int _piece = 0; _piece < 4; _piece++)
         {
            if(_player == player)
            {
               switch(_piece)
               {
                  case 0: p = '1'; break;
                  case 1: p = '2'; break;
                  case 2: p = '3'; break;
                  case 3: p = '4'; break;
               }
            }
            
            int pos = playerPos[_player][_piece];
            if(pos >= 0 && pos <= 15)
            {
               outputNew[pos * 2] = p;
            }
            else if(pos >= 16 && pos <= 29)
            {
               outputNew[(((BOARD_WIDTH * 2) + 1) * (pos - 14)) - 3] = p;
            }
            else if(pos >= 30 && pos <= 45)
            {
               outputNew[output.length() - 3 - ((pos - 30) * 2)] = p;
            }
            else if(pos >= 46 && pos <= 59)
            {
               outputNew[(output.length() - (BOARD_WIDTH * 2) - 1) - (((BOARD_WIDTH * 2) + 1) * (pos - 45))] = p;
            }
            else if(pos < 0)
            {
               switch(_player)
               {
                  case 0:
                     outputNew[4 + (((BOARD_WIDTH * 2) + 1) * (-pos))] = p;
                     break;
                  case 1:
                     
                     break;
                  case 2:
                     
                     break;
                  case 3:
                     
                     break;
               }
            }
         }
      }
      
      output = String.valueOf(outputNew);
      
      return output;
   }
   
   public String toString()
   {
      String output = "";
      String temp = "";
      for(int y = 0; y < BOARD_WIDTH; y++)
      {
         for(int x = 0; x < BOARD_WIDTH; x++)
         {
            if(y == 0)
            {
               temp = "" + board[x];
               if(temp.equals("A"))
                  temp = "-";
               if(temp.equals("1"))
                  temp = "*";
            }
            else if(y > 0 && y < BOARD_WIDTH - 1)
            {
               if(x == 0)
               {
                  temp = "" + board[BOARD_LENGTH - y];
                  if(temp.equals("D"))
                     temp = "|";
                  if(temp.equals("-"))
                     temp = "|";
                  if(temp.equals(">"))
                     temp = "^";
               }
               else if(x == BOARD_WIDTH - 1)
               {
                  temp = "" + board[y + BOARD_WIDTH - 1];
                  if(temp.equals("B"))
                     temp = "|";
                  if(temp.equals("-"))
                     temp = "|";
                  if(temp.equals(">"))
                     temp = "V";
               }
               else
               {
                  if(x == 2 && y >= 1 && y <= 5)
                     temp = "█";
                  else if(x == BOARD_WIDTH - 3 && y <= BOARD_WIDTH - 1 && y >= BOARD_WIDTH - 5)
                     temp = "█";
                  else if(y == BOARD_WIDTH - 3 && x >= 1 && x <= 5)
                     temp = "█";
                  else if(y == 2 && x <= BOARD_WIDTH - 1 && x >= BOARD_WIDTH - 5)
                     temp = "█";
                  else if(x == 2 && y == 6)
                     temp = "A";
                  else if(x == 10 && y == 2)
                     temp = "B";
                  else if(x == 13 && y == 10)
                     temp = "C";
                  else if(x == 6 && y == 13)
                     temp = "D";
                  else
                     temp = " ";
               }
            }
            else if(y == BOARD_WIDTH - 1)
            {
               temp = "" + board[(BOARD_WIDTH - x - 1) + (BOARD_LENGTH / 2)];
               if(temp.equals("C"))
                  temp = "-";
               if(temp.equals(">"))
                  temp = "<";
            }
            output += temp + " ";
         }
         output += "\n";
      }
      
      char[] outputNew = output.toCharArray();
      for(int _player = 0; _player < 4; _player++)
      {
         char p = ' ';
         if(_player == 0)
            p = '%';
         else if(_player == 1)
            p = '{';
         else if(_player == 2)
            p = '&';
         else if(_player == 3)
            p = ']';
            
         for(int _piece = 0; _piece < 4; _piece++)
         {
            int pos = playerPos[_player][_piece];
            if(pos >= 0 && pos <= 15)
            {
               outputNew[pos * 2] = p;
            }
            else if(pos >= 16 && pos <= 29)
            {
               outputNew[(((BOARD_WIDTH * 2) + 1) * (pos - 14)) - 3] = p;
            }
            else if(pos >= 30 && pos <= 45)
            {
               outputNew[output.length() - 3 - ((pos - 30) * 2)] = p;
            }
            else if(pos >= 46 && pos <= 59)
            {
               outputNew[(output.length() - (BOARD_WIDTH * 2) - 1) - (((BOARD_WIDTH * 2) + 1) * (pos - 45))] = p;
            }
            else if(pos < 0 && pos != -7)
            {
               switch(_player)
               {
                  case 0:
                     outputNew[4 + (((BOARD_WIDTH * 2) + 1) * (-pos))] = p;
                     break;
                  case 1:
                     
                     break;
                  case 2:
                     
                     break;
                  case 3:
                     
                     break;
               }
            }
         }
      }
      
      output = String.valueOf(outputNew);
      
      return output;
   }
}