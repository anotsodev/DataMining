/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
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
                linearPane.setHgap(5.5);
                linearPane.setVgap(5.5);
                String text = "";
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
                BufferedReader reader = null;
                BufferedReader reader2 = null;
                String line = "";
                String splitCsv = ",";
                double outlook_prob_yes = 0.0;
                double temp_prob_yes = 0.0;
                double humidity_prob_yes = 0.0;
                double windy_prob_yes = 0.0;

                double outlook_prob_no = 0.0;
                double temp_prob_no = 0.0;
                double humidity_prob_no = 0.0;
                double windy_prob_no = 0.0;
        //        YES Count
                int sunny_yes = 0;
                int overcast_yes = 0;
                int rainy_yes = 0;

                int hot_yes = 0;
                int mild_yes = 0;
                int cool_yes = 0;

                int high_yes = 0;
                int normal_yes = 0;

                int true_yes = 0;
                int false_yes = 0;
        //        NO Count
                int sunny_no = 0;
                int overcast_no = 0;
                int rainy_no = 0;

                int hot_no = 0;
                int mild_no = 0;
                int cool_no = 0;

                int high_no = 0;
                int normal_no = 0;

                int true_no = 0;
                int false_no = 0;

                double yes_prob = 0.0;
                double no_prob = 0.0;

                int yes_count = 0;
                int no_count = 0;
                int n = 0;
                int unlabeledRow = 0;
                double total_yes_prob = 0.0;
                double total_no_prob = 0.0;
                try {
                    text += "\t\t\t\t=========== Training Dataset =========== \n";
                    text += "\n";
                    text+= String.format("%-20s%-20s%-20s%-20s%-20s","Outlook","Temperature","Humidity","Windy","Play")+"\n";
                    reader = new BufferedReader(new FileReader(csvFile));
                    reader2 = new BufferedReader(new FileReader(csvFile2));
                    while((line = reader.readLine()) != null) {
                        String[] playDs = line.split(splitCsv);
                        text+= String.format("%-20s%-30s%-20s%-20s%-10s",playDs[0],playDs[1],playDs[2],playDs[3],playDs[4])+"\n";
                        
                        n+=1;
                        unlabeledRow++;
                        if(playDs[4].toLowerCase().equals("yes")) {
                            yes_count += 1;
                        }
                        if(playDs[4].toLowerCase().equals("no")) {
                            no_count += 1;
                        }
        //                Count number outlook with yes
                        if(playDs[0].toLowerCase().equals("sunny") && playDs[4].toLowerCase().equals("yes")) {
                            sunny_yes+=1;
                        }
                        if(playDs[0].toLowerCase().equals("overcast") && playDs[4].toLowerCase().equals("yes")) {
                            overcast_yes+=1;
                        }
                        if(playDs[0].toLowerCase().equals("rainy") && playDs[4].toLowerCase().equals("yes")) {
                            rainy_yes+=1;
                        }
        //                Count number of temperature with yes
                        if(playDs[1].toLowerCase().equals("hot") && playDs[4].toLowerCase().equals("yes")) {
                            hot_yes+=1;
                        }
                        if(playDs[1].toLowerCase().equals("mild") && playDs[4].toLowerCase().equals("yes")) {
                            mild_yes+=1;
                        }
                        if(playDs[1].toLowerCase().equals("cool") && playDs[4].toLowerCase().equals("yes")) {
                            cool_yes+=1;
                        }
        //                Count number of humidity with yes
                        if(playDs[2].toLowerCase().equals("high") && playDs[4].toLowerCase().equals("yes")) {
                            high_yes+=1;
                        }
                        if(playDs[2].toLowerCase().equals("normal") && playDs[4].toLowerCase().equals("yes")) {
                            normal_yes+=1;
                        }
        //                Count windy with yes
                        if(playDs[3].toLowerCase().equals("true") && playDs[4].toLowerCase().equals("yes")) {
                            true_yes+=1;
                        }
                        if(playDs[3].toLowerCase().equals("false") && playDs[4].toLowerCase().equals("yes")) {
                            false_yes+=1;
                        }
        //                Count number outlook with no
                        if(playDs[0].toLowerCase().equals("sunny") && playDs[4].toLowerCase().equals("no")) {
                            sunny_no+=1;
                        }
                        if(playDs[0].toLowerCase().equals("overcast") && playDs[4].toLowerCase().equals("no")) {
                            overcast_no+=1;
                        }
                        if(playDs[0].toLowerCase().equals("rainy") && playDs[4].toLowerCase().equals("no")) {
                            rainy_no+=1;
                        }
        //                Count number of temperature with no
                        if(playDs[1].toLowerCase().equals("hot") && playDs[4].toLowerCase().equals("no")) {
                            hot_no+=1;
                        }
                        if(playDs[1].toLowerCase().equals("mild") && playDs[4].toLowerCase().equals("no")) {
                            mild_no+=1;
                        }
                        if(playDs[1].toLowerCase().equals("cool") && playDs[4].toLowerCase().equals("no")) {
                            cool_no+=1;
                        }
        //                Count number of humidity with no
                        if(playDs[2].toLowerCase().equals("high") && playDs[4].toLowerCase().equals("no")) {
                            high_no+=1;
                        }
                        if(playDs[2].toLowerCase().equals("normal") && playDs[4].toLowerCase().equals("no")) {
                            normal_no+=1;
                        }
        //                Count windy with yes
                        if(playDs[3].toLowerCase().equals("true") && playDs[4].toLowerCase().equals("no")) {
                            true_no+=1;
                        }
                        if(playDs[3].toLowerCase().equals("false") && playDs[4].toLowerCase().equals("no")) {
                            false_no+=1;
                        }
                    }// End of While
                    text += "\n";
                    text += "\nNumber of Rows: "+unlabeledRow;
                    text += "\n";
                    text += ("\t\t\t\t=========== A New Day ===========\n");
                    text += "\n";
                    text+= "Outlook\t\t\tTemperature\t\t\tHumidity\t\t\tWindy\t\t\tPlay\n";
                    unlabeledRow = 0;
                    while((line = reader2.readLine()) != null) {

                        String[] playDs = line.split(splitCsv);
                        String outlook = playDs[0].toLowerCase();
                        String temperature = playDs[1].toLowerCase();
                        String humidity = playDs[2].toLowerCase();
                        String windy = playDs[3].toLowerCase();
                        text+= String.format("%-20s%20s%30s%30s%30s%n",outlook,temperature,humidity,windy,"?");
                        unlabeledRow++;
                        switch(outlook) {
                            case "sunny":
                                outlook_prob_yes = ((double)sunny_yes/(double)yes_count);
                                outlook_prob_no = ((double)sunny_no/(double)no_count);
                                break;
                            case "overcast":
                                outlook_prob_yes = ((double)overcast_yes/(double)yes_count);
                                outlook_prob_no = ((double)overcast_no/(double)no_count);
                                break;
                            case "rainy":
                                outlook_prob_yes = ((double)rainy_yes/(double)yes_count);
                                outlook_prob_no = ((double)rainy_no/(double)no_count);
                                break;
                        }
                        switch(temperature) {
                            case "hot":
                                temp_prob_yes = ((double)hot_yes/(double)yes_count);
                                temp_prob_no = ((double)hot_no/(double)no_count);
                                break;
                            case "mild":
                                temp_prob_yes = ((double)mild_yes/(double)yes_count);
                                temp_prob_no = ((double)mild_no/(double)no_count);
                                break;
                            case "cool":
                                temp_prob_yes = ((double)cool_yes/(double)yes_count);
                                temp_prob_no = ((double)cool_no/(double)no_count);
                                break;
                        }
                        switch(humidity) {
                            case "high":
                                humidity_prob_yes = ((double)high_yes/(double)yes_count);
                                humidity_prob_no = ((double)high_no/(double)no_count);
                                break;
                            case "normal":
                                humidity_prob_yes = ((double)normal_yes/(double)yes_count);
                                humidity_prob_no = ((double)normal_no/(double)no_count);
                                break;
                        }
                        switch(windy) {
                            case "true":
                                windy_prob_yes = ((double)true_yes/(double)yes_count);
                                windy_prob_no = ((double)true_no/(double)no_count);

                                break;
                            case "false":
                                windy_prob_yes = ((double)false_yes/(double)yes_count);
                                windy_prob_no = ((double)false_no/(double)no_count);
                                break;
                        }

                    }//End of While
                    text += "\n";
                    text += "\nNumber of Rows: "+unlabeledRow;
                   total_yes_prob = (outlook_prob_yes*temp_prob_yes*humidity_prob_yes*windy_prob_yes*((double)yes_count/(double)n));
                   total_no_prob = (outlook_prob_no*temp_prob_no*humidity_prob_no*windy_prob_no*((double)no_count/(double)n));
                   text+= "\n";
                   if(total_yes_prob > total_no_prob) {
                       text += String.format("%70s", "Play = Yes");
                   }
                   if(total_no_prob > total_yes_prob) {
                       text+= String.format("%70s","Play = No");
                   }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
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
        pane.getChildren().add(welcome);
        pane.getChildren().add(button1);
        pane.getChildren().add(button2);
        
        Scene scene = new Scene(pane, 250,100);
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
    
}
