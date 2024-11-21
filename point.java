

public class point {
    private int type; // 1 start  , -1 end , 0 way
    
    private int x ; private int y;

    private point parent;
    
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
}

