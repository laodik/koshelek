import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

    public static void JsonJoinFunc (String MainJson, String SecondaryJson) throws FileNotFoundException, IOException, ParseException{

        //объявление необходимых экземпляров JSONObject и считывание данных из ранее указанных файлов (в одной директории с программой)
        JSONParser parser = new JSONParser();
        JSONObject ResObj=new JSONObject();
        JSONObject jsonMainO = (JSONObject) parser.parse(new FileReader(MainJson));
        JSONObject jsonSecO = (JSONObject) parser.parse(new FileReader(SecondaryJson));
        //объявление идентификатора необходимости изменений в результирующем файле
        Integer i;

        //поиск совпадений ключей в JSON файлах
        for (Object keyMain : jsonMainO.keySet()) {
            i = 0;
            for (Object keySec : jsonSecO.keySet()) {
                String keyStrM = (String)keyMain;
                String keyStrS = (String)keySec;

                //найдено совпадение
                if (keyStrM.equals(keyStrS)) {

                    //выведение данных из json в строку для дальнейшей редактуры
                    Object o = jsonMainO.get(keyMain);
                    String str = o.toString();
                    o = jsonSecO.get(keySec);
                    String str2 = o.toString();

                    //создание новой объединенной строки
                    str="{\""+keyStrM+"\":{"+str.substring(1, str.length()-1)+"," + str2.substring(1, str2.length()-1)+"}}";

                    //создание промежуточного JSON файла с данными из строки str
                    JSONObject SubJsonObj= (JSONObject) parser.parse(str);

                    //вставка объединенных значений в результирующий файл
                    ResObj.put(keyMain,SubJsonObj.get(keyMain));
                    i=1;
                }
            }

            //вставка неизменненных строк в результирующий файл
            if (i==0) {ResObj.put(keyMain,jsonMainO.get(keyMain));}
        }


        //запись полученного результата в файл
        FileWriter file = new FileWriter("result.json");
        file.write(ResObj.toJSONString());
        file.flush();
        file.close();

    }

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException{
        //Считываение имен файлов
        Scanner in = new Scanner(System.in);
        System.out.println("Наименование основного файла");
        String MainJson = in.nextLine();
        System.out.println("Наименование дополнительного файла");
        String SecondaryJson = in.nextLine();
        //обращаемся к функции для объединения JSON файлов
        JsonJoinFunc (MainJson, SecondaryJson);
        System.out.printf("Итоговое значение записано в строку result.json \n");
    }

}
