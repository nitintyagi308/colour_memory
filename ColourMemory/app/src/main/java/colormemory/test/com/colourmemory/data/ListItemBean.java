package colormemory.test.com.colourmemory.data;

/**
 * Created by nitin.tyagi on 1/16/2017.
 */

public class ListItemBean {

    private int score;
    private int rank;
    private String name;

    public ListItemBean() {
    }

    public ListItemBean(int score, int rank, String name) {
        this.score = score;
        this.rank = rank;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
