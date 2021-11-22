package output.Query;

import fileio.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;
import output.*;

public class QueryActorAverage extends Query {
    private HashMap<ShowInput, Double> mediumRatedShow = new HashMap<>();
    private Map<MovieInputData, List<Double>> movieInputDataListMap;
    private Map<SerialInputData, List<ActionInputData>> serialInputDataListMap;

    public Map<SerialInputData, List<ActionInputData>> getSerialInputDataListMap() {
        return serialInputDataListMap;
    }

    public void setSerialInputDataListMap(Map<SerialInputData, List<ActionInputData>> serialInputDataListMap) {
        this.serialInputDataListMap = serialInputDataListMap;
    }

    public Map<MovieInputData, List<Double>> getMovieInputDataListMap() {
        return movieInputDataListMap;
    }

    public void setMovieInputDataListMap(Map<MovieInputData, List<Double>> movieInputDataListMap) {
        this.movieInputDataListMap = movieInputDataListMap;
    }

    public QueryActorAverage() {
    }

    public double calculateMediumGradeMovie(List<Double> gradeList) {
        double sum = 0;
        for (Double grade : gradeList) {
            sum += grade;
        }
        return sum / gradeList.size();
    }

    public double calculateMediumGradeSerial(List<ActionInputData> actionInputDataList, SerialInputData serialInputData) {
        Double[] gradeOfSeason = new Double[serialInputData.getSeasons().size()];
        for (int i = 0; i < serialInputData.getSeasons().size(); i++) {
            int countAppearance = 0;
            double sum = 0;
            for (var actionInputData : actionInputDataList) {
                if (actionInputData.getSeasonNumber() == i + 1) {
                    countAppearance++;
                    sum += actionInputData.getGrade();
                }
            }
            if (countAppearance != 0) {
                gradeOfSeason[i] = sum / countAppearance;
            } else {
                gradeOfSeason[i] = 0d;
            }
        }
        double totalRating = 0;
        for (int i = 0; i < gradeOfSeason.length; i++) {
            totalRating += gradeOfSeason[i];
        }
        return totalRating / gradeOfSeason.length;
    }

    /**
     * @param actionInputData
     * @param input
     */
    @Override
    public Result query(ActionInputData actionInputData, Input input) {
        Result result = new Result();
        HashMap<ShowInput, Double> arrangedRatingShow;
        String[] tmpNameList;
        List<String> storeNameActorList = new ArrayList<>();
        StringBuffer tmpNameActorList = new StringBuffer().append("Query result: [");
        for (var entry : this.movieInputDataListMap.entrySet()) {
            mediumRatedShow.put(entry.getKey(), calculateMediumGradeMovie(entry.getValue()));
        }
        for (var entry : this.serialInputDataListMap.entrySet()) {
            mediumRatedShow.put(entry.getKey(), calculateMediumGradeSerial(entry.getValue(), entry.getKey()));
        }
        if (actionInputData.getSortType().equals("asc")) {
            arrangedRatingShow = mediumRatedShow.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            arrangedRatingShow = mediumRatedShow.entrySet().stream().
                    sorted(Map.Entry.<ShowInput, Double>comparingByValue().reversed()).
                    collect(Collectors.
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        for (var entry : arrangedRatingShow.entrySet()) {
            tmpNameList = entry.getKey().getCast().toArray(new String[0]);
            Arrays.sort(tmpNameList);
            storeNameActorList.addAll(Arrays.asList(tmpNameList));
        }
        for (int i = 0; i < actionInputData.getNumber(); i++) {
            if (i == 0) {
                tmpNameActorList.append(storeNameActorList.get(i));
            } else {
                tmpNameActorList.append(", ").append(storeNameActorList.get(i));
            }
        }
        tmpNameActorList.append("]");
        result.setId(actionInputData.getActionId());
        result.setMessage(tmpNameActorList);
        return result;
    }
}