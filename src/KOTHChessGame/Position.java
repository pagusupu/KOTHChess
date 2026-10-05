package KOTHChessGame;

public class Position {
    
    private int file;
    private int rank;

    public Position(int rank, int file){
        this.file = file;
        this.rank = rank;
    }
    
    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public void setValues(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }
    
    public boolean equals(Position pos){
        return (pos.getFile() == this.file && pos.getRank() == this.rank);
    }
    
    @Override
    public String toString(){
        // override toString method to print position (A6, B4, etc.)
        return (char)('A'+file) + "" + (rank+1);
    }
    
}
