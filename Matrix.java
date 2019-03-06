
package Home;

import java.math.*;
import java.util.ArrayList;


public class Matrix {

    public int[][] matrix;
    public ArrayList<Integer> wholeList; //The original 12-tone series
    public ArrayList<Integer> segments; //The way to combine pitches together, each number stands for the number of pitches to play simultaneously.
    public ArrayList<ArrayList<Integer>> melodySoundList; //The 12-tone melody with pause to conduct Son Clave rhythm
    public ArrayList<ArrayList<Integer>> originList;    //The original 12-tone series
    public ArrayList<ArrayList<Integer>> minuetDurationRow; //The minuet tones with pause to conduct duration row rhythm
    public ArrayList<ArrayList<Integer>> minuetTimePoint; //The minuet tones with pause to conduct time point rhythm
    public ArrayList<ArrayList<Integer>> timePointList; //The original 12-tone series with time point rhythm
    public ArrayList<ArrayList<Integer>> minuet; //The tones of Bach's Minuet
    public ArrayList<int[]> minuetDurations; //The duration row list for Minuet
    public int[] minuetDurationArray; //The array of all duration values for Minuet
    public ArrayList<int[]> durations; //The duration row system duration list
    public ArrayList<Integer> minuetTPDurations; // The time point system duration list for Minuet
    public ArrayList<Integer> tpDurations; // The time point system duration list
    public ArrayList<int[]> minuetVolumes; //The ArrayList of all volume values for Minuet
    public int[] minuetAllVolume; //The array of all volume values for Minuet
    public ArrayList<int[]> volumes; //The ArrayList of all volume values
    public int[] allVolume; //The array of all volume values
    public int[] trithm;    //The user input rhythm array
    public int[] triVolume;// The user input volume array

    public Matrix(int[] list) {

        this.matrix = new int[12][12];
        matrix[0] = list;
        for (int i = 1; i < 12; i++) {
            this.matrix[i][0] = 12 - this.matrix[0][i];
        }
        for (int y = 1; y < 12; y++) {
            for (int x = 1; x < 12; x++) {
                if (this.matrix[y][0] + this.matrix[0][x] < 12) {
                    this.matrix[y][x] = this.matrix[y][0] + this.matrix[0][x];
                }
                if (this.matrix[y][0] + this.matrix[0][x] >= 12) {
                    this.matrix[y][x] = this.matrix[y][0] + this.matrix[0][x] - 12;
                }
            }
        }

    }

    public void setTrithm(int a, int b, int c) {
        trithm = new int[3];
        trithm[0] = a;
        trithm[1] = b;
        trithm[2] = c;
    }

    public void setTriVol(int a, int b, int c) {
        triVolume = new int[3];
        triVolume[0] = a;
        triVolume[1] = b;
        triVolume[2] = c;
    }

    public String Melody_Generation(String s) {
        //Randomly choose a row and a column
        int r = (int) (Math.random() * 11 + 1);
        int c = (int) (Math.random() * 11 + 1);

        //Generate array for Prime, Retrograde, Inversion and Retrograde Inversion
        int[] prime = this.matrix[r];
        int[] inver = new int[12];
        for (int i = 0; i < 12; i++) {
            inver[i] = this.matrix[i][c];
        }
        int[] retro = new int[12];
        for (int i = 0; i < 12; i++) {
            retro[i] = prime[11 - i];
        }
        int[] reIn = new int[12];
        for (int i = 0; i < 12; i++) {
            reIn[i] = inver[11 - i];
        }

        //Print Prime, Retrograde, Inversion and Retrograde Inversion
        s += "Prime:";
        for (int i = 0; i < 12; i++) {
            s += prime[i] + " ";
        }
        s += "\n";
        s += "Retrograde:";
        for (int i = 0; i < retro.length; i++) {
            s += retro[i] + " ";
        }
        s += "\n";
        s += "Inversion:";
        for (int i = 0; i < 12; i++) {
            s += inver[i] + " ";
        }
        s += "\n";
        s += "Retrograde Inversion:";
        for (int i = 0; i < reIn.length; i++) {
            s += reIn[i] + " ";
        }
        s += "\n";

        //The whole list of the tone series
        wholeList = new ArrayList<Integer>();
        for (int n = 0; n < 4; n++) {
            for (int i = 0; i < 12; i++) {
                int octave = 2 + (int) (Math.random() * 5);
                wholeList.add(prime[i] + 12 * octave);
            }
            for (int i = 0; i < 12; i++) {
                int octave = 2 + (int) (Math.random() * 5);
                wholeList.add(inver[i] + 12 * octave);
            }
            for (int i = 0; i < 12; i++) {
                int octave = 2 + (int) (Math.random() * 5);
                wholeList.add(retro[i] + 12 * octave);
            }
            for (int i = 0; i < 12; i++) {
                int octave = 2 + (int) (Math.random() * 5);
                wholeList.add(reIn[i] + 12 * octave);
            }
        }

        s += "Whole Tune Series: \n";
        for (int i = 0; i < wholeList.size(); i++) {
            s += wholeList.get(i) + " ";
        }
        s += "\n";

        //Divide prime, inver, retro, reIn into segments
        segments = new ArrayList<Integer>();
        int restTone = 12;
        for (int i = 0; i < 16; i++) {
            while (restTone > 0) {
                Integer x = (int) ((Math.random() * 4) + 1); //random number between 1 to 4
                restTone = restTone - x;
                if (restTone >= 0) {
                    segments.add(x);
                } else {
                    restTone = restTone + x;
                }
            }
            restTone = 12;
        }

        s += "Tune segments: [";
        for (int i = 0; i < segments.size(); i++) {
            s += segments.get(i) + " ";
        }
        s += "]\n";

        //Construct a list of sounds each of which is a list of tones
        melodySoundList = new ArrayList<ArrayList<Integer>>();
        originList = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> tempList = new ArrayList<Integer>();
        for (int i = 0; i < wholeList.size(); i++) {
            tempList.add(wholeList.get(i));
        }
        for (int i = 0; i < segments.size(); i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < segments.get(i); j++) {
                list.add(tempList.get(j));
            }
            for (int q = 0; q < segments.get(i); q++) {
                tempList.remove(0);
            }
            melodySoundList.add(list);
            originList.add(list);
        }

        //Apply Son Clave (3122224) to the series in melodySoundList
        /*Add pause as empty ArrayList[] to melodySoundList. The pause in the Son Clave is in the 3rd,
		 *  5th, 10th, 12th, 17th...positions. They divide the sound as segments with 2,1,4,1,
		 *  4,1,4...sounds.*/
        ArrayList<Integer> list0 = new ArrayList<Integer>();
        melodySoundList.add(2, list0);
        int p = 3;	//The current position
        int count = 0; //If count is odd,the next pause position will jump 2 indexes, otherwise 5.
        while (p < melodySoundList.size()) {
            count++;
            if (count % 2 == 1) {
                p += 2;
            } else {
                p += 5;
            }
            if (p <= melodySoundList.size()) {
                melodySoundList.add(p - 1, list0);
            }

        }

        s += "Final Sound List:\n [";
        for (int i = 0; i < melodySoundList.size(); i++) {
            s += "[";
            for (int j = 0; j < melodySoundList.get(i).size(); j++) {
                s += melodySoundList.get(i).get(j) + " ";
            }
            s += "] ";
        }
        s += "]";

        return s;
    }

    //Generate Minuet
    public void generateMinuet() {
        minuet = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> c = new ArrayList<Integer>();
        c.add(72);
        ArrayList<Integer> d = new ArrayList<Integer>();
        d.add(74);
        ArrayList<Integer> e = new ArrayList<Integer>();
        e.add(76);
        ArrayList<Integer> f = new ArrayList<Integer>();
        f.add(77);
        ArrayList<Integer> g = new ArrayList<Integer>();
        g.add(79);
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(81);
        ArrayList<Integer> b = new ArrayList<Integer>();
        b.add(83);
        ArrayList<Integer> c_up = new ArrayList<Integer>();
        c_up.add(84);
        ArrayList<Integer> b_down = new ArrayList<Integer>();
        b_down.add(71);
        minuet.add(g);
        minuet.add(c);
        minuet.add(d);
        minuet.add(e);
        minuet.add(f);
        minuet.add(g);
        minuet.add(c);
        minuet.add(c);
        minuet.add(a);
        minuet.add(f);
        minuet.add(g);
        minuet.add(a);
        minuet.add(b);
        minuet.add(c_up);
        minuet.add(c);
        minuet.add(c);
        minuet.add(f);
        minuet.add(g);
        minuet.add(f);
        minuet.add(e);
        minuet.add(d);
        minuet.add(e);
        minuet.add(f);
        minuet.add(e);
        minuet.add(d);
        minuet.add(c);
        minuet.add(b_down);
        minuet.add(c);
        minuet.add(d);
        minuet.add(e);
        minuet.add(c);
        minuet.add(d);
        minuet.add(g);
        minuet.add(c);
        minuet.add(d);
        minuet.add(e);
        minuet.add(f);
        minuet.add(g);
        minuet.add(c);
        minuet.add(c);
        minuet.add(a);
        minuet.add(f);
        minuet.add(g);
        minuet.add(a);
        minuet.add(b);
        minuet.add(c_up);
        minuet.add(c);
        minuet.add(c);
        minuet.add(f);
        minuet.add(g);
        minuet.add(f);
        minuet.add(e);
        minuet.add(d);
        minuet.add(e);
        minuet.add(f);
        minuet.add(e);
        minuet.add(d);
        minuet.add(c);
        minuet.add(d);
        minuet.add(e);
        minuet.add(d);
        minuet.add(c);
        minuet.add(b_down);
        minuet.add(c);
    }

     /**
     * Construct minuet duration list
     */
    public void constructMinuetDurations() {
        minuetDurations = new ArrayList<int[]>();
        generateMinuet();
        int lastDur = 0;
        //for (int i = 0; i < Math.ceil(originList.size() / 3); i++) {
        for(int i=0; i< Math.ceil(minuet.size()/3); i++) {
            int[] tri = new int[3];
            tri[0] = trithm[0];
            tri[1] = trithm[1];
            tri[2] = trithm[2];
            if (((trithm[0] + trithm[1] + trithm[2]) % 12) != 0) { //To fill the 12-unit duration of a bar
                int[] tri2 = new int[4];
                tri2[0] = trithm[0];
                tri2[1] = trithm[1];
                tri2[2] = trithm[2];
                tri2[3] = 12 - ((trithm[0] + trithm[1] + trithm[2]) % 12);
                minuetDurations.add(tri2);
            } else {
                minuetDurations.add(tri);
            }

            for (int j = 0; j <= 2; j++) {
                trithm[j] = trithm[j] + 3;
                if (trithm[j] > 12) {
                    trithm[j] = trithm[j] - 12;
                }
            }
            if(i == Math.ceil(minuet.size()/3)-1){
                lastDur = trithm[0];
            }
        }

        int count = 0;
        //Print the Duration List
        System.out.print("Minuet Durations List: [");
        for (int i = 0; i < minuetDurations.size(); i++) {
            System.out.print("[ ");
            for (int j = 0; j < minuetDurations.get(i).length; j++) {
                System.out.print(minuetDurations.get(i)[j] + " ");
                count++;
            }
            System.out.print("] ");
        }
        System.out.println(" ]");

        minuetDurationArray = new int[count+1];
        int index = 0;
        for(int i = 0; i< minuetDurations.size(); i++){
            for(int j = 0; j < minuetDurations.get(i).length; j++){
                minuetDurationArray[index] = minuetDurations.get(i)[j];
                index++;
            }
        }
        minuetDurationArray[count] = lastDur;
    }

    /**
     * Construct original duration list
     */
    public void constructOriginDurations() {
        durations = new ArrayList<int[]>();
        for (int i = 0; i < Math.ceil(originList.size() / 3); i++) {
            int[] tri = new int[3];
            tri[0] = trithm[0];
            tri[1] = trithm[1];
            tri[2] = trithm[2];
            if (((trithm[0] + trithm[1] + trithm[2]) % 12) != 0) { //To fill the 12-unit duration of a bar
                int[] tri2 = new int[4];
                tri2[0] = trithm[0];
                tri2[1] = trithm[1];
                tri2[2] = trithm[2];
                tri2[3] = 12 - ((trithm[0] + trithm[1] + trithm[2]) % 12);
                durations.add(tri2);
            } else {
                durations.add(tri);
            }

            for (int j = 0; j <= 2; j++) {
                trithm[j] = trithm[j] + 3;
                if (trithm[j] > 12) {
                    trithm[j] = trithm[j] - 12;
                }
            }
        }

        //Print the Duration List
        System.out.print("Durations List: [");
        for (int i = 0; i < durations.size(); i++) {
            System.out.print("[ ");
            for (int j = 0; j < durations.get(i).length; j++) {
                System.out.print(durations.get(i)[j] + " ");
            }
            System.out.print("] ");
        }
        System.out.println(" ]");

    }

    /**
     * Construct the durations list
     */
    public String Duration_Row(String s) {
        constructMinuetDurations();
        //Generate durationRowList
        minuetDurationRow = new ArrayList<ArrayList<Integer>>();

        //for (int i = 0; i < originList.size(); i++) {
        //    durationRowList.add(originList.get(i));
        //}
        for (int i = 0; i < minuet.size(); i++) {
            minuetDurationRow.add(minuet.get(i));
        }
        int size = minuetDurations.size();
        int index = 0;
        ArrayList<Integer> pause = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            if (minuetDurations.get(i).length == 4) {
                index += 3;
                if (index <= minuetDurationRow.size() + 1) {
                    minuetDurationRow.add(index, pause);
                    index++;
                }
            } else {
                index += 3;
            }
        }

        s += "\nMinuet Duration Row List: \n";
        for (int i = 0; i < minuetDurationRow.size(); i++) {
            s += "[";
            if(minuetDurationRow.get(i).size()>0){
                s += minuetDurationRow.get(i).get(0);
            }else{
                s += " ";
            }
            s += "] ";
        }
        s += "\n";
        s += "The size of Minuet Duration Row List: "+ minuetDurationRow.size()+"\n";
        s += "The size of Minuet Duration Array: "+ minuetDurationArray.length+"\n";
        s += "Minuet Duration Array: \n";
        for(int i =0; i< minuetDurationArray.length; i++){
            s += minuetDurationArray[i]+ " ";
        }
        s += "\nMinuet Durations ArrayList: \n[";
         for (int i = 0; i < minuetDurations.size(); i++) {
            s += "[ ";
            for (int j = 0; j < minuetDurations.get(i).length; j++) {
                s += minuetDurations.get(i)[j] + " ";
            }
            s += "] ";
        }
        s += " ]";
        return s;
    }

    /**
     * *
     * Construct the duration list and for time point method for Minuet
     */
    public String timePointMinuet(String s) {
        constructMinuetDurations();
        minuetTimePoint = new ArrayList<ArrayList<Integer>>();
        minuetTPDurations = new ArrayList<Integer>();
        int count = minuetDurations.size() * 4;
        int[] durationArr = new int[count]; //Change ArrayList<int[]> durations into array
        int index = 0;
        for (int i = 0; i < minuetDurations.size(); i++) {
            for (int j = 0; j < minuetDurations.get(i).length; j++) {
                durationArr[index] = minuetDurations.get(i)[j];
                index++;
            }
        }
        for (int i = 0; i < count; i++) {
            System.out.print(durationArr[i] + " ");
        }
        System.out.println();

        ArrayList<Integer> pause = new ArrayList<Integer>();

        int durIndex = 0;

        ArrayList<ArrayList<Integer>> tempList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < minuet.size(); i++) {
            tempList.add(minuet.get(i));
        }

        while (!tempList.isEmpty()) {
            if (durationArr[durIndex] == 12) {
                minuetTPDurations.add(12);
                 minuetTimePoint.add(pause);
            } else if (durIndex == 0) {
                minuetTPDurations.add(durationArr[durIndex]);
                 minuetTimePoint.add(pause);
            } else if (durationArr[durIndex + 1] <= durationArr[durIndex]) {
                if (durationArr[durIndex] <= durationArr[durIndex - 1]) {
                    minuetTPDurations.add(durationArr[durIndex]);
                     minuetTimePoint.add(pause);
                    int temp = 12 - durationArr[durIndex];
                    minuetTPDurations.add(temp);
                     minuetTimePoint.add(tempList.get(0));
                    tempList.remove(0);
                } else {
                    int temp1 = durationArr[durIndex] - durationArr[durIndex - 1];
                    minuetTPDurations.add(temp1);
                     minuetTimePoint.add(tempList.get(0));
                    tempList.remove(0);
                    if (!tempList.isEmpty()) {
                        int temp2 = 12 - durationArr[durIndex];
                        minuetTPDurations.add(temp2);
                         minuetTimePoint.add(tempList.get(0));
                        tempList.remove(0);
                    }
                }
            } else if (durationArr[durIndex + 1] > durationArr[durIndex]) {
                if (durationArr[durIndex] <= durationArr[durIndex - 1]) {
                    minuetTPDurations.add(durationArr[durIndex]);
                     minuetTimePoint.add(pause);
                    int temp = durationArr[durIndex + 1] - durationArr[durIndex];
                    minuetTPDurations.add(temp);
                     minuetTimePoint.add(tempList.get(0));
                    tempList.remove(0);
                } else {
                    int temp1 = durationArr[durIndex] - durationArr[durIndex - 1];
                    minuetTPDurations.add(temp1);
                    minuetTimePoint.add(tempList.get(0));
                    tempList.remove(0);
                    if (!tempList.isEmpty()) {
                        int temp2 = durationArr[durIndex + 1] - durationArr[durIndex];
                        minuetTPDurations.add(temp2);
                         minuetTimePoint.add(tempList.get(0));
                        tempList.remove(0);
                    }
                }
            }
            durIndex++;
        }

        s += "\n Minuet Timt Point Durations: [";
        for (int i = 0; i < minuetTPDurations.size(); i++) {
            s += minuetTPDurations.get(i) + " ";
        }
        s += "]\n";
       // s += "Size of Minuet Timt Point Durations:" + minuetTPDurations.size() + "\n";

        s += " Minuet Time Point List:\n";
        for (int i = 0; i <  minuetTimePoint.size(); i++) {
            s += "[";
            for (int j = 0; j <  minuetTimePoint.get(i).size(); j++) {
                s +=  minuetTimePoint.get(i).get(j) + " ";
            }
            s += "]\n";
        }
       // s += "Size of minuetTimePointList: " +  minuetTimePoint.size();

        return s;
    }

     /**
     * *
     * Construct the duration list and for time point method for 12-tone Melody
     */
    public String timePointTwelveTone(String s) {
        constructOriginDurations();
        timePointList = new ArrayList<ArrayList<Integer>>();
        tpDurations = new ArrayList<Integer>();
        int count = durations.size() * 4;
        int[] durationArr = new int[count]; //Change ArrayList<int[]> durations into array
        int index = 0;
        for (int i = 0; i < durations.size(); i++) {
            for (int j = 0; j < durations.get(i).length; j++) {
                durationArr[index] = durations.get(i)[j];
                index++;
            }
        }
        for (int i = 0; i < count; i++) {
            System.out.print(durationArr[i] + " ");
        }
        System.out.println();

        ArrayList<Integer> pause = new ArrayList<Integer>();

        int durIndex = 0;

        ArrayList<ArrayList<Integer>> tempList = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < originList.size(); i++) {
            tempList.add(originList.get(i));
        }

        while (!tempList.isEmpty()) {
            if (durationArr[durIndex] == 12) {
                tpDurations.add(12);
                timePointList.add(pause);
            } else if (durIndex == 0) {
                tpDurations.add(durationArr[durIndex]);
                timePointList.add(pause);
            } else if (durationArr[durIndex + 1] <= durationArr[durIndex]) {
                if (durationArr[durIndex] <= durationArr[durIndex - 1]) {
                    tpDurations.add(durationArr[durIndex]);
                    timePointList.add(pause);
                    int temp = 12 - durationArr[durIndex];
                    tpDurations.add(temp);
                    timePointList.add(tempList.get(0));
                    tempList.remove(0);
                } else {
                    int temp1 = durationArr[durIndex] - durationArr[durIndex - 1];
                    tpDurations.add(temp1);
                    timePointList.add(tempList.get(0));
                    tempList.remove(0);
                    if (!tempList.isEmpty()) {
                        int temp2 = 12 - durationArr[durIndex];
                        tpDurations.add(temp2);
                        timePointList.add(tempList.get(0));
                        tempList.remove(0);
                    }
                }
            } else if (durationArr[durIndex + 1] > durationArr[durIndex]) {
                if (durationArr[durIndex] <= durationArr[durIndex - 1]) {
                    tpDurations.add(durationArr[durIndex]);
                    timePointList.add(pause);
                    int temp = durationArr[durIndex + 1] - durationArr[durIndex];
                    tpDurations.add(temp);
                    timePointList.add(tempList.get(0));
                    tempList.remove(0);
                } else {
                    int temp1 = durationArr[durIndex] - durationArr[durIndex - 1];
                    tpDurations.add(temp1);
                    timePointList.add(tempList.get(0));
                    tempList.remove(0);
                    if (!tempList.isEmpty()) {
                        int temp2 = durationArr[durIndex + 1] - durationArr[durIndex];
                        tpDurations.add(temp2);
                        timePointList.add(tempList.get(0));
                        tempList.remove(0);
                    }
                }
            }
            durIndex++;
        }

        s += "\n tpDurations: [";
        for (int i = 0; i < tpDurations.size(); i++) {
            s += tpDurations.get(i) + " ";
        }
        s += "]\n";
        s += "Size of tpDurations:" + tpDurations.size() + "\n";

        s += "timePointList:\n";
        for (int i = 0; i < timePointList.size(); i++) {
            s += "[";
            for (int j = 0; j < timePointList.get(i).size(); j++) {
                s += timePointList.get(i).get(j) + " ";
            }
            s += "]\n";
        }
        s += "Size of timePointList: " + timePointList.size();

        return s;
    }
    
    /**
     * Construct volumes list for minuet
     */
    public String constructMinuetVolume(String s) {
        minuetVolumes = new ArrayList<int[]>();
        generateMinuet();
        int lastVol = 0;
        for (int i = 0; i < Math.ceil(minuet.size() / 3); i++) {
            int[] tri = new int[3];
            tri[0] = triVolume[0];
            tri[1] = triVolume[1];
            tri[2] = triVolume[2];
            minuetVolumes.add(tri);

            for (int j = 0; j <= 2; j++) {
                triVolume[j] = triVolume[j] + 3;
                if (triVolume[j] >= 12) {
                    triVolume[j] = triVolume[j] - 12;
                }
            }
          if(i==Math.ceil(minuet.size() / 3)-1){
              lastVol = triVolume[0];
          }  
        }

        int count = minuetVolumes.size() * 3;
        minuetAllVolume = new int[count+1]; //Change ArrayList<int[]> durations into array
        int index = 0;
        for (int i = 0; i < minuetVolumes.size(); i++) {
            for (int j = 0; j < minuetVolumes.get(i).length; j++) {
                minuetAllVolume[index] = minuetVolumes.get(i)[j];
                index++;
            }
        }
        minuetAllVolume[count] = lastVol;
        //Print all Volume
        s += "\nVolume: \n";
        for (int i = 0; i < count; i++) {
            s += minuetAllVolume[i] + " ";
        }
        s += "\n";
        
        s+= "Length of allVolume: "+minuetAllVolume.length;
        s+= "Size of minuet: "+minuet.size();
        return s;
    }
    
    /**
     * Construct volumes list for 12-tone melody with time point rhythm
     */
    public String constructMelodyTimePointVolume(String s) {
        volumes = new ArrayList<int[]>();
        for (int i = 0; i < Math.ceil(timePointList.size() / 3); i++) {
            int[] tri = new int[3];
            tri[0] = triVolume[0];
            tri[1] = triVolume[1];
            tri[2] = triVolume[2];
            volumes.add(tri);

            for (int j = 0; j <= 2; j++) {
                triVolume[j] = triVolume[j] + 3;
                if (triVolume[j] >= 12) {
                    triVolume[j] = triVolume[j] - 12;
                }
            }
        }

        int count = volumes.size() * 3;
        allVolume = new int[count]; //Change ArrayList<int[]> volumes into array
        int index = 0;
        for (int i = 0; i < volumes.size(); i++) {
            for (int j = 0; j < volumes.get(i).length; j++) {
                allVolume[index] = volumes.get(i)[j];
                index++;
            }
        }
        //Print all Volume
        s += "\nVolume: \n";
        for (int i = 0; i < count; i++) {
            s += allVolume[i] + " ";
        }
        s += "\n";
        
        s+= "Length of allVolume: "+allVolume.length;
        s+= "Size of time point list: "+ timePointList.size();
        return s;
    }
}
