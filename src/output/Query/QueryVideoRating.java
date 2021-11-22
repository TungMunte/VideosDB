package output.Query;

import fileio.*;
import java.util.*;
import java.util.stream.*;
import output.*;

public class QueryVideoRating extends Query {
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

    public double calculateMediumGradeMovie(List<Double> gradeList) {
        double sum = 0;
        for (Double grade : gradeList) {
            sum += grade;
        }
        return sum / gradeList.size();
    }

    public double calculateMediumGradeSerial(List<ActionInputData> actionInputDataList,
                                             SerialInputData serialInputData) {
        double sum = 0;
        for (int i = 0; i < actionInputDataList.size(); i++) {
            sum += actionInputDataList.get(i).getGrade();
        }
        sum = sum / actionInputDataList.size();
        sum = sum / serialInputData.getSeasons().size();
        return sum;
    }

    @Override
    public Result query(ActionInputData actionInputData, Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        HashMap<String, Double> arrangedRatingShow;
        Map<String, Double> unsortedShowList = new HashMap<>();
        List<String> nameList = new ArrayList<>();

        if (actionInputData.getObjectType().equals("movies")) {
            for (var entry : this.movieInputDataListMap.entrySet()) {
                int checkMatchYear = 0;
                int checkMatchGenre = 0;
                for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                    if (entry.getKey().getYear() == Integer.parseInt(actionInputData.
                            getFilters().get(0).get(i))) {
                        checkMatchYear++;
                        break;
                    }
                }
                for (int i = 0; i < actionInputData.getFilters().get(1).size(); i++) {
                    if (entry.getKey().getGenres().contains(actionInputData.
                            getFilters().get(1).get(0)) == true) {
                        checkMatchGenre++;
                        break;
                    }
                }
                if (checkMatchGenre == 1 && checkMatchYear == 1) {
                    unsortedShowList.put(entry.getKey().getTitle(),
                            calculateMediumGradeMovie(entry.getValue()));
                }
            }
        } else {
            for (var entry : this.serialInputDataListMap.entrySet()) {
                int checkMatchYear = 0;
                int checkMatchGenre = 0;
                for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                    if (entry.getKey().getYear() == Integer.parseInt(actionInputData.
                            getFilters().get(0).get(i))) {
                        checkMatchYear++;
                        break;
                    }
                }
                for (int i = 0; i < actionInputData.getFilters().get(1).size(); i++) {
                    if (entry.getKey().getGenres().contains(actionInputData.
                            getFilters().get(1).get(0)) == true) {
                        checkMatchGenre++;
                        break;
                    }
                }
                if (checkMatchGenre == 1 && checkMatchYear == 1) {
                    unsortedShowList.put(entry.getKey().getTitle(),
                            calculateMediumGradeSerial(entry.getValue(), entry.getKey()));
                }
            }
        }

        if (actionInputData.getSortType().equals("asc")) {
            arrangedRatingShow = unsortedShowList.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            arrangedRatingShow = unsortedShowList.entrySet().stream().
                    sorted(Map.Entry.<String, Double>comparingByValue().reversed()).
                    collect(Collectors.
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        for (var entry : arrangedRatingShow.entrySet()) {
            nameList.add(entry.getKey());
        }
        if (nameList.size() < actionInputData.getNumber()) {
            for (int i = 0; i < nameList.size(); i++) {
                if (i == 0) {
                    message.append(nameList.get(0));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        } else {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                if (i == 0) {
                    message.append(nameList.get(0));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        }

        message.append("]");
        result.setId(actionInputData.getActionId());
        result.setMessage(message);
        return result;
    }
}
