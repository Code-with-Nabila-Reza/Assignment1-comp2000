public abstract class Collectible {
    private String Cname;
    private int points; // u gain points when u collect items

    public Collectible(String Cname, int points){
        this.Cname = Cname;
        this.points = points;
    }

    public String getName(){
        return Cname;
    }

    public int getPoints(){
        return points;
    }
}
