

public class point {
    private int type; // 1 start  , -1 end , 0 way
    
    private int x ; private int y;

    private point parent;
    private int numOfNeighbors;
    private point up;
    private point down;
    private point right;
    private point left;
    
    public point(int type , int x ,int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }
    public int getType() {
        return type;
    }
    public point getUp(){
        return this.up;
    }
    public point getDown(){
        return this.down;
    }
    public point getRight(){
        return this.right;
    }
    public point getLeft(){
        return this.left;
    }
    public point getParent() {
        return parent;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }


    public void setUp(point up){
        this.up = up;
    }

    public void setDown (point down){
        this.down = down;
    }    
    public void setRight(point right){
        this.right = right;
    }    
    public void setLeft (point left){
        this.left = left;
    }
    public void setParent(point parent) {
        this.parent = parent;
    }
    
    public boolean hasNext(){
        if(this.up == null && this.down == null && this.right == null && this.left == null){
            return false;
        }
        return true;
    }
    public point[] returnNextPoints(){
        int[] nextPointsInt = returnNextPointsInt();
        point[] nextPoints = new point[nextPointsInt.length];
        for(int i = 0 ;i<nextPoints.length;i++){
            switch(nextPointsInt[i]){
                case 0: // up
                    nextPoints[i] = this.up;
                    break;
                case 1: // down
                    nextPoints[i] = this.down;
                    break;
                case 2:// right
                    nextPoints[i] = this.right;
                    break;
                case 3:// left
                    nextPoints[i] = this.left;
                    break;
            }
        }
        return (nextPoints.length == 0)? null : nextPoints;
    }

    private int[] MakeArryShorter(int[] arry , int length){
        int[] newArry = new int[length];
        for(int i = 0 ;i<length ;i++ ){
            newArry[i] = arry[i];
        }
        return newArry;
    }

    public int[] returnNextPointsInt(){
        int[] nextPoints = new int[4];
        int i = 0;
        if(this.up != null){
            nextPoints[i] = 0;
            i++;
        }
        if(this.down != null){
            nextPoints[i] = 1;
            i++;
        }
        if(this.right != null){
            nextPoints[i] = 2;
            i++;
        }
        if(this.left != null){
            nextPoints[i] = 3;
            i++;
        }
        nextPoints = MakeArryShorter(nextPoints, i);
        return nextPoints;
    }

    public point addNext(int[][] board , int direction , int steps , int n , int type){
        point newP;
        switch(direction){
            case 0: // up
                newP = new point(type , this.x , this.y - steps);
                this.up = newP;
                newP.setParent(this);
                board[this.y - steps][this.x] = n;
                break;
            
            case 1: // down
                newP = new point(type , this.x , this.y + steps);
                this.down = newP;
                newP.setParent(this);
                board[this.y + steps][this.x] = n;
                break;
            
            case 2: // right
                newP = new point(type , this.x + steps , this.y);
                this.right = newP;
                newP.setParent(this);
                board[this.y][this.x + steps] = n;
                break;

            case 3: // left
                newP = new point(type , this.x - steps , this.y);
                this.left = newP;
                newP.setParent(this);
                board[this.y][this.x - steps] = n;
                break;
            
            default:
                newP = new point(-1 , this.x , this.y - steps);
                break; 
        }
        return newP;
    }

    public int numOfNeighbors(){
        return returnNextPointsInt().length;
    }

    public int getNumOfNeighbors(){
        return numOfNeighbors;
    }
}

