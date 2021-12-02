import java.io.Serializable;

public class Mark implements Serializable {
    static final long serialVersionUID = 2323232323L;
    String subjectName;
    String teacherName;
    int mark;

    public Mark(String subjectName, String teacherName, int mark) {
        this.setSubjectName(subjectName);
        this.setTeacherName(teacherName);
        this.setMark(mark);
    }

    public Mark(){
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public static int CheckCorrectMark(String value) {
        int m = -1;
        try {
            m = Integer.parseInt(value) > 0 && Integer.parseInt(value) <= 5 ? Integer.parseInt(value) : -1;
        } catch (NumberFormatException e) {
            m = -1;
        }
        return m;
    }

    @Override
    public String toString() {
        return "\nMark{" +
                "subjectName='" + subjectName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", mark=" + mark +
                '}';
    }
}