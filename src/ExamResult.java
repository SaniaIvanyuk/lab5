import java.io.Serializable;
import java.util.Arrays;

public class ExamResult implements Serializable {
    static final long serialVersionUID = 454564564564564L;
    public String f;
    public String i;
    public String o;
    public int numberGradeBook;
    public String facultyName;
    public int course;
    public Mark[] result = new Mark[5];


    public ExamResult(String f, String i, String o, int numberGradeBook, String facultyName, int course,
                      Mark m1, Mark m2, Mark m3, Mark m4, Mark m5) {
        this.setF(f);
        this.setI(i);
        this.setO(o);
        this.setNumberGradeBook(numberGradeBook);
        this.setFacultyName(facultyName);
        this.setCourse(course);

        result[0] = m1;
        result[1] = m2;
        result[2] = m3;
        result[3] = m4;
        result[4] = m5;
    }

    public ExamResult(){}

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public int getNumberGradeBook() {
        return numberGradeBook;
    }

    public void setNumberGradeBook(int numberGradeBook) {
        this.numberGradeBook = numberGradeBook;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public Mark[] getResult() {
        return result;
    }

    public void setResult(Mark[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "\nf='" + f + '\'' +
                ", i='" + i + '\'' +
                ", o='" + o + '\'' +
                ", numberGradeBook=" + numberGradeBook +
                ", facultyName='" + facultyName + '\'' +
                ", course=" + course +
                ", \nresult=" + Arrays.toString(result) +
                '}';
    }
}

