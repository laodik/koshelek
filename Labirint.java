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
    private static Long startTime;

    static  ArrayList<String> getMap() throws IOException {
        ArrayList<String> myMap = new ArrayList<String>();
        FileReader mapFile = new FileReader( "map.txt" );
        Scanner mapScanner = new Scanner(mapFile);
        while (mapScanner.hasNext()) {
            myMap.add(mapScanner.nextLine());
        }
        mapScanner.close();
        mapFile.close();
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

    static void resultsToFile (String map [][]) throws IOException {
        Integer stepNumber=1;
        int x=0;
        int finishX=-1;
        int finishY=-1;
        while (x < arrayLineSize) {
            for (int y = 0; y < arrayColumnSize; y++) {
                if (map[x][y].equals(Integer.toString(stepNumber))) {
                    if (stepNumber==1) map[x][y] = "S";
                    else map[x][y] = "X";
                    finishX=x;
                    finishY=y;
                    stepNumber++;
                    x=-1;
                    y=0;
                    break;
                }
            }
            x++;
        }
        if (finishX!=-1) map[finishX][finishY] = "F";
        //Cleaning the map field.
        for (x = 0; x < arrayLineSize;x++) {
            for (int y = 0; y < arrayColumnSize; y++) {
                if ((map[x][y]!="-1")&&(map[x][y]!="X")&&(map[x][y]!="S")&&(map[x][y]!="F")) {
                    map[x][y]="0";
                }
            }
        }
        recordingFile (map);
    }

    static void recordingFile (String map [][]) throws IOException {
        FileWriter resultMapFile = new FileWriter("mapResult.txt");
        for (int x = 0; x < arrayLineSize;x++) {
            for (int y = 0; y < arrayColumnSize; y++)
                resultMapFile.write(map[x][y] + " ");
            resultMapFile.write("\r\n");
        }
        resultMapFile.write("\r\n");
        resultMapFile.write("Минимальное число шагов - "+(currentIndex-1));
        Long stopTime = System.currentTimeMillis();
        Long duration = stopTime-startTime;
        resultMapFile.write("\r\n");
        resultMapFile.write("Время выполнения программы - "+duration+"мс");
        resultMapFile.close();
    }


    public static void main(String [] args) throws IOException {
        startTime = System.currentTimeMillis();
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
        resultsToFile(resultPath);
    }
}
