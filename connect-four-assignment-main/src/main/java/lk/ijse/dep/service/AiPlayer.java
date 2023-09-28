package lk.ijse.dep.service;

public class AiPlayer extends Player {
    int[][] virtualBoard =new int[6][5];
    public AiPlayer(Board board) {
        super(board);
    }
    @Override
    public void movePiece(int col) {

        col= findColumn();

        if(board.isLegalMove(col)){
            board.updateMove(col,Piece.GREEN);
            board.getBoardUI().update(col, false);
            Winner winner = board.findWinner();

            if(winner!=null){
                board.getBoardUI().notifyWinner(winner);
            }else{
                if(!board.existLegalMoves()){
                    board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }
        }
    }

    public int findColumn(){
        Piece[][] pieces = BoardImpl.getPieces();
        for (int col= 0; col < 6; col++) {
            for (int row = 0; row < 5; row++) {
                if (pieces[col][row] == Piece.BLUE) {
                    virtualBoard[col][row] = -1;
                } else if (pieces[col][row] == Piece.GREEN) {
                    virtualBoard[col][row] = 1;
                } else if (pieces[row][row] == Piece.EMPTY)
                    virtualBoard[col][row] = 0;
            }
        }

        int maxEval = (int) Double.NEGATIVE_INFINITY;
        int findCol= 0;
        for (int i = 0; i< virtualBoard.length; i++) {
            for (int j = 0; j <virtualBoard[0].length; j++) {
                if (virtualBoard[i][j] == 0) {
                    virtualBoard[i][j] = 1;
                    int  heuristicVal = minimax(virtualBoard, 0, false, i, j);
                    virtualBoard[i][j] = 0;
                    if ( heuristicVal > maxEval) {
                        maxEval =  heuristicVal;
                        findCol = i;
                    }
                }
            }
        }
        return findCol;
    }


    int minimax(int[][] virtualBoard, int depth, boolean maxmizingPlayer, int col, int row){
        int result= checkWin(virtualBoard, col, row);
        if (depth == 4 || result != 0) {
            if (result == 0)
                return 0;
            return checkWin(virtualBoard, col, row);

        }

        int maxEval;
        if (maxmizingPlayer) {
            maxEval= (int) Double.NEGATIVE_INFINITY;
            for (int i = 0; i < virtualBoard.length; i++) {
                for (int j = 0; j < virtualBoard[0].length; j++) {

                    if (virtualBoard[i][j] == 0) {
                        virtualBoard[i][j] = 1;
                        int heuristicVal = minimax(virtualBoard, depth + 1, false, i, j);
                        virtualBoard[i][j] = 0;
                        maxEval = Math.max(heuristicVal, maxEval);
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = (int) Double.POSITIVE_INFINITY;
            for (int i = 0; i < virtualBoard.length; i++) {
                for (int j = 0; j <virtualBoard[0].length; j++) {
                    if (virtualBoard[i][j] == 0) {
                        virtualBoard[i][j] = -1;
                        int heuristicVal = minimax(virtualBoard, depth + 1, true, i, j);
                        virtualBoard[i][j] = 0;
                        minEval = Math.min(heuristicVal, minEval);
                    }
                }
            }
            return minEval;
        }
    }
    public int checkWin(int [][] virtualBoard,int colIndex,int rowIndex) {
        int result=virtualBoard[colIndex][rowIndex];
        int count=0;
        int LoopCol=0;
        int LoopRow=0;

        L1: while(true) {

            for (int i = rowIndex; i >= 0; i--) {
                if (virtualBoard[colIndex][i] == result) {
                    count++;
                    LoopRow = i;
                } else {
                    break;
                }
                if (count == 4) {
                    return result;
                }
            }
            while (true) {
                count = 0;
                LoopCol = 0;
                LoopRow = 0;
                L2:
                for (int i = colIndex; i < virtualBoard.length; i++) {
                    if (virtualBoard[i][rowIndex] == result) {
                        count++;
                        LoopCol = i;
                        LoopRow = rowIndex;
                        if (count == 4) {
                            return result;
                        }
                    } else {
                        break L2;
                    }
                }
                if (colIndex == 0) {
                    break L1;
                }

                for (int i = colIndex; i >= 0; i--) {
                    if (i>0 && virtualBoard[i - 1][rowIndex] ==result) {
                        count++;
                        LoopCol = i;
                        LoopRow = rowIndex;
                        if (count == 4) {
                            return result;
                        }
                    } else {
                        break L1;
                    }
                }
            }
        }
        return 0;
    }
}