

import java.util.Random;

public class main {
    int boardSize = 20;
    int[][] board = new int[boardSize][boardSize]; // 0 notting , 1 point , 2 line
    int numOfButtons = 10;
    int steps = 5;
    Random rn = new Random();

    public void start(){
        for(int i = 0;i<boardSize;i++){
            for(int j = 0;j<boardSize ;j++){
                board[i][j] = 0;
            }
        }
        int startx = rn.nextInt(boardSize);
        int starty = rn.nextInt(boardSize);
        point p = new point(1 , startx ,starty);
        if(!createBoard(p, 0)){
            start();
        }
    }

    public boolean createBoard(point p , int n){
        if(n == numOfButtons){
            return true;
        }
        return direction(p, n);
    }

    public boolean direction(point p , int n){
        point newP;
        int type = 0;
        if(n + 1 == numOfButtons){
            type = -1;
        }
        int r = randomDirection(p); // * 0 - up , 1 - down , 2 - right , 3 - left
        if(r == -1){
            return false;
        }
        switch (r) {
            case 0: // up
                    newP = new point(type , p.getX() , p.getY() - steps);
                    p.setUp(newP);
                    newP.setParent(p);
                    board[p.getY() - steps][p.getX()] = n+1;
                    return createBoard(newP , n+1);
        
            case 1: // down
                    newP = new point(type , p.getX() , p.getY()+ steps);
                    p.setDown(newP);
                    newP.setParent(p);
                    board[p.getY() + steps][p.getX()] = n+1;
                    return createBoard(newP , n+1);
            
            case 2: // right
                    newP = new point(type , p.getX() + steps , p.getY());
                    p.setRight(newP);
                    newP.setParent(p);
                    board[p.getY()][p.getX() + steps] = n+1;
                    return createBoard(newP , n+1);
            
            case 3: // left
                    newP = new point(type , p.getX() - steps , p.getY());
                    p.setLeft(newP);
                    newP.setParent(p);
                    board[p.getY()][p.getX() - steps] = n+1;
                     return createBoard(newP , n+1);
        }
        return true;
    }

    // true - if colition will not acoure , false - if collition will acoure
    public boolean checkForColitions(point p , int nextX , int nextY){
        if(p.getParent() != null){
            if(!checkForColitions(p.getParent(), nextX, nextY)){
                return false;
            }
        }
        else{
            return true;
        }
        if(p.getParent().getX() == nextX && p.getParent().getY() == nextY){
            return false;
        }
        else{
            return true;
        }
    }

    public int randomDirection(point p){
        int[] directions = new int[4];
        for(int i = 0;i<directions.length;i++){
            directions[i] = i;
        }

        if(p.getY() - steps < 0 || !checkForColitions(p, p.getX(), p.getY()-steps)){ // up
            directions = updateDirection(directions, 0);
        }

        if(!(p.getY() + steps < 20) || !checkForColitions(p, p.getX(), p.getY()+steps)){ //down
            directions = updateDirection(directions, 1);
        }

        if(!(p.getX() + steps < 20) || !checkForColitions(p, p.getX() + steps, p.getY())){ // right
            directions = updateDirection(directions , 2);
        }

        if(p.getX() - steps < 0 || !checkForColitions(p, p.getX() - steps, p.getY())){ // left
            directions = updateDirection(directions, 3);
        }
        if(directions.length == 0){
            return -1;
        }
        return directions[rn.nextInt(directions.length)];

    }

    public int[] updateDirection(int[] directions , int directionToRemove){
        int [] newDirections = new int[directions.length -1];
        int remove = find(directionToRemove, directions);
        int n = 0;
        for(int i = 0;i < directions.length ;i++){
            if(i == remove){
                n++;
                continue;
            }
            newDirections[i - n] = directions[i];
        }
        return newDirections;
    }

    public int find(int num , int[] arr){
        for(int i = 0;i<arr.length;i++){
            if(arr[i] == num){
                return i;
            }
        }
        return -1;
    }




}
