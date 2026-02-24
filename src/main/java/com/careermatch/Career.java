
public class Career {
    private int careerId;
    private String title;
    private String category;
    private String description;

    public Career(int careerId, String title, String category, String description) {
        this.careerId = careerId;
        this.title = title;
        this.category = category;
        this.description = description;
    }

    public int getCareerId() { return careerId; }
    public void setCareerId(int careerId) { this.careerId = careerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

