package DSAProject;

class Appointment {
    private final String title;
    private final String date;
    private final String time;
    private final String description;

    public Appointment(String title, String date, String time, String description) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "Title: " + title + "\nDate: " + date + "\nTime: " + time + "\nDescription: " + description;
    }
}
