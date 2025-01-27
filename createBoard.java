

import java.util.Random;

public class createBoard {
    private int boardSize = 30;
    private int[][] board = new int[boardSize][boardSize]; // 0 notting , n point , -1 line
    private int[][] playableBoard = new int[boardSize][boardSize];
    private int numOfButtons = 40;
    private Random rn = new Random();
    private int maximumSteps = 7;
    private int minSteps = 4;

    private void start(){
        for(int i = 0;i<boardSize;i++){
            for(int j = 0;j<boardSize ;j++){
                board[i][j] = 0;
                playableBoard[i][j] = 0;
            }
        }
        int startx = rn.nextInt(boardSize);
        int starty = rn.nextInt(boardSize);
        point p = new point(1 , startx ,starty);
        board[starty][startx] = 1;
        if(!createBoard(p, 1)){
            start();
        }
        else{
            addBranches(p, 0);
            createPlayableBoard(p);
            printBoard();
        }
    }

    private boolean createBoard(point p , int n){
        if(n == numOfButtons){
            return true;
        }
        return direction(p, n);
    }
    private boolean direction(point p , int n){
        int type = 0;
        if(n + 1 == numOfButtons){
            type = -1;
        }
        int r = randomDirection(p); // 0 - up , 1 - down , 2 - right , 3 - left
        if(r == -1){
            return false;
        }
        int Steps = setSteps(p.getX(), p.getY(), r);
        point newP = p.addNext(board, r, Steps, n+1 , type);
        createLine(p, r);
        return createBoard(newP, n+1);
    }

    private int randomDirection(point p){
        int[] directions = new int[4];
        for(int i = 0;i<directions.length ; i++){
            directions[i] = i;
        }
        
        if(((p.getParent() == null)? false : (p.getParent().getDown() == p)) || p.getY() - minSteps < 0 || checkForLine(p.getX() , p.getY() , 0 , 1) <= minSteps){ // up
            directions = updateDirection(directions, 0);
        }
        if(((p.getParent() == null)? false : (p.getParent().getUp() == p)) || p.getY() + minSteps > boardSize -1 || checkForLine(p.getX() , p.getY() , 1 , 1) <= minSteps){ // down
            directions = updateDirection(directions, 1);
        }
        if(((p.getParent() == null)? false : (p.getParent().getLeft() == p)) || p.getX() + minSteps > boardSize -1 || checkForLine(p.getX() , p.getY() , 2 , 1) <= minSteps){ // right
            directions = updateDirection(directions, 2);
        }
        if(((p.getParent() == null)? false : (p.getParent().getRight() == p)) || p.getX() - minSteps < 0 || checkForLine(p.getX() , p.getY() , 3 , 1) <= minSteps){ // left
            directions = updateDirection(directions, 3);
        }
        if(directions.length == 0){
            return -1;
        }
        return directions[rn.nextInt(directions.length)];
    }

    private int setSteps(int x , int y , int direction){
        switch(direction){
            case 0: //up
                return rn.nextInt(minSteps ,checkForLine(x , y , 0 , minSteps));
            case 1: //down
                return rn.nextInt(minSteps,checkForLine(x,y,1 , minSteps));
            case 2: // right
                return rn.nextInt(minSteps ,checkForLine(x, y,2 , minSteps));
            case 3: // left
                return rn.nextInt(minSteps ,checkForLine(x, y,3 , minSteps));
        }
        return -1;
    }

    private int checkForLine(int x ,int y ,int direction , int start){
        switch (direction) {
            case 0: // up
                for(int i = start ; i<= maximumSteps;i++){
                    if(y-i < 0){
                        return i;
                    }
                    if(board[y-i][x] != 0){
                        return i-1;
                    }
                }
                return maximumSteps;
        
            case 1: // down
                for(int i = start ; i<= maximumSteps;i++){
                    if(y+i >= boardSize){
                        return i;
                    }
                    if(board[y+i][x] != 0){
                        return i-1;
                    }
                }
                return maximumSteps;
            
            case 2://right
                for(int i = start ; i<= maximumSteps;i++){
                    if(x+i >= boardSize){
                        return i;
                    }
                    if(board[y][x+i] != 0){
                        return i-1;
                    }
                }
                return maximumSteps;

            case 3: //left
                for(int i = start ; i<= maximumSteps;i++){
                    if(x-i < 0){
                        return i;
                    }
                    if(board[y][x-i] != 0){
                        return i-1;
                    }
                }
                return maximumSteps;
        }
        return -1;
    }


    private int[] updateDirection(int[] arry , int toRemove){
        int [] newArry = new int[arry.length -1];
        int remove = find(toRemove, arry);
        int n = 0;
        for(int i = 0;i < arry.length ;i++){
            if(i == remove){
                n++;
                continue;
            }
            newArry[i - n] = arry[i];
        }
        return newArry;
    }

    private int find(int num , int[] arr){
        for(int i = 0;i<arr.length;i++){
            if(arr[i] == num){
                return i;
            }
        }
        return -1;
    }

    private void createLine(point p , int direction){
        switch (direction) {
            case 0: // up
                for(int y = p.getY()-1;y>p.getUp().getY();y--){
                    board[y][p.getX()] = -1;
                }
                break;
        
            case 1:// down
                for(int y = p.getY()+1;y<p.getDown().getY();y++){
                    board[y][p.getX()] = -1;
                }
                break;
            
            case 2:// right
                for(int x = p.getX()+1;x<p.getRight().getX();x++){
                    board[p.getY()][x] = -1;
                }
                break;

            case 3:// left
                for(int x = p.getX()-1 ;x>p.getLeft().getX();x--){
                    board[p.getY()][x] = -1;
                }
                break;
        }
    }

    private void printBoard(){
        for(int i = 0;i<boardSize;i++){
            for(int j = 0;j<boardSize;j++){
                if(board[i][j] != 0 && board[i][j] != -1){
                    System.out.print("  "+playableBoard[i][j]+" ");
                }
                else{
                    System.out.print("    ");
                }
            }
            System.out.println("");
        }
        for(int i = 0 ;i<boardSize*4;i++){
            System.out.print("-");
        }
        System.out.println("");
        for(int i = 0;i<boardSize;i++){
            for(int j = 0;j<boardSize;j++){
                if(board[i][j] != 0){
                    if(board[i][j]<10 && board[i][j] >= 0){
                        System.out.print("[ "+board[i][j] +"]");
                    }
                    else{
                        System.out.print("["+board[i][j] +"]");
                    }
                }
                else{
                    System.out.print("[  ]");
                }
                    
            }
            System.out.println("");
        }
    }



    private void addBranches(point p , int n){
        if(p == null){
            return;
        }
        point next = (p.returnNextPoints() == null)? null : p.returnNextPoints()[0];
        if(canAddBranch(p) &&true){
            int randomDirection = randomDirection(p);
            int stepAmount = setSteps(p.getX() , p.getY() , randomDirection);
            p.addNext(board, randomDirection, stepAmount, numOfButtons + n + 1 , -1);
            createLine(p, randomDirection);
            n++;
        }
        addBranches(next, n);
    }
    
    private boolean canAddBranch(point p , int n){
        int[] directions = new int[4];
        for(int i = 0;i<directions.length;i++){
            directions[i] = i;
        }
        for(int i = 0;i<p.returnNextPointsInt().length;i++){
            directions = updateDirection(directions, p.returnNextPointsInt()[i]);
        }
        boolean flag = false;
        for(int i = 0;i<directions.length;i++){
            flag = (checkForLine(p.getX(),p.getY(), directions[i] , 0) <= minSteps)? false : true;
        }
        return flag;
    }
    private boolean canAddBranch(point p){
        int[] directions = new int[4];
        for(int i = 0;i<directions.length ; i++){
            directions[i] = i;
        }
        
        if(p.getY() - minSteps < 0 || checkForLine(p.getX() , p.getY() , 0 , 1) <= minSteps){ // up
            directions = updateDirection(directions, 0);
        }
        if(p.getY() + minSteps > boardSize -1 || checkForLine(p.getX() , p.getY() , 1 , 1) <= minSteps){ // down
            directions = updateDirection(directions, 1);
        }
        if(p.getX() + minSteps > boardSize -1 || checkForLine(p.getX() , p.getY() , 2 , 1) <= minSteps){ // right
            directions = updateDirection(directions, 2);
        }
        if(p.getX() - minSteps < 0 || checkForLine(p.getX() , p.getY() , 3 , 1) <= minSteps){ // left
            directions = updateDirection(directions, 3);
        }
        return (directions.length == 0)? false : true;
    }

    private void createPlayableBoard(point p){
        point[] points = p.returnNextPoints();
        if(points == null){
            return;
        }
        point c;
        for(int i = 0;i<points.length;i++){
            c = points[i];
            if(!c.hasNext()){
                playableBoard[c.getY()][c.getX()] = 1;
            }
        }
        playableBoard[p.getY()][p.getX()] = p.numOfNeighbors() +((p.getParent() == null)? 0 : 1);
        for(int i = 0;i<points.length;i++){
            createPlayableBoard(points[i]);
        }
    }

    public int[][] exportBoard(){
        start();
        return playableBoard;
    }
}
