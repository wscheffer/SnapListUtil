package za.co.retrorabbit.snaprecycler;

/**
 * Created by wsche on 2016/06/03.
 */
public class Thing {
    public long id;
    public int imageResource;
    public String text;

    public Thing(long id, int imageResource, String text) {
        this.id = id;
        this.imageResource = imageResource;
        this.text = text;
    }
}
