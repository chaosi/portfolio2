package sample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SearchDb {

    private SqlConnection dbQuary = new SqlConnection(); // connection manager class
    private ResultSet rs; // for handling statementsQuary
    private LinkedList<LinkedList<String>> TrainDataSet = new LinkedList<LinkedList<String>>(); // unfiltere dataset from DB quary
    private LinkedList<String> filteredTraindataList = new LinkedList<>(); //filtered data set ready for GUI/user



    // find station that most resembles input search
    public String FindStation(String SearchText) {
        try {
            String stmtQ = "select Stations  from StationName WHERE Stations LIKE '" + SearchText + "%' limit 1";
            rs = dbQuary.select(stmtQ);

            return rs.getString("Stations"); // return value from "Stations" column

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

            System.out.println(e);
            return null;

        } catch (SQLException e) {
            e.printStackTrace();

            System.out.println(e);
            return null;

        }
    }



    // get dataSet from Db and
    public LinkedList setDataTraindataset(String from, String to, String hour, String min) {
        try {
            // removing all left over data from lists
            TrainDataSet.clear();
            filteredTraindataList.clear();

            String stmtQ ="select schedule.station, schedule.time, schedule.lineId, schedule.trainNumber, lines.\"from\", lines.\"to\" from lines INNER JOIN schedule ON lines.id = schedule.lineId where station LIKE '" + from + "%'  or station like '" + to + "%' and TIME(time) > '" + hour + ":" + min + ":00' Order By TIME(time) ASC";
            rs = dbQuary.select(stmtQ); //Query statement to Database

            while (rs.next()) {
                LinkedList<String> train = new LinkedList<String>();//maze
                train.add(rs.getString("station"));
                train.add(rs.getString("time"));
                train.add(rs.getString("lineId"));
                train.add(rs.getString("trainNumber"));
                train.add(rs.getString("from"));
                train.add(rs.getString("to"));
                TrainDataSet.add(train);
            }

            FilterTrainStations(from,to); //start adding to filteredTraindataList

            // check if  if list is not empty else add "no Train Connections Found" to list and return
            if(1 <= filteredTraindataList.size()) {
                return filteredTraindataList;
            }
            else {
                filteredTraindataList.add("no Train Connections Found");
                return filteredTraindataList;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e);
            filteredTraindataList.add("error" + e);
            return filteredTraindataList;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            filteredTraindataList.add("error" + e);
            return filteredTraindataList;
        }
    }



    //filters and sorts relevant trainstations
    private void FilterTrainStations(String From, String To) {


            for (int i = 0; i < TrainDataSet.size(); i++) {
                if (TrainDataSet.get(i).get(0).equals(From)) {// get a from value
                    for (int j = i; j < TrainDataSet.size(); j++) {//start a loop to find matching  'To' from found 'From' values (lineId and trainNumber)
                        if (TrainDataSet.get(j).get(0).equals(To)) {
                            if (TrainDataSet.get(i).get(2).equals(TrainDataSet.get(j).get(2))) {//if LineId matches
                                if (TrainDataSet.get(i).get(3).equals(TrainDataSet.get(j).get(3))){//if Trainnumber matches
                                    String line = "**** line - "+TrainDataSet.get(i).get(4)+"-"+TrainDataSet.get(i).get(5)+"****";
                                    filteredTraindataList.add(line);
                                    String output = " - start from " + TrainDataSet.get(i).get(0) + " at time " + TrainDataSet.get(i).get(1) + "" + "- end at " + TrainDataSet.get(j).get(0) + " at time " + TrainDataSet.get(j).get(1);
                                    filteredTraindataList.add(output);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
    }
}



