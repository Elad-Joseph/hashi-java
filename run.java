

public class run {
     public static void main(String[] args) {
        main m = new main();
        m.start();

        for(int i = 0;i<20;i++){
            for(int j = 0;j<20;j++){
                if(m.board[i][j] != 0){
                    System.out.print("["+m.board[i][j] +"]");
                }
                else{
                    System.out.print("[ ]");
                }
                    
            }
            System.out.println("");
        }
     }
}
