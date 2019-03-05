import java.io.*;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;

class Main
{
    private static Integer currentIndex=2;
    private static String endPathStatus= "In progress";
    private static Integer arrayLineSize;
    private static Integer arrayColumnSize;

    static  ArrayList<String> getMap() throws FileNotFoundException{
        ArrayList<String> myMap = new ArrayList<String>();
        FileReader mapFile = new FileReader( "map.txt" );
        Scanner mapScanner = new Scanner(mapFile);
        while (mapScanner.hasNext()) {
            myMap.add(mapScanner.nextLine());
        }
        mapScanner.close();
        return (myMap);
    }

    static String getFieldStepNumber (String CheckingPosition) {
        switch(CheckingPosition) {
            case "-2":
                CheckingPosition = Integer.toString(currentIndex);
                endPathStatus = "Finish found!";
                break;
            case "0":
                CheckingPosition = Integer.toString(currentIndex);
                break;
        }
        return (CheckingPosition);
    }

    static String [][] findWay (String [][] Map) {
        arrayLineSize = Map.length;
        arrayColumnSize = Map[0].length;
        while (endPathStatus=="In progress") {
            for (int i = 0; i < arrayLineSize;i++) {
                for (int j = 0; j < arrayColumnSize; j++) {
                    if (Map[i][j].equals(Integer.toString(currentIndex-1))) {
                        if ((i-1)>=0) {
                            Map[i - 1][j] = getFieldStepNumber(Map[i - 1][j]);
                        }
                        if ((i+1)<Map.length) {
                            Map[i + 1][j] = getFieldStepNumber(Map[i + 1][j]);
                        }
                        if ((j-1)>=0) {
                            Map[i][j - 1] = getFieldStepNumber(Map[i][j - 1]);
                        }
                        if ((j+1)<Map[i].length) {
                            Map[i][j + 1] = getFieldStepNumber(Map[i][j + 1]);
                        }
                    }
                }
            }
            if (currentIndex>=64) endPathStatus="There is no way out!";
            else currentIndex++;
        }
        return (Map);
    }

    public static void main(String [] args) throws FileNotFoundException {
        ArrayList<String> myMap = getMap();
        arrayLineSize = myMap.size();
        arrayColumnSize = myMap.get(0).toString().length()/2;
        String myMapArray [][] = new String [arrayLineSize][arrayColumnSize];
        for (int i = 0; i < arrayLineSize;i++) {
            myMapArray[i] = myMap.get(i).toString().split(" ");
        }
        String [][] resultPath = findWay(myMapArray);
        for (int x = 0; x < arrayLineSize;x++) {
            for (int y = 0; y < arrayColumnSize; y++) {
                System.out.print(resultPath[x][y]+" ");
            }
            System.out.println("");
        }
        System.out.println("Минимальное число шагов - "+(currentIndex-1));
    }
}
