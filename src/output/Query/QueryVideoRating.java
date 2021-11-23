package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import java.util.*;
import java.util.stream.Collectors;
import output.Result;

public final class QueryVideoRating extends Query {
    private Map<MovieInputData, List<Double>> movieInputDataListMap;
    private Map<SerialInputData, List<ActionInputData>> serialInputDataListMap;

    public void setSerialInputDataListMap(final Map<SerialInputData,
            List<ActionInputData>> serialInputDataListMap) {
        this.serialInputDataListMap = serialInputDataListMap;
    }

    public void setMovieInputDataListMap(final Map<MovieInputData,
            List<Double>> movieInputDataListMap) {
        this.movieInputDataListMap = movieInputDataListMap;
    }

    /**
     * return medium grade of curent movie
     *
     * @param gradeList
     */
    public double calculateMediumGradeMovie(final List<Double> gradeList) {
        double sum = 0;
        for (Double grade : gradeList) {
            sum += grade;
        }
        return sum / gradeList.size();
    }

    /**
     * return medium grade of curent serial
     *
     * @param actionInputDataList
     * @param serialInputData
     */
    public double calculateMediumGradeSerial(final List<ActionInputData> actionInputDataList,
                                             final SerialInputData serialInputData) {
        double sum = 0;
        for (ActionInputData actionInputData : actionInputDataList) {
            sum += actionInputData.getGrade();
        }
        sum = sum / actionInputDataList.size();
        sum = sum / serialInputData.getSeasons().size();
        return sum;
    }

    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        HashMap<String, Double> arrangedRatingShow;
        Map<String, Double> unsortedShowList = new HashMap<>();
        List<String> nameList = new ArrayList<>();

        if (actionInputData.getObjectType().equals("movies")) {
            for (var entry : this.movieInputDataListMap.entrySet()) {
                int checkMatchYear = 0;
                int checkMatchGenre = 0;
                if (actionInputData.getFilters().get(0).get(0) == null) {
                    checkMatchYear++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                        if (entry.getKey().getYear() == Integer.parseInt(actionInputData.
                                getFilters().get(0).get(i))) {
                            checkMatchYear++;
                            break;
                        }
                    }
                }
                if (actionInputData.getFilters().get(1).get(0) == null) {
                    checkMatchGenre++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(1).size(); i++) {
                        if (entry.getKey().getGenres().contains(actionInputData
                                .getFilters().get(1).get(0))) {
                            checkMatchGenre++;
                            break;
                        }
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
                if (actionInputData.getFilters().get(0).get(0) == null) {
                    checkMatchYear++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(0).size(); i++) {
                        if (entry.getKey().getYear() == Integer.parseInt(actionInputData.
                                getFilters().get(0).get(i))) {
                            checkMatchYear++;
                            break;
                        }
                    }
                }
                if (actionInputData.getFilters().get(1).get(0) == null) {
                    checkMatchGenre++;
                } else {
                    for (int i = 0; i < actionInputData.getFilters().get(1).size(); i++) {
                        if (entry.getKey().getGenres().contains(actionInputData
                                .getFilters().get(1).get(0))) {
                            checkMatchGenre++;
                            break;
                        }
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
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            arrangedRatingShow = unsortedShowList.entrySet().stream().
                    sorted(Map.Entry.<String, Double>comparingByValue().reversed()).
                    collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
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
