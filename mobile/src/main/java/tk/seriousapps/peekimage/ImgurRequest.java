package tk.seriousapps.peekimage;

/**
 * Created by taquigrafiau on 23/04/2016.
 */
public class ImgurRequest {
    public static String getUrl() {
        String imageId = getImageId();
        String Out = "http://i.imgur.com/" + imageId + "b.png";
        return Out;
    }

    public static String getImageId() {
        int len = 5;
        StringBuilder text = new StringBuilder();
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < len; i++) {
            int nextpossiblecharposition = (int) Math.floor(Math.random() * possible.length());
            String imgurchar = Character.toString(possible.charAt(nextpossiblecharposition));
            if (text.indexOf(imgurchar) == -1) {
                text.append(imgurchar);
            } else {
                i--;
            }
        }
        String out = text.toString();
        return out;
    }
}
