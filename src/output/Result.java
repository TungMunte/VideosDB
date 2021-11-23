package output;

public final class Result {
    private int id;
    private StringBuffer message;

    public Result() {

    }

    public StringBuffer getMessage() {
        return message;
    }

    public void setMessage(final StringBuffer message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Result(final int id, final StringBuffer message) {
        this.id = id;
        this.message = new StringBuffer(message);
    }

    @Override
    public String toString() {
        return "{" + "id:" + id
                + "," + "message:" + message + '}';
    }
}
