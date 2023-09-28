package lk.ijse.dep.service;

public class BoardImpl implements Board {

    private static Piece[][] pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
    private BoardUI boardUI;

    public BoardUI getBoardUI() {return boardUI;}
    public static Piece[][] getPieces() {
        return pieces;
    }

    public BoardImpl(BoardUI boardUI){
        this.boardUI = boardUI;
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++) {
                pieces[i][j] = Piece.EMPTY;
            }
        }
    }

    @Override
    public int findNextAvailableSpot(int col){
        for (int i = 0; i < 5; i++) {
            if (pieces[col][i].equals(Piece.EMPTY)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
        if(findNextAvailableSpot(col)==-1) {
            return false;
        }else{return true;}
    }

    @Override
    public boolean existLegalMoves() {
        for (int i=0; i<6; i++) {
            if (isLegalMove(i) == true) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {

        for (int i=0; i<5; i++){
            if(pieces[col][i]==Piece.EMPTY){
                pieces[col][i]=move;
                break;
            }
        }
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row]=move;
    }

    @Override
    public Winner findWinner() {
        Winner win;
        for (int row=0; row<5; row++){

            if(pieces[0][row]==Piece.GREEN && pieces[1][row]==Piece.GREEN && pieces[2][row]==Piece.GREEN && pieces[3][row]==Piece.GREEN){
                win = new Winner(Piece.GREEN,0,row,3,row);
                return win;
            }else if(pieces[0][row]==Piece.BLUE && pieces[1][row]==Piece.BLUE && pieces[2][row]==Piece.BLUE && pieces[3][row]==Piece.BLUE){
                win = new Winner(Piece.BLUE,0,row,3,row);
                return win;
            }else if(pieces[1][row]==Piece.GREEN && pieces[2][row]==Piece.GREEN && pieces[3][row]==Piece.GREEN &&pieces[4][row]==Piece.GREEN){
                win = new Winner(Piece.GREEN,1,row,4,row);
                return win;
            }else if(pieces[1][row]==Piece.BLUE && pieces[2][row]==Piece.BLUE && pieces[3][row]==Piece.BLUE &&pieces[4][row]==Piece.BLUE){
                win = new Winner(Piece.BLUE,1,row,4,row);
                return win;
            }else if(pieces[2][row]==Piece.GREEN && pieces[3][row]==Piece.GREEN && pieces[4][row]==Piece.GREEN &&pieces[5][row]==Piece.GREEN){
                win = new Winner(Piece.GREEN,2,row,5,row);
                return win;
            }else if (pieces[2][row]==Piece.BLUE && pieces[3][row]==Piece.BLUE && pieces[4][row]==Piece.BLUE &&pieces[5][row]==Piece.BLUE){
                win = new Winner(Piece.BLUE,2,row,5,row);
                return win;
            }
        }

        for (int col=0; col<6; col++){

            if(pieces[col][0]==Piece.GREEN && pieces[col][1]==Piece.GREEN && pieces[col][2]==Piece.GREEN &&pieces[col][3]==Piece.GREEN){
                win = new Winner(Piece.GREEN,col,0,col,3);
                return win;
            }else if(pieces[col][0]==Piece.BLUE && pieces[col][1]==Piece.BLUE && pieces[col][2]==Piece.BLUE &&pieces[col][3]==Piece.BLUE){
                win = new Winner(Piece.BLUE,col,0,col,3);
                return win;
            }else if(pieces[col][1]==Piece.GREEN && pieces[col][2]==Piece.GREEN && pieces[col][3]==Piece.GREEN &&pieces[col][4]==Piece.GREEN){
                win = new Winner(Piece.GREEN,col,1,col,4);
                return win;
            }else if(pieces[col][1]==Piece.BLUE && pieces[col][2]==Piece.BLUE && pieces[col][3]==Piece.BLUE &&pieces[col][4]==Piece.BLUE){
                win = new Winner(Piece.BLUE,col,1,col,4);
                return win;
            }

        }
        return null;
    }

}