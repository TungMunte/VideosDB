package output.Query;

import fileio.ShowInput;
import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import java.util.*;
import java.util.stream.Collectors;
import output.Result;

public final class QueryActorAverage extends Query {
    private final HashMap<ShowInput, Double> mediumRatedShow = new HashMap<>();
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

    public QueryActorAverage() {
    }

    /**
     * calculate medium grade of curent movie
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
     * calculate medium grade of curent serial
     *
     * @param actionInputDataList
     * @param serialInputData
     */
    public double calculateMediumGradeSerial(final List<ActionInputData> actionInputDataList,
                                             final SerialInputData serialInputData) {
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
        for (Double aDouble : gradeOfSeason) {
            totalRating += aDouble;
        }
        return totalRating / gradeOfSeason.length;
    }

    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        HashMap<ShowInput, Double> arrangedRatingShow;
        String[] tmpNameList;
        List<String> storeNameActorList = new ArrayList<>();
        StringBuffer tmpNameActorList = new StringBuffer().append("Query result: [");
        for (var entry : this.movieInputDataListMap.entrySet()) {
            mediumRatedShow.put(entry.getKey(), calculateMediumGradeMovie(entry.getValue()));
        }
        for (var entry : this.serialInputDataListMap.entrySet()) {
            mediumRatedShow.put(entry.getKey(),
                    calculateMediumGradeSerial(entry.getValue(), entry.getKey()));
        }
        if (actionInputData.getSortType().equals("asc")) {
            arrangedRatingShow = mediumRatedShow.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            arrangedRatingShow = mediumRatedShow.entrySet().stream().
                    sorted(Map.Entry.<ShowInput, Double>comparingByValue().reversed()).
                    collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        for (var entry : arrangedRatingShow.entrySet()) {
            tmpNameList = entry.getKey().getCast().toArray(new String[0]);
            Arrays.sort(tmpNameList);
            storeNameActorList.addAll(Arrays.asList(tmpNameList));
        }
        if (actionInputData.getNumber() < storeNameActorList.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                if (i == 0) {
                    tmpNameActorList.append(storeNameActorList.get(i));
                } else {
                    tmpNameActorList.append(", ").append(storeNameActorList.get(i));
                }
            }
        } else {
            for (int i = 0; i < storeNameActorList.size(); i++) {
                if (i == 0) {
                    tmpNameActorList.append(storeNameActorList.get(i));
                } else {
                    tmpNameActorList.append(", ").append(storeNameActorList.get(i));
                }
            }
        }
        tmpNameActorList.append("]");
        result.setId(actionInputData.getActionId());
        result.setMessage(tmpNameActorList);
        return result;
    }
}