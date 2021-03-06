/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.application.Application;
//import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
//import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DataMining extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setHgap(5);
        pane.setVgap(5);

        Text welcome = new Text();
        welcome.setText("Please choose one algorithm to display");
        final TextField uniqueTokenField = new TextField();
        Label uniqueTokenLabel = new Label();
        Label uniqueTokenLabel2 = new Label();
        uniqueTokenField.setPrefColumnCount(2);
        uniqueTokenField.setText("0.0001");
        uniqueTokenLabel.setWrapText(true);
        uniqueTokenLabel.setText("Please enter the minimum value for unique tokens");
        uniqueTokenLabel2.setText("(For Naive Bayes Classifier)");

        Button button1 = new Button();
        button1.setText("Linear Regression");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ObservableList<LinearRegression> data = FXCollections.observableArrayList();
                GridPane linearPane = new GridPane();
                linearPane.setAlignment(Pos.CENTER);
                //linearPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
                linearPane.setHgap(5.5);
                linearPane.setVgap(5.5);
                Stage stage1 = new Stage();
                stage1.setTitle("Linear Regression");
                String csvFile = "";
                String csvFile2 = "";
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Training Dataset for Linear Regression");
                File file = fileChooser.showOpenDialog(stage1);
                if(file != null) {
                    csvFile = file.getName();
                }else {
                    csvFile = "blank.txt";
                }
                FileChooser fileChooser2 = new FileChooser();
                fileChooser.setTitle("Open Unlabeled Dataset for Linear Regression");

                File file2 = fileChooser.showOpenDialog(stage1);
                if(file2 != null) {
                    csvFile2 = file2.getName();
                }else {
                    csvFile2 = "blank.txt";
                }
                //"LinearRegressionDS.csv";
                //csvFile2 = "Sample.csv";
                BufferedReader reader1 = null;
                BufferedReader reader2 = null;
                String line;
                String splitCsv = ",";
                double[] x = null;
                double[] y = null;
                double meanX = 0.0;
                double meanY = 0.0;
                int n = 0;
                String toOutput = "";

                try {
                    int row = 0;
                    int checkRowCount = 0;
                    reader1 = new BufferedReader(new FileReader(csvFile));
                    reader2 = new BufferedReader(new FileReader(csvFile2));
                    //toOutput += "=========== Training Dataset =========== \n";
                    //toOutput+="\n";
                    //toOutput += "\tX Value\t\t\t\tY Values\n";
                    //toOutput+="\n";
                    ArrayList<LinearRegression> linearRegressionArray = new ArrayList<>();
                    while((line = reader1.readLine()) != null) {
                        checkRowCount+=1;
                    }
                    x = new double[checkRowCount];
                    y = new double[checkRowCount];
                    reader1 = new BufferedReader(new FileReader(csvFile));
                    while((line = reader1.readLine()) != null) {

                        String[] csvLine = line.split(splitCsv);
                        //toOutput+= "\t\t"+csvLine[0]+"\t\t\t\t\t"+csvLine[1]+"\n";
                        row += 1;
                        x[n] = Double.valueOf(csvLine[0]);
                        y[n] = Double.valueOf(csvLine[1]);

                        linearRegressionArray.add(new LinearRegression(row, String.valueOf(x[n]), String.valueOf(y[n])));

                        n++;
                    }
                    data = FXCollections.observableArrayList(linearRegressionArray);
                    //toOutput += "\n";
                    //toOutput += "\nNumber of Rows: "+x.length;
                    n = 0;
                    double sumX = 0.0;
                    while(n < x.length) {
                        sumX += x[n];
                        n++;
                    }
                    meanX = sumX/x.length;
                    //System.out.println(meanX);
                    n = 0;
                    double sumY = 0.0;
                    while(n < y.length) {
                        sumY += y[n];
                        n++;
                    }
                    meanY = sumY/y.length;
                    //System.out.println(meanY);
                    n = 0;
                    double sumXMeanYMean = 0.0;
                    double sumPowOfDiffMeanX = 0.0;
                    double sumPowOfDiffMeanY = 0.0;
                    while(n < x.length) {
                        sumXMeanYMean += ((x[n]-meanX)*(y[n]-meanY));
                        sumPowOfDiffMeanX += Math.pow((x[n]-meanX), 2);
                        sumPowOfDiffMeanY += Math.pow((y[n]-meanY), 2);
                        n++;
                    }


                    double r = (sumXMeanYMean/Math.sqrt(sumPowOfDiffMeanX*sumPowOfDiffMeanY));


                    double sx = Math.sqrt(sumPowOfDiffMeanX/(x.length-1));
                    double sy = Math.sqrt(sumPowOfDiffMeanY/(y.length-1));


                    double b = r*(sy/sx);
                    double a = meanY - (b*meanX);

                    toOutput+="\n";
                    toOutput+= "=========== Unlabeled Dataset =========== \n";
                    toOutput+="\n";
                    toOutput+= "\tX Values\t\t\t\tPredicted Values\n";
                    toOutput+="\n";
                    int unlabeledRow = 0;
                    while((line = reader2.readLine()) != null) {
                        double prediction = a+(b*Double.valueOf(line));

                        toOutput+= "\t\t"+line+"\t\t\t\t"+prediction+"\n";
                        unlabeledRow++;
                    }
                    toOutput += "\n";
                    toOutput += "\nNumber of Rows: "+unlabeledRow;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                } finally {
                    if (reader1 != null) {
                        try {
                            reader1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


//                Text text1 = new Text(text);
//
//
//
//                linearPane.add(text1, 0,1);

                TableView<LinearRegression> table = new TableView<>();
                table.setMinHeight(400);
                Scene scene = new Scene(new Group());
                stage1.setTitle("Linear Regression");
                stage1.setWidth(720);
                stage1.setHeight(720);

                final Label label = new Label("Linear Regression Algorithm");
                //label.setFont(new Font("Arial", 20));


                TableColumn rowsCol = new TableColumn("Row #");
                rowsCol.setMinWidth(100);
                rowsCol.setCellValueFactory(
                        new PropertyValueFactory<>("row"));

                TableColumn xValue = new TableColumn("X Value");
                xValue.setMinWidth(400);
                xValue.setCellValueFactory(
                        new PropertyValueFactory<>("xValue"));

                TableColumn yValue = new TableColumn("Y Value");
                yValue.setMinWidth(200);
                yValue.setCellValueFactory(
                        new PropertyValueFactory<>("yValue"));

                table.setItems(data);
                table.getColumns().addAll(rowsCol, xValue, yValue);

                Text text1 = new Text(toOutput);
                final HBox hbox = new HBox();
                hbox.setSpacing(5);
                hbox.setPadding(new Insets(10, 0, 0, 10));
                hbox.getChildren().addAll(text1);
                final VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(10, 0, 0, 10));
                vbox.getChildren().addAll(label, table, hbox);


                ((Group) scene.getRoot()).getChildren().addAll(vbox);


                stage1.setScene(scene);
                stage1.show();

//                stage1.setScene(new Scene(linearPane, 500,500));
//                stage1.show();


            }
        });

        Button button2 = new Button();
        button2.setText("Naive Bayes");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double tokenMinValField = Double.valueOf(uniqueTokenField.getText());
                GridPane linearPane = new GridPane();
                linearPane.setAlignment(Pos.CENTER);
                //linearPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
                ObservableList<Reviews> data = FXCollections.observableArrayList();
                linearPane.setHgap(5.5);
                linearPane.setVgap(5.5);
                String toOutput = "";
                String toOutput1 = "";
                Stage stage1 = new Stage();
                stage1.setTitle("Naive Bayes");
                String csvFile = "";
                String csvFile2 = "";
                String csvFile3 = "";
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Training Dataset for Naive Bayes");
                File file = fileChooser.showOpenDialog(stage1);
                if(file != null) {
                    csvFile = file.getName();
                }else {
                    csvFile = "blank.txt";
                }
                FileChooser fileChooser2 = new FileChooser();
                fileChooser.setTitle("Open Unlabeled Dataset for Naive Bayes");

                File file2 = fileChooser.showOpenDialog(stage1);
                if(file2 != null) {
                    csvFile2 = file2.getName();
                }else {
                    csvFile2 = "blank.txt";
                }
                FileChooser fileChooser3 = new FileChooser();
                fileChooser.setTitle("Open Stop Words Text File");

                File file3 = fileChooser.showOpenDialog(stage1);
                if(file3 != null) {
                    csvFile3 = file3.getName();
                }else {
                    csvFile3 = "blank.txt";
                }
                try {
/* Initialize variables */
                    String unlabeledDatasetFile = csvFile2;
                    String trainingDatasetFile = csvFile;
                    ArrayList<String> wordList = new ArrayList<String>();
/* Read the csv files */
                    BufferedReader trainingDataset = new BufferedReader(new FileReader(trainingDatasetFile));
                    BufferedReader unlabeledDataset = new BufferedReader(new FileReader(unlabeledDatasetFile));
                    BufferedReader stopWordsFile = new BufferedReader(new FileReader(csvFile3));

                    Set<String> arrayOfClass = new HashSet<>();
                    ArrayList<String> arrayOfWords = new ArrayList<>();
                    ArrayList<Reviews> reviewsArray = new ArrayList<>();
                    ArrayList<String> stopWords = new ArrayList<>();
                    String line = "";
                    String nLine = "";
                    int rows = 0;
                    /* Add to Stop Words ArrayList */
                    while((nLine = stopWordsFile.readLine()) != null) {
                        stopWords.add(nLine);
                        System.out.println(nLine);
                    }
/* Loop and add the content of the csv file to wordList array list*/
                    while((line = trainingDataset.readLine()) != null) {
                        String[] text = line.split(",");
                        String[] words = text[0].split(" ");
                        rows+=1;
                        reviewsArray.add(new Reviews(rows,text[0],text[1]));
                        String classification = text[1];
                        arrayOfClass.add(classification);
/* Put the words inside the wordList */
                        for(String word:words) {
//                    <row_no>_<word>_<classification>
                            if(!stopWords.contains(word)) {
                                arrayOfWords.add(word);
                                wordList.add(String.valueOf(rows)+"_"+word+"_"+classification);
                                System.out.println(String.valueOf(rows)+"_"+word+"_"+classification);
                            }

                            //System.out.println(String.valueOf(rows)+"_"+word+"_"+classification);
                        }

                    } //end of while
                    data = FXCollections.observableArrayList(reviewsArray);
/* Initialization of list of unique words with their respective class */
                    Set<String> uniqueWords = new HashSet<>();
                    List<String> uniqueWordsToCount = new ArrayList<>();
                    HashMap<Integer, String> textList = new HashMap<>();
                    ArrayList<String> uniqueWordsWithClass = new ArrayList<>();
                    List<String> uniqueClassList = new ArrayList<>();
                    List<String> wordListWithClass = new ArrayList<>();
/*Get the words from wordList */
                    for(String word:wordList) {
                        String[] words = word.split("_");
                        String x = words[0];
                        String y = words[1];
                        String z = words[2];
/* then add the words and its class to set and array list */
/* The Set<String> uniqueWords will contain the unique words retrieved from wordList */
                        textList.put(Integer.valueOf(x), z);
                        uniqueWords.add(y);
                        uniqueWordsWithClass.add(y+"_"+z);
                        //wordListWithClass.add(y+"_"+z);
                        uniqueWordsToCount.add(y);
                    }
/* Concatenate unique words with their respective class <unique_word>_<class> */
                    for(String forArrayClass:arrayOfClass){
                        for(String word:uniqueWords) {
                            //System.out.println(word);
                            wordListWithClass.add(word+"_"+forArrayClass);
                            //System.out.println(word+"_"+forArrayClass);
                        }
                    }


                    //Set<String> uniqueWordListWithClass = new HashSet<>(wordListWithClass);
/* HashMap for row/doc and its class [<ROW> <CLASS>] */
                    for(Map.Entry m:textList.entrySet()) {
                        uniqueClassList.add(String.valueOf(m.getValue()));
                    }
/* Initialization for the set of unique classes */
                    Set<String> uniqueClass = new HashSet<String>(uniqueClassList);
/* Initialization for the count of each unique classes */
                    HashMap<String, Integer> classCount = new HashMap<>();
/* Initialization for the probability of each unique classes */
                    HashMap<String, Double> parentProbability = new HashMap<>();
                    int count = 0;
/* Get the unique class and count the instances */
                    for(String uc:uniqueClass) {
                        for(Map.Entry m:textList.entrySet()) {
                            //System.out.println(m.getKey()+" "+m.getValue());
                            
                            if(String.valueOf(m.getValue()).equalsIgnoreCase(uc)) {
                                count++;
                            }
                        }
                        classCount.put(uc,count);
                        parentProbability.put(uc,((double) count/(double) rows));
                        count = 0;
                    }
/* Get the frequency of each unique words */
                    int frequency = 0;
                    HashMap<String, Integer> frequencyOfClass = new HashMap<>();
                    for(Map.Entry m:classCount.entrySet()) {
                        for(String word:wordListWithClass) {

                            String[] words = word.split("_");
                            String x = words[0];
                            String y = words[1];
                            if(y.equalsIgnoreCase(String.valueOf(m.getKey()))) {
                                frequency+=Collections.frequency(uniqueWordsWithClass, word);
                                //System.out.println(Collections.frequency(uniqueWordsWithClass, word));
                            }

                        }
                        //System.out.println("================");
                        //System.out.println(frequency);

                        frequencyOfClass.put(String.valueOf(m.getKey()), frequency);
                        frequency = 0;
                    }
                    int vocabulary = 0;
                    /* Count the number of words in the vocabulary */
                    for(String word: uniqueWords) {
                        vocabulary+=1;
                        //System.out.println(word);
                    }
                    //System.out.println("++++++++++++");
                    HashMap<String, Double> computedWordWithClass = new HashMap<>();
                    int nk = 0;
                    int nn = 0;
/* Compute the probability of each word with their respective class. Ex. Hate_negative = 0.125
                        then place it to HashMap
*/
                    for(Map.Entry m:frequencyOfClass.entrySet()) {

                        for(String word:wordListWithClass) {
                            String[] words = word.split("_");
                            String x = words[0];
                            String y = words[1];
                            nn = (int) m.getValue();
                            //System.out.println(n);
                            nk = (Collections.frequency(uniqueWordsWithClass, word));
                            //System.out.println(nk);
                            //System.out.println(word+" "+nk);
                            if(y.equalsIgnoreCase(String.valueOf(m.getKey()))) {
                                computedWordWithClass.put((x + "_" + y), ( ((double) nk + 1)/(vocabulary+nn) ) );
                                //System.out.println(x + "_" + y+( ((double) nk + 1)/(vocabulary+nn) ) );
                            }

                        }
                        //System.out.println("==============");
                        //System.out.println(nn);

                    }


/* Compute the Unlabeled Dataset */
                    double tokenMinVal = tokenMinValField;
                    toOutput1+="Minimum value for unique tokens: "+tokenMinVal+"\n\n";
                    toOutput1+="--------------------------------------------------------------------------------\n\n";
                    String tempString = "";
                    String tempString2 = "";
/* Initialization of Set<String> for unique words from dataset */
                    Set<String> uniqueUnlabeledDatasetWords = new HashSet<>();
                    Set<String> uniqueUnlabeledDatasetWords2 = new HashSet<>();
                    HashMap<String, Double> highestProbabilityDesc = new HashMap<>();
                    double temp1 = 0.0;
                    double res = 0.0;
                    double temp = 0.0;
                    int doc = 0;
                    toOutput1+="Computed Probabilities\n\n";
                    while((line = unlabeledDataset.readLine()) != null) {
                        doc++;
                        //System.out.println(line);
                        String[] words = line.split(" ");
                        toOutput += "\nUnlabeled Dataset: "+doc+"\n\n'"+line+"'"+"\n";
                        for(String word:words) {
                            if(!stopWords.contains(word)) {
                                uniqueUnlabeledDatasetWords.add(word);
                            }
                            if(!stopWords.contains(word) && !uniqueWords.contains(word)) {
                                uniqueUnlabeledDatasetWords2.add(word);
                            }
                        }
                        toOutput1 += "\nUnlabeled Dataset: "+doc+"\n\n";
                        for(Map.Entry m:frequencyOfClass.entrySet()) {
//                            tempString +="\nComputed value for "+m.getKey()+" class (Unlabeled Dataset "+doc+")\n\n";
                            for(String word:uniqueUnlabeledDatasetWords) {
                                if(computedWordWithClass.containsKey(word+"_"+m.getKey())) {
                                    //tempString+=(word+"_"+m.getKey()+": "+computedWordWithClass.get(word+"_"+m.getKey()))+"\n";
                                    temp = computedWordWithClass.get(word+"_"+m.getKey());
                                    if(res == 0) {
                                        res = temp;
                                    }else {
                                        res *= temp;
                                    }
                                }else {
                                    //System.out.println(word);
                                    //uniqueUnlabeledDatasetWords.add(word);
                                    //tempString+=(word+"_"+m.getKey()+": "+tokenMinVal+" (Unique word and minimum value assigned)\n");
                                }
                            }

                            res = res*parentProbability.get(m.getKey());
                            highestProbabilityDesc.put(String.valueOf(m.getKey()), res);
/* Get the highest computed probability that will determine the class of unlabeled dataset */
                            if(temp1 > res) {
                                tempString2 = "";
                                tempString2 += temp1+" (Highest computed probability)";


                            }else {
                                tempString2 = "";
                                tempString2 += res+" (Highest computed probability)";
                            }
                            toOutput1+="P("+m.getKey()+"): "+(parentProbability.get(m.getKey()))+"\t\n";
                            toOutput1+="Predicted P("+m.getKey()+"): "+res+"\n\n";
                            temp1 = res;
                            System.out.println(temp1+" -- "+m.getKey());
                            res = 0.0;
                            temp = 0.0;

                        }
                        Map.Entry<String, Double> maxEntry = null;

                        for (Map.Entry<String, Double> entry : highestProbabilityDesc.entrySet())
                        {
                            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                            {
                                maxEntry = entry;
                            }
                        }
                        toOutput1+=tempString2+" - "+maxEntry.getKey()+"\n\n";

                    }

//                    double res = 0.0;
//                    double temp = 0.0;
/* Compute the probability to predict the class of the unlabeled dataset 
                    P(W|+) = P(W1|+)...P(Wn|+)
                    P(W|-) = P(W1|-)...P(Wn|-)
*/

//                    HashMap<String, Double> highestProbabilityDesc = new HashMap<>();
//                    for(Map.Entry m:frequencyOfClass.entrySet()) {
//                        tempString +="\nComputed value for "+m.getKey()+" class (Unlabeled Dataset)\n\n";
//                        for(String word:uniqueUnlabeledDatasetWords) {
//                            if(computedWordWithClass.containsKey(word+"_"+m.getKey())) {
//                                tempString+=(word+"_"+m.getKey()+": "+computedWordWithClass.get(word+"_"+m.getKey()))+"\n";
//                                temp = computedWordWithClass.get(word+"_"+m.getKey());
//                                if(res == 0) {
//                                    res = temp;
//                                }else {
//                                    res *= temp;
//                                }
//                            }else {
//                                //System.out.println(word);
//                                //uniqueUnlabeledDatasetWords.add(word);
//                                tempString+=(word+"_"+m.getKey()+": "+tokenMinVal+" (Unique word and minimum value assigned)\n");
//                            }
//                        }
//
//                        res = res*parentProbability.get(m.getKey());
//                        highestProbabilityDesc.put(String.valueOf(m.getKey()), res);
/* Get the highest computed probability that will determine the class of unlabeled dataset */
//                        if(temp1 > res) {
//                            tempString2 = "";
//                            tempString2 += temp1+" (Highest computed probability)";
//
//
//                        }else {
//                            tempString2 = "";
//                            tempString2 += res+" (Highest computed probability)";
//                        }
//                        toOutput1+="P("+m.getKey()+"): "+(parentProbability.get(m.getKey()))+"\t\n";
//                        toOutput1+="Predicted P("+m.getKey()+"): "+res+"\n\n";
//                        temp1 = res;
//                        System.out.println(temp1+" -- "+m.getKey());
//                        res = 0.0;
//                        temp = 0.0;
//
//                    }
//                    Map.Entry<String, Double> maxEntry = null;
//
//                    for (Map.Entry<String, Double> entry : highestProbabilityDesc.entrySet())
//                    {
//                        if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
//                        {
//                            maxEntry = entry;
//                        }
//                    }
//
//
//                    toOutput1+=tempString2+" - "+maxEntry.getKey()+"\n\n";
//                    toOutput1+="--------------------------------------------------------------------------------\n\n";
//                    toOutput1+=tempString;

                    toOutput1+="--------------------------------------------------------------------------------\n\n";
                    toOutput1+="Unique Words from Unlabeled Dataset\n\n";

                    for(String w:uniqueUnlabeledDatasetWords2) {
                        toOutput1+=w+"\n";
                    }
                }catch(Exception e) {
                    e.printStackTrace();
                }


//
//
//
//                linearPane.add(text1, 0,1);

                TableView<Reviews> table = new TableView<>();
                table.setMinHeight(400);
                Scene scene = new Scene(new Group());
                stage1.setTitle("Naive Bayes");
                stage1.setWidth(720);
                stage1.setHeight(720);

                final Label label = new Label("Naive Bayes Text Classifier");
                //label.setFont(new Font("Arial", 20));


                TableColumn rowsCol = new TableColumn("Row #");
                rowsCol.setMinWidth(100);
                rowsCol.setCellValueFactory(
                        new PropertyValueFactory<>("row"));

                TableColumn textCol = new TableColumn("Text");
                textCol.setMinWidth(400);
                textCol.setCellValueFactory(
                        new PropertyValueFactory<>("text"));

                TableColumn reviewClassCol = new TableColumn("Class");
                reviewClassCol.setMinWidth(200);
                reviewClassCol.setCellValueFactory(
                        new PropertyValueFactory<>("reviewClass"));

                table.setItems(data);
                table.getColumns().addAll(rowsCol, textCol, reviewClassCol);

                Text text1 = new Text(toOutput);
                TextArea textArea = new TextArea();
                TextArea textArea2 = new TextArea();
                textArea.setEditable(false);
                textArea.setPrefColumnCount(10);
                textArea.setPrefRowCount(13);

                textArea2.setEditable(false);
                textArea2.setPrefColumnCount(38);
                textArea2.setPrefRowCount(13);
                textArea2.setText(toOutput1);

                textArea.setText(toOutput);
                textArea.setWrapText(true);
                HBox hbox = new HBox();
                hbox.setSpacing(5);
                hbox.setPadding(new Insets(10, 0, 0, 10));
                hbox.getChildren().addAll(textArea,textArea2);

                final VBox vbox = new VBox();
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(10, 0, 0, 10));
                vbox.getChildren().addAll(label, table, hbox);


                ((Group) scene.getRoot()).getChildren().addAll(vbox);


                stage1.setScene(scene);
                stage1.show();
            }
        });

        Text linearRegressionInst = new Text();
        Text naiveBayesInst = new Text();
        String out1 = "Instructions for Using Linear Regression\n - Locate the Training Dataset \n - Locate the Unlabeled Dataset";
        String out2 = "Instructions for Using Naive Bayes\n - Set the minimum value for unique tokens \n - Locate the Training Dataset \n - Locate the Unlabeled Dataset \n - Locate the stop words list text file";
        linearRegressionInst.setText(out1);
        naiveBayesInst.setText(out2);

        GridPane.setConstraints(welcome, 0, 0);
        pane.getChildren().add(welcome);
        GridPane.setConstraints(button1, 0, 1);
        pane.getChildren().add(button1);
        GridPane.setConstraints(button2, 0, 2);
        pane.getChildren().add(button2);
        GridPane.setConstraints(uniqueTokenLabel, 0, 3);
        pane.getChildren().add(uniqueTokenLabel);
        GridPane.setConstraints(uniqueTokenLabel2, 0, 4);
        pane.getChildren().add(uniqueTokenLabel2);
        GridPane.setConstraints(uniqueTokenField, 0, 5);
        pane.getChildren().add(uniqueTokenField);

        GridPane.setConstraints(linearRegressionInst, 0, 10);
        pane.getChildren().add(linearRegressionInst);
        GridPane.setConstraints(naiveBayesInst, 0, 11);
        pane.getChildren().add(naiveBayesInst);
        Scene scene = new Scene(pane, 400,400);
        primaryStage.setTitle("Data Mining Algorithms");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public class Reviews {
        private final SimpleIntegerProperty row;
        private final SimpleStringProperty text;
        private final SimpleStringProperty reviewClass;

        private Reviews(int row, String text, String reviewClass) {
            this.row = new SimpleIntegerProperty(row);
            this.text = new SimpleStringProperty(text);
            this.reviewClass = new SimpleStringProperty(reviewClass);
        }

        public int getRow() {
            return row.get();
        }

        public void setRow(int sRow) {
            row.set(sRow);
        }

        public String getText() {
            return text.get();
        }

        public void setText(String sText) {
            text.set(sText);
        }

        public String getReviewClass() {
            return reviewClass.get();
        }

        public void setReviewClass(String sReviewClass) {
            reviewClass.set(sReviewClass);
        }
    }

    public class LinearRegression {
        private final SimpleIntegerProperty row;
        private final SimpleStringProperty xValue;
        private final SimpleStringProperty yValue;

        private LinearRegression(int row, String xValue, String yValue) {
            this.row = new SimpleIntegerProperty(row);
            this.xValue = new SimpleStringProperty(xValue);
            this.yValue = new SimpleStringProperty(yValue);
        }

        public int getRow() {
            return row.get();
        }

        public void setRow(int sRow) {
            row.set(sRow);
        }

        public String getXValue() {
            return xValue.get();
        }

        public void setXValue(String sXValue) {
            xValue.set(sXValue);
        }

        public String getYValue() {
            return yValue.get();
        }

        public void setYValue(String sYValue) {
            yValue.set(sYValue);
        }
    }

}
