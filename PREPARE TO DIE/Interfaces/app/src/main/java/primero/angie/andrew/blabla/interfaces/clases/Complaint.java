package primero.angie.andrew.blabla.interfaces.clases;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thech on 19/10/2017.
 */
public class Complaint implements Parcelable {
    private String title;
    private String description;
    private int categoryId;
    private int userId;
    private boolean enabled;
    private Location location;
    private List<Picture> pictures;
    public static Complaint complaint;
    private String createdAt;
    private User user;
    private int id;

    public Complaint() {
        complaint = this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Location getLocation() {
        return location;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", location=" + location +
                ", createdAt='" + createdAt + '\'' +
                ", enabled=" + enabled +
                ", pictures=" + pictures +
                ", userId=" + userId +
                ", user=" + user +
                '}';
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public Complaint(Parcel in) {
        title = in.readString();
        description = in.readString();
        categoryId = in.readInt();
        userId = in.readInt();
        enabled = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            pictures = new ArrayList<Picture>();
            in.readList(pictures, Picture.class.getClassLoader());
        } else {
            pictures = null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(userId);
        dest.writeByte((byte) (enabled ? 0x01 : 0x00));
        if (pictures == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Complaint> CREATOR = new Parcelable.Creator<Complaint>() {
        @Override
        public Complaint createFromParcel(Parcel in) {
            return new Complaint(in);
        }

        @Override
        public Complaint[] newArray(int size) {
            return new Complaint[size];
        }
    };
}