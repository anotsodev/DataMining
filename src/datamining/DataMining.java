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
import java.util.*;
import java.util.List;

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
import javafx.scene.control.cell.PropertyValueFactory;
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
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(10,10,10,10));
        pane.setHgap(5);
        pane.setVgap(25);

        Text welcome = new Text();
        welcome.setText("Please choose one algorithm to display");

        Button button1 = new Button();
        button1.setText("Linear Regression");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                }
                FileChooser fileChooser2 = new FileChooser();
                fileChooser.setTitle("Open Unlabeled Dataset for Linear Regression");

                File file2 = fileChooser.showOpenDialog(stage1);
                if(file2 != null) {
                    csvFile2 = file2.getName();
                }
                //"LinearRegressionDS.csv";
                //csvFile2 = "Sample.csv";
                BufferedReader reader1 = null;
                BufferedReader reader2 = null;
                String line;
                String splitCsv = ",";
                double[] x = new double[10];
                double[] y = new double[10];
                double meanX = 0.0;
                double meanY = 0.0;
                int n = 0;
                String text = "";
                try {
                    reader1 = new BufferedReader(new FileReader(csvFile));
                    reader2 = new BufferedReader(new FileReader(csvFile2));
                    text += "=========== Training Dataset =========== \n";
                    text+="\n";
                    text += "\tX Value\t\t\t\tY Values\n";
                    text+="\n";
                    while((line = reader1.readLine()) != null) {

                        String[] csvLine = line.split(splitCsv);
                        text+= "\t\t"+csvLine[0]+"\t\t\t\t\t"+csvLine[1]+"\n";

                        x[n] = Double.valueOf(csvLine[0]);
                        y[n] = Double.valueOf(csvLine[1]);

                        n++;
                    }
                    text += "\n";
                    text += "\nNumber of Rows: "+x.length;
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

                    text+="\n";
                    text+= "=========== Unlabeled Dataset =========== \n";
                    text+="\n";
                    text+= "\tX Values\t\t\t\tPredicted Values\n";
                    text+="\n";
                    int unlabeledRow = 0;
                    while((line = reader2.readLine()) != null) {
                        double prediction = a+(b*Double.valueOf(line));

                        text+= "\t\t"+line+"\t\t\t\t"+prediction+"\n";
                        unlabeledRow++;
                    }
                    text += "\n";
                    text += "\nNumber of Rows: "+unlabeledRow;

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


                Text text1 = new Text(text);



                linearPane.add(text1, 0,1);


                stage1.setScene(new Scene(linearPane, 500,500));
                stage1.show();


            }
        });

        Button button2 = new Button();
        button2.setText("Naive Bayes");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GridPane linearPane = new GridPane();
                linearPane.setAlignment(Pos.CENTER);
                //linearPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
                ObservableList<Reviews> data = FXCollections.observableArrayList();
                linearPane.setHgap(5.5);
                linearPane.setVgap(5.5);
                String toOutput = "";
                Stage stage1 = new Stage();
                stage1.setTitle("Naive Bayes");
                String csvFile = "";
                String csvFile2 = "";
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Training Dataset for Naive Bayes");
                File file = fileChooser.showOpenDialog(stage1);
                if(file != null) {
                    csvFile = file.getName();
                }
                FileChooser fileChooser2 = new FileChooser();
                fileChooser.setTitle("Open Unlabeled Dataset for Naive Bayes");

                File file2 = fileChooser.showOpenDialog(stage1);
                if(file2 != null) {
                    csvFile2 = file2.getName();
                }
                try {
            /* Initialize variables */
                    String unlabeledDatasetFile = csvFile2;
                    String trainingDatasetFile = csvFile;
                    List<String> wordList = new ArrayList<String>();
             /* Read the csv files */
                    BufferedReader trainingDataset = new BufferedReader(new FileReader(trainingDatasetFile));
                    BufferedReader unlabeledDataset = new BufferedReader(new FileReader(unlabeledDatasetFile));
                    Set<String> arrayOfClass = new HashSet<>();
                    ArrayList<String> arrayOfWords = new ArrayList<>();
                    ArrayList<Reviews> reviewsArray = new ArrayList<>();
                    String line = "";
                    int rows = 0;
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
                            arrayOfWords.add(word);
                            wordList.add(String.valueOf(rows)+"_"+word+"_"+classification);
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
            /*Get the words inside the uniqueWords Set */
                    for(String word:wordList) {
                        String[] words = word.split("_");
                        String x = words[0];
                        String y = words[1];
                        String z = words[2];
                /* then add the word and its class to array list */
                        textList.put(Integer.valueOf(x), z);
                        uniqueWords.add(y);
                        uniqueWordsWithClass.add(y+"_"+z);
                        //wordListWithClass.add(y+"_"+z);
                        uniqueWordsToCount.add(y);
                    }
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


                    for(Map.Entry m:parentProbability.entrySet()) {
                        //System.out.println(m.getKey()+" "+m.getValue());
                    }
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
                    for(String word: uniqueWords) {
                        vocabulary+=1;
                        //System.out.println(word);
                    }
                    //System.out.println("++++++++++++");
                    HashMap<String, Double> computedWordWithClass = new HashMap<>();
                    int nk = 0;
                    int nn = 0;

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
                    toOutput+="\n\t=======================================================";
                    while((line = unlabeledDataset.readLine()) != null) {
                        //System.out.println(line);
                        String[] words = line.split(" ");
                        toOutput += "\n\t\t\t\t\tUnlabeled Dataset: "+"'"+line+"'"+"\n";
                        double res = 0.0;
                        double temp = 0.0;
                        for(Map.Entry m:frequencyOfClass.entrySet()) {
                            //System.out.println(m.getKey());
                            for(Map.Entry n:computedWordWithClass.entrySet()) {
                                String computedWords = String.valueOf(n.getKey());
                                String[] arrayOfComputedWords = computedWords.split("_");
                                for(String word:words) {
                                    if(word.equalsIgnoreCase(arrayOfComputedWords[0]) && arrayOfComputedWords[1].equalsIgnoreCase(String.valueOf(m.getKey()))) {

                                        temp = (double) n.getValue();
                                        if(res == 0) {
                                            res = temp;
                                        }else {
                                            res *= temp;
                                        }
                                    }else {

                                    }
                                }
                            }
                            res = res*parentProbability.get(m.getKey());
                            toOutput+="\t=======================================================\n";
                            toOutput+="P("+m.getKey()+") : "+(parentProbability.get(m.getKey()))+"\t";
                            toOutput+=" - \tComputed total for P("+m.getKey()+"): "+(res)+"\n";
                            res = 0.0;
                            temp = 0.0;
                        }

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
            }
        });
        pane.getChildren().add(welcome);
        pane.getChildren().add(button1);
        pane.getChildren().add(button2);

        Scene scene = new Scene(pane, 300,150);
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

}

