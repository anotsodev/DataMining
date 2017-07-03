//package dumps;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.*;
//
//public class NaiveBayesTextClassifier {
//    public static void main(String[] args) {
//
//        try {
//            /* Initialize variables */
//            String unlabeledDatasetFile = "UnlabeledDatasetReview.csv";
//            String trainingDatasetFile = "Reviews.csv";
//            List<String> wordList = new ArrayList<String>();
//             /* Read the csv files */
//            BufferedReader trainingDataset = new BufferedReader(new FileReader(trainingDatasetFile));
//            BufferedReader unlabeledDataset = new BufferedReader(new FileReader(unlabeledDatasetFile));
//            Set<String> arrayOfClass = new HashSet<>();
//            ArrayList<String> arrayOfWords = new ArrayList<>();
//            String line = "";
//            int rows = 0;
//            /* Loop and add the content of the csv file to wordList array list*/
//            while((line = trainingDataset.readLine()) != null) {
//                String[] text = line.split(",");
//                String[] words = text[0].split(" ");
//                rows+=1;
//                String classification = text[1];
//                arrayOfClass.add(classification);
//                /* Put the words inside the wordList */
//                for(String word:words) {
////                    <row_no>_<word>_<classification>
//                    arrayOfWords.add(word);
//                    wordList.add(String.valueOf(rows)+"_"+word+"_"+classification);
//                    //System.out.println(String.valueOf(rows)+"_"+word+"_"+classification);
//                }
//
//            } //end of while
//
//            /* Initialization of list of unique words with their respective class */
//            Set<String> uniqueWords = new HashSet<>();
//            List<String> uniqueWordsToCount = new ArrayList<>();
//            HashMap<Integer, String> textList = new HashMap<>();
//            ArrayList<String> uniqueWordsWithClass = new ArrayList<>();
//            List<String> uniqueClassList = new ArrayList<>();
//            List<String> wordListWithClass = new ArrayList<>();
//            /*Get the words inside the uniqueWords Set */
//            for(String word:wordList) {
//                String[] words = word.split("_");
//                String x = words[0];
//                String y = words[1];
//                String z = words[2];
//                /* then add the word and its class to array list */
//                textList.put(Integer.valueOf(x), z);
//                uniqueWords.add(y);
//                uniqueWordsWithClass.add(y+"_"+z);
//                //wordListWithClass.add(y+"_"+z);
//                uniqueWordsToCount.add(y);
//            }
//            for(String forArrayClass:arrayOfClass){
//                for(String word:uniqueWords) {
//                    //System.out.println(word);
//                    wordListWithClass.add(word+"_"+forArrayClass);
//                    //System.out.println(word+"_"+forArrayClass);
//                }
//            }
//
//
//            //Set<String> uniqueWordListWithClass = new HashSet<>(wordListWithClass);
//            /* HashMap for row/doc and its class [<ROW> <CLASS>] */
//            for(Map.Entry m:textList.entrySet()) {
//                uniqueClassList.add(String.valueOf(m.getValue()));
//            }
//            /* Initialization for the set of unique classes */
//            Set<String> uniqueClass = new HashSet<String>(uniqueClassList);
//            /* Initialization for the count of each unique classes */
//            HashMap<String, Integer> classCount = new HashMap<>();
//            /* Initialization for the probability of each unique classes */
//            HashMap<String, Double> parentProbability = new HashMap<>();
//            int count = 0;
//
//            for(String uc:uniqueClass) {
//                for(Map.Entry m:textList.entrySet()) {
//                    //System.out.println(m.getKey()+" "+m.getValue());
//                    if(String.valueOf(m.getValue()).equalsIgnoreCase(uc)) {
//                        count++;
//                    }
//                }
//                classCount.put(uc,count);
//                parentProbability.put(uc,((double) count/(double) rows));
//                count = 0;
//            }
//
//
//            for(Map.Entry m:parentProbability.entrySet()) {
//                //System.out.println(m.getKey()+" "+m.getValue());
//            }
//            int frequency = 0;
//            HashMap<String, Integer> frequencyOfClass = new HashMap<>();
//            for(Map.Entry m:classCount.entrySet()) {
//                for(String word:wordListWithClass) {
//
//                    String[] words = word.split("_");
//                    String x = words[0];
//                    String y = words[1];
//                    if(y.equalsIgnoreCase(String.valueOf(m.getKey()))) {
//                        frequency+=Collections.frequency(uniqueWordsWithClass, word);
//                        //System.out.println(Collections.frequency(uniqueWordsWithClass, word));
//                    }
//
//                }
//                //System.out.println("================");
//                //System.out.println(frequency);
//
//                frequencyOfClass.put(String.valueOf(m.getKey()), frequency);
//                frequency = 0;
//            }
//            int vocabulary = 0;
//            for(String word: uniqueWords) {
//                vocabulary+=1;
//                //System.out.println(word);
//            }
//            //System.out.println("++++++++++++");
//            HashMap<String, Double> computedWordWithClass = new HashMap<>();
//            int nk = 0;
//            int nn = 0;
//
//            for(Map.Entry m:frequencyOfClass.entrySet()) {
//
//                for(String word:wordListWithClass) {
//                    String[] words = word.split("_");
//                    String x = words[0];
//                    String y = words[1];
//                    nn = (int) m.getValue();
//                    //System.out.println(n);
//                    nk = (Collections.frequency(uniqueWordsWithClass, word));
//                    //System.out.println(nk);
//                    //System.out.println(word+" "+nk);
//                    if(y.equalsIgnoreCase(String.valueOf(m.getKey()))) {
//                        computedWordWithClass.put((x + "_" + y), ( ((double) nk + 1)/(vocabulary+nn) ) );
//                        //System.out.println(x + "_" + y+( ((double) nk + 1)/(vocabulary+nn) ) );
//                    }
//
//                }
//                //System.out.println("==============");
//                //System.out.println(nn);
//
//            }
//
//
//            /* Compute the Unlabeled Dataset */
//
//            while((line = unlabeledDataset.readLine()) != null) {
//                //System.out.println(line);
//                String[] words = line.split(" ");
//                double res = 0.0;
//                double temp = 0.0;
//                for(Map.Entry m:frequencyOfClass.entrySet()) {
//                    //System.out.println(m.getKey());
//                    for(Map.Entry n:computedWordWithClass.entrySet()) {
//                        String computedWords = String.valueOf(n.getKey());
//                        String[] arrayOfComputedWords = computedWords.split("_");
//                        for(String word:words) {
//                            if(word.equalsIgnoreCase(arrayOfComputedWords[0]) && arrayOfComputedWords[1].equalsIgnoreCase(String.valueOf(m.getKey()))) {
//
//                                temp = (double) n.getValue();
//                                if(res == 0) {
//                                    res = temp;
//                                }else {
//                                    res *= temp;
//                                }
//                            }else {
//
//                            }
//                        }
//                    }
//                    res = res*parentProbability.get(m.getKey());
//                    System.out.println(parentProbability.get(m.getKey()));
//                    System.out.println(res);
//                    res = 0.0;
//                    temp = 0.0;
//                }
//
//            }
//
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
