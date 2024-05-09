package Entity;

public class Achat {

    private int id;
    private String image,type,description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Achat(String image, String type, String description) {
        this.image = image;
        this.type = type;
        this.description = description;
    }

    public Achat(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Achat(int id, String image, String type, String description) {
        this.id = id;
        this.image = image;
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Achat{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
