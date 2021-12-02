import java.io.IOException;
import java.util.Scanner;


public class Lab5_main {
    public static ExamResults exRes = new ExamResults();

    public static int CheckCorrectFunction(String value) {
        int func = -1;
        try {
            func = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            func = -1;
        }

        return func;
    }

    public static void main(String[] args) throws IOException {
        final String[] discipline = {"English", "Math", "Programming", "Physics", "Chemistry"};
        int function;
        Scanner in = new Scanner(System.in);

        /*exRes.add(new ExamResult("Zhuravlev", "David", "Timofeevich", 1, "IKS", 2,
                new Mark(discipline[0], "Kudryashova", 5), new Mark(discipline[1], "Fedorov", 4),
                new Mark(discipline[2], "Gavrilov", 4), new Mark(discipline[3], "Aleksandrov", 5),
                new Mark(discipline[4], "Zayceva", 5)));

        exRes.add(new ExamResult("Ivanov", "Ivan", "Ivanovich", 2, "IIRBT", 3,
                new Mark(discipline[0], "Markova", 3), new Mark(discipline[1], "Fedorov", 5),
                new Mark(discipline[2], "Demetiev", 4), new Mark(discipline[3], "Sharapov", 2),
                new Mark(discipline[4], "Rogova", 2)));

        exRes.add(new ExamResult("Sokolova", "Olga", "Nikolaevna", 3, "UNI", 3,
                new Mark(discipline[0], "Kudryashova", 2), new Mark(discipline[1], "Novikov", 2),
                new Mark(discipline[2], "Dorofeev", 1), new Mark(discipline[3], "Kiselev", 2),
                new Mark(discipline[4], "Zayceva", 2)));

        exRes.add(new ExamResult("Vasiliev", "Rostislav", "Arkadievich", 4, "IEKSU", 3,
                new Mark(discipline[0], "Kudryashova", 3), new Mark(discipline[1], "Fedorov", 3),
                new Mark(discipline[2], "Dorofeev", 5), new Mark(discipline[3], "Prohorov", 5),
                new Mark(discipline[4], "Kuzmin", 4)));

        exRes.add(new ExamResult("Gorbunova", "Anastasiya", "Stepanovna", 5, "HTF", 3,
                new Mark(discipline[0], "Markova", 5), new Mark(discipline[1], "Novikov", 2),
                new Mark(discipline[2], "Gavrilov", 3), new Mark(discipline[3], "Sharapov", 2),
                new Mark(discipline[4], "Gulyaev", 4)));*/

        //exRes.examResults.clear();

        do {
            System.out.println(Color.CYAN_BOLD);
            System.out.println("\nСписок функций: ");
            System.out.println("[1] - Вывод массива на экран");
            System.out.println("[2] - Показать отличников");
            System.out.println("[3] - Показать двоечников");
            System.out.println("[4] - Показать дисциплину с наибольшим количеством двоечников");
            System.out.println("[5] - Удаление студентов-двоечников");
            System.out.println("[6] - Сохранение в файл");
            System.out.println("[7] - Загрузить из файла");
            System.out.println("[8] - Сериализация базы данных");
            System.out.println("[9] - Десериализация базы данных");
            System.out.println("[10] - Сериализация Jackson базы данных");
            System.out.println("[11] - Десериализация Jackson базы данных");
            System.out.println("[12] - Загрузить резервную копию");
            System.out.println("[0] - Выход из программы");
            System.out.println(Color.RESET);
            do {
                System.out.print("Введите номер функции: ");
                function = CheckCorrectFunction(in.nextLine());
            } while (function == -1);

            switch (function) {
                case 1:
                    System.out.println(exRes);
                    break;
                case 2:
                    System.out.println("\nСтуденты отличники: ");
                    exRes.IsExcellent();
                    break;
                case 3:
                    System.out.println("\nСтуденты двоечники: ");
                    exRes.IsBadStudent();
                    break;
                case 4:
                    exRes.SubjectWithBadMark();
                    break;
                case 5:
                    exRes.RemoveBadStudents();
                    break;
                case 6:
                    System.out.print("\nВведите путь для сохранения файла:");
                    String saveFileName = in.nextLine();
                    exRes.saveFile(saveFileName);
                    break;
                case 7:
                    System.out.print("\nВведите путь к базе данных:");
                    String loadFileName = in.nextLine();
                    exRes.loadFile(loadFileName);
                    break;
                case 8:
                    System.out.print("\nВведите путь для сохранения файла:");
                    String serializeFileName = in.nextLine();
                    exRes.serializeFile(serializeFileName);
                    break;
                case 9:
                    System.out.print("\nВведите путь к базе данных:");
                    String deserializeFileName = in.nextLine();
                    exRes.deserializeFile(deserializeFileName);
                    break;
                case 10:
                    System.out.print("\nВведите путь для сохранения файла:");
                    String jacksonSerializeFileName = in.nextLine();
                    exRes.JacksonSerializeFile(jacksonSerializeFileName);
                    break;
                case 11:
                    System.out.print("\nВведите путь к базе данных:");
                    String jacksonDeserializeFileName = in.nextLine();
                    exRes.jacksonDeserializeFile(jacksonDeserializeFileName);
                    break;
                case 12:
                    FileWork name = new FileWork();
                    exRes.loadFile(name.lastReserveName());
                    break;
                case 0:
                    exRes.saveFile("" + (System.currentTimeMillis() / 1000) + ".backup");
                    break;
                default:
                    System.out.println("\nТакой функции нету");
            }
        } while (function != 0);
    }
}
