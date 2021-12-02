import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class FileWork {
    @JsonProperty("examResults")

    public static final int MARKS_COUNT = 5;
    public static final int DELIMITER_BYTE_VALUE = 46;
    private static FileOutputStream outFile;
    private static FileInputStream inFile;

    public void serialize(ExamResults examResults, String fileName) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream outOOS = new ObjectOutputStream(fileOut);
            outOOS.writeObject(examResults);
            outOOS.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // examResults.examResults.clear();
    }

    public ExamResults deserialize(String fileName) throws IOException {
        ExamResults examResults = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream inOIS = new ObjectInputStream(fileIn);
           examResults = (ExamResults) inOIS.readObject();
            inOIS.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException с) {
            System.out.println("Not found!");
            с.printStackTrace();
        }
        return examResults;
    }

    public void jacksonSerialize(ExamResults examResults, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.writeValue(new File(fileName), examResults);
    }

    public ArrayList jacksonDeSerialize(ArrayList<ExamResult> examResults, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ExamResults newExamResults = objectMapper.readValue(new File(fileName), ExamResults.class);
        return examResults = newExamResults.examResults;
    }

    public void saveBinary(ArrayList<ExamResult> examResults, String fileName) throws IOException {

        byte[] bytesToWrite;
        byte[] f, i, o, numberGradeBook, facultyName, course, delim;
        byte[][] subjectName = new byte[MARKS_COUNT][];
        byte[][] teacherName = new byte[MARKS_COUNT][];
        byte[][] mark = new byte[MARKS_COUNT][];
        String delimiter = ".";
        for (ExamResult object : examResults) {
            f = object.getF().getBytes(StandardCharsets.UTF_8);
            i = object.getI().getBytes(StandardCharsets.UTF_8);
            o = object.getO().getBytes(StandardCharsets.UTF_8);
            String strNGB = Integer.toString(object.getNumberGradeBook());
            numberGradeBook = strNGB.getBytes(StandardCharsets.UTF_8);
            facultyName = object.getFacultyName().getBytes(StandardCharsets.UTF_8);
            String strCourse = Integer.toString(object.getCourse());
            course = strCourse.getBytes(StandardCharsets.UTF_8);

            for (int j = 0; j < MARKS_COUNT; j++) {
                subjectName[j] = object.result[j].getSubjectName().getBytes(StandardCharsets.UTF_8);
            }

            for (int j = 0; j < MARKS_COUNT; j++) {
                teacherName[j] = object.result[j].getTeacherName().getBytes(StandardCharsets.UTF_8);
            }

            String[] m = new String[MARKS_COUNT];
            for (int j = 0; j < MARKS_COUNT; j++) {
                m[j] = Integer.toString(object.result[j].getMark());
            }

            for (int j = 0; j < MARKS_COUNT; j++) {
                mark[j] = m[j].getBytes(StandardCharsets.UTF_8);
            }

            delim = delimiter.getBytes(StandardCharsets.UTF_8);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(f);
            outputStream.write(delim);
            outputStream.write(i);
            outputStream.write(delim);
            outputStream.write(o);
            outputStream.write(delim);
            outputStream.write(numberGradeBook);
            outputStream.write(delim);
            outputStream.write(facultyName);
            outputStream.write(delim);
            outputStream.write(course);
            outputStream.write(delim);

            for (int j = 0; j < MARKS_COUNT; j++) {
                outputStream.write(subjectName[j]);
                outputStream.write(delim);
                outputStream.write(teacherName[j]);
                outputStream.write(delim);
                outputStream.write(mark[j]);
                outputStream.write(delim);
            }

            bytesToWrite = outputStream.toByteArray();
            outFile = null;
            boolean isOpened = false;
            try {
                outFile = new FileOutputStream(fileName, true);
                isOpened = true;
                outFile.write(bytesToWrite); //запись в файл
            } catch (FileNotFoundException e) {
                System.out.println("Невозможно произвести запись в файл:" + fileName);
            } catch (IOException e) {
                System.out.println("Ошибка ввода/вывода:" + e);
            }
            if (isOpened) {
                outFile.close();
            }
        }
    }

    public void save(ArrayList<ExamResult> examResults, String fileName) throws IOException {
        FileWriter outStream = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(outStream);
        for (ExamResult student : examResults) {
            try {
                bw.write(student.getF());
                bw.write(System.lineSeparator());
                bw.write(student.getI());
                bw.write(System.lineSeparator());
                bw.write(student.getO());
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(student.getNumberGradeBook()));
                bw.write(System.lineSeparator());
                bw.write(student.getFacultyName());
                bw.write(System.lineSeparator());
                bw.write(String.valueOf(student.getCourse()));
                bw.write(System.lineSeparator());
                for (int i = 0; i < MARKS_COUNT; i++) {
                    bw.write(student.result[i].getTeacherName());
                    bw.write(System.lineSeparator());
                    bw.write(student.result[i].getSubjectName());
                    bw.write(System.lineSeparator());
                    bw.write(String.valueOf(student.result[i].getMark()));
                    bw.write(System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        examResults.clear();
        bw.close();
        outStream.close();
    }

    public void loadBinary(ArrayList<ExamResult> examResults, String fileName) throws IOException {
        examResults.clear();
        byte[] wert = new byte[0];
        int amount = 0;
        try {

            inFile = new FileInputStream(fileName);
            int bytesAvailable = inFile.available(); //сколько можно считать
            System.out.println("Available: " + bytesAvailable);

            byte[] bytesRead = new byte[bytesAvailable]; //куда считываем
            int count = inFile.read(bytesRead, 0, bytesAvailable);

            System.out.println("Было считано байт: " + count);
            System.out.println(Arrays.toString(bytesRead));
            byte[] trap = bytesRead;
            wert = trap;
            amount = count;
            inFile.close();

        } catch (FileNotFoundException e) {
            System.out.println("Невозможно произвести чтение из файла:" + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода:" + e);
        }
        byte[] dataBase = wert;
        String f = "";
        String i = "";
        String o = "";
        int numberGradeBook = 0;
        String facultyName = "";
        int course = 0;
        String[] subjectName = new String[]{"", "", "", "", ""};
        String[] teacherName = new String[]{"", "", "", "", ""};
        int[] mark = new int[]{0, 0, 0, 0, 0};
        String strNGB = "";
        String strCourse = "";
        String strMark = "";
        int q = 0;
        for (int j = 0; j < amount; j++) {
            for (j = q; j < amount; j++) {
                if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                    q = j + 1;
                    break;
                }
                byte[] tempArr = new byte[1];
                tempArr[0] = dataBase[j];
                String str = new String(tempArr, "UTF-8");
                f = f + str;
            }

            for (j = q; j < amount; j++) {
                if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                    q = j + 1;
                    break;
                }
                byte[] tempArr = new byte[1];
                tempArr[0] = dataBase[j];
                String str = new String(tempArr, "UTF-8");
                i = i + str;
            }

            for (j = q; j < amount; j++) {
                if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                    q = j + 1;
                    break;
                }
                byte[] tempArr = new byte[1];
                tempArr[0] = dataBase[j];
                String str = new String(tempArr, "UTF-8");
                o = o + str;
            }

            for (j = q; j < amount; j++) {
                if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                    q = j + 1;
                    break;
                }
                byte[] tempArr = new byte[1];
                tempArr[0] = dataBase[j];
                String str = new String(tempArr, "UTF-8");
                strNGB = strNGB + str;
                numberGradeBook = Integer.parseInt(strNGB);
            }

            for (j = q; j < amount; j++) {
                if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                    q = j + 1;
                    break;
                }
                byte[] tempArr = new byte[1];
                tempArr[0] = dataBase[j];
                String str = new String(tempArr, "UTF-8");
                facultyName = facultyName + str;
            }

            for (j = q; j < amount; j++) {
                if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                    q = j + 1;
                    break;
                }
                byte[] tempArr = new byte[1];
                tempArr[0] = dataBase[j];
                String str = new String(tempArr, "UTF-8");
                strCourse = strCourse + str;
                course = Integer.parseInt(strCourse);
            }

            for (int k = 0; k < MARKS_COUNT; k++) {
                for (j = q; j < amount; j++) {
                    if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                        q = j + 1;
                        break;
                    }
                    byte[] tempArr = new byte[1];
                    tempArr[0] = dataBase[j];
                    String str = new String(tempArr, "UTF-8");
                    subjectName[k] += str;
                }
                for (j = q; j < amount; j++) {
                    if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                        q = j + 1;
                        break;
                    }
                    byte[] tempArr = new byte[1];
                    tempArr[0] = dataBase[j];
                    String str = new String(tempArr, "UTF-8");
                    teacherName[k] += str;
                }
                strMark = "";
                for (j = q; j < amount; j++) {
                    if (dataBase[j] == DELIMITER_BYTE_VALUE) {
                        q = j + 1;
                        break;
                    }
                    byte[] tempArr = new byte[1];
                    tempArr[0] = dataBase[j];
                    String str = new String(tempArr, "UTF-8");
                    strMark = strMark + str;
                    mark[k] = Integer.parseInt(strMark);
                }
            }

            j -= 1;
            if (j == amount - 1) {
                break;
            }

            examResults.add(new ExamResult(f, i, o, numberGradeBook, facultyName, course,
                    new Mark(subjectName[0], teacherName[0], mark[0]),
                    new Mark(subjectName[1], teacherName[1], mark[1]),
                    new Mark(subjectName[2], teacherName[2], mark[2]),
                    new Mark(subjectName[3], teacherName[3], mark[3]),
                    new Mark(subjectName[4], teacherName[4], mark[4])));

            f = "";
            i = "";
            o = "";
            numberGradeBook = 0;
            facultyName = "";
            course = 0;
            strNGB = "";
            strCourse = "";
            strMark = "";
            subjectName = new String[]{"", "", "", "", ""};
            teacherName = new String[]{"", "", "", "", ""};
            mark = new int[]{0, 0, 0, 0, 0};
        }
    }

    public void load(ArrayList<ExamResult> examResults, String fileName) throws IOException {
        examResults.clear();
        try {
            Scanner scan = new Scanner(new FileReader(fileName));
            String f;
            String i;
            String o;
            int numberGradeBook;
            String facultyName;
            int course;
            String[] subjectName = new String[MARKS_COUNT];
            String[] teacherName = new String[MARKS_COUNT];
            int[] mark = new int[MARKS_COUNT];

            while (scan.hasNextLine()) {
                f = scan.nextLine();
                i = scan.nextLine();
                o = scan.nextLine();
                numberGradeBook = Integer.valueOf(scan.nextLine());
                facultyName = scan.nextLine();
                course = Integer.valueOf(scan.nextLine());
                for (int j = 0; j < MARKS_COUNT; j++) {
                    teacherName[j] = scan.nextLine();
                    subjectName[j] = scan.nextLine();
                    mark[j] = scan.nextInt();
                    scan.nextLine();
                }

                examResults.add(new ExamResult(f, i, o, numberGradeBook, facultyName, course,
                        new Mark(teacherName[0], subjectName[0], mark[0]),
                        new Mark(teacherName[1], subjectName[1], mark[1]),
                        new Mark(teacherName[2], subjectName[2], mark[2]),
                        new Mark(teacherName[3], subjectName[3], mark[3]),
                        new Mark(teacherName[4], subjectName[4], mark[4])));
            }
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String lastReserveName() throws IOException {
        File file = new File(".");
        File[] files = file.listFiles();

        String lastFile = "";
        for (int i = files.length - 1; i >= 0; i--) {
            if (files[i].isFile() && files[i].getName().endsWith(".backup")) {
                lastFile = files[i].getName();
                break;
            }
        }
        return lastFile;
    }
}
