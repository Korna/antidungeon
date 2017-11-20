package kom.hikeside.Models.Atom;

/**
 * Created by Koma on 18.09.2017.
 */

public class WebData {
    protected String webKey;//primary key
    public WebData(){

    }
    public WebData(String webKey) {
        this.webKey = webKey;
    }

    public String getWebKey() {
        return webKey;
    }

    public void setWebKey(String webKey) {
        this.webKey = webKey;
    }
}
