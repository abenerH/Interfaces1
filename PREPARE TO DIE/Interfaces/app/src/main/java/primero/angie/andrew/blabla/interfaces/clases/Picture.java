package primero.angie.andrew.blabla.interfaces.clases;

        import java.util.ArrayList;

/**
 * Created by macyarin on 3/11/17.
 */

/*
* Esta clase es la que se encarga de proveer un modelo para trabajar con las imágenes asociadas a
* una denuncia. Esta contiene los atributos title, url y complaintId.
* */

public class Picture extends android.graphics.Picture {
    private String title;
    private String url;
    private int complaintId;
    private boolean enabled;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}

