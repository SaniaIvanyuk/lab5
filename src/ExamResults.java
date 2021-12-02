import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class ExamResults implements Serializable {
    @JsonProperty("examResults")
    ArrayList<ExamResult> examResults;
    @JsonProperty("description")
    String description;
    static final long serialVersionUID = 656565665L;

    public static final String[] discipline = {"English", "Math", "Programming", "Physics", "Chemistry"};
    public static final int MARK_COUNT = 5;

    public ExamResults() {
        description = LocalDate.now().toString();
        this.examResults = new ArrayList<>();
    }

    public ExamResults(ExamResults examResults) {
        this.examResults = examResults.examResults;
        this.description = examResults.description;
    }

    void add(ExamResult examRes) {
        examResults.add(examRes);
    }

    public void IsExcellent() {
        boolean Check = false;
        for (ExamResult object : examResults) {
            for (int i = 0; i < MARK_COUNT; i++) {
                if (object.result[i].getMark() > 3) {
                    Check = true;
                } else {
                    Check = false;
                    break;
                }
            }
            if (Check == true) {
                System.out.println(object);
            }
        }
    }

    public void IsBadStudent() {
        St:
        for (ExamResult object : examResults) {
            for (int i = 0; i < MARK_COUNT; i++) {
                if (object.result[i].getMark() == 2) {
                    {
                        System.out.println(object);
                        continue St;
                    }
                }
            }
        }
    }

    public void RemoveBadStudents() {
        for (int k = 0; k < examResults.size(); k++) {
            for (int i = 0; i < MARK_COUNT; i++) {
                if (examResults.get(k).result[i].getMark() == 2) {
                    examResults.remove(examResults.get(k));
                    k--;
                    break;
                }
            }
        }
    }

    public void SubjectWithBadMark() {
        int marks[] = {0, 0, 0, 0, 0};
        for (ExamResult object : examResults) {
            for (int i = 0; i < MARK_COUNT; i++) {
                if (object.result[i].getSubjectName().equals(discipline[0]) && object.result[i].getMark() == 2) {
                    marks[0]++;
                }
                if (object.result[i].getSubjectName().equals(discipline[1]) && object.result[i].getMark() == 2) {
                    marks[1]++;
                }
                if (object.result[i].getSubjectName().equals(discipline[2]) && object.result[i].getMark() == 2) {
                    marks[2]++;
                }
                if (object.result[i].getSubjectName().equals(discipline[3]) && object.result[i].getMark() == 2) {
                    marks[3]++;
                }
                if (object.result[i].getSubjectName().equals(discipline[4]) && object.result[i].getMark() == 2) {
                    marks[4]++;
                }
            }
        }

        ArrayList<Integer> counters = new ArrayList<>();
        for (int i = 0; i < marks.length; i++) {
            counters.add(marks[i]);
        }

        if (marks[0] == Collections.max(counters)) {
            System.out.println(discipline[0] + " - " + marks[0]);
        }
        if (marks[1] == Collections.max(counters)) {
            System.out.println(discipline[1] + " - " + marks[1]);
        }
        if (marks[2] == Collections.max(counters)) {
            System.out.println(discipline[2] + " - " + marks[2]);
        }
        if (marks[3] == Collections.max(counters)) {
            System.out.println(discipline[3] + " - " + marks[3]);
        }
        if (marks[4] == Collections.max(counters)) {
            System.out.println(discipline[4] + " - " + marks[4]);
        }
    }

    void saveFile(String fileName) throws IOException {
        FileWork saveToFile = new FileWork();
        saveToFile.saveBinary(examResults, fileName);
    }

    void loadFile(String fileName) throws IOException {
        FileWork loadToBase = new FileWork();
        loadToBase.loadBinary(examResults, fileName);
    }

    void serializeFile(String fileName) throws IOException {
        FileWork saveToFile = new FileWork();
        saveToFile.serialize(this, fileName);
    }

    void deserializeFile(String fileName) throws IOException {
        FileWork loadToBase = new FileWork();
        ExamResults er = new ExamResults(loadToBase.deserialize(fileName));
        this.examResults = er.examResults;
        this.description = er.description;
    }

    void JacksonSerializeFile(String fileName) throws IOException {
        FileWork saveToFile = new FileWork();
        saveToFile.jacksonSerialize(this, fileName);
        examResults.clear();
    }

    void jacksonDeserializeFile(String fileName) throws IOException {
        FileWork loadToBase = new FileWork();
        this.examResults = loadToBase.jacksonDeSerialize(examResults, fileName);
    }

    @Override
    public String toString() {
        return "ExamResults{" +
                examResults +
                '}';
    }
}