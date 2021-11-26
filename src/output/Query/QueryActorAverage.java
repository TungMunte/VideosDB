package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;
import java.util.*;
import output.Result;
import output.Store.StoreQueryActorAverage;

public final class QueryActorAverage extends Query {

    public QueryActorAverage() {
    }

    /**
     * return medium grade of each movie
     *
     * @param actionInputDataList
     */
    public double mediumGradeMovie(final List<ActionInputData> actionInputDataList) {
        double grade = 0d;
        for (var entry : actionInputDataList) {
            grade += entry.getGrade();
        }
        return grade / actionInputDataList.size();
    }

    /**
     * return medium grade of each serial
     *
     * @param actionInputDataList
     * @param serialInputData
     */
    public double mediumGradeSerial(final List<ActionInputData> actionInputDataList,
                                    final SerialInputData serialInputData) {
        double grade = 0d;
        Double[] seasonGraded = new Double[serialInputData.getSeasons().size()];
        Double[] sumForEachSeason = new Double[serialInputData.getSeasons().size()];
        for (int i = 0; i < seasonGraded.length; i++) {
            seasonGraded[i] = 0d;
            sumForEachSeason[i] = 0d;
        }
        for (var action : actionInputDataList) {
            seasonGraded[action.getSeasonNumber() - 1]++;
            sumForEachSeason[action.getSeasonNumber() - 1] += action.getGrade();
        }
        for (int i = 0; i < seasonGraded.length; i++) {
            if (seasonGraded[i] != 0) {
                sumForEachSeason[i] = sumForEachSeason[i] / seasonGraded[i];
            }
        }
        for (int i = 0; i < seasonGraded.length; i++) {
            grade += sumForEachSeason[i];
        }

        return grade / serialInputData.getSeasons().size();
    }

    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {

        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<ActionInputData> actionInputDataList = new ArrayList<>();
        List<ActionInputData> ratingActionForMovie = new ArrayList<>();
        List<ActionInputData> ratingActionForSerial = new ArrayList<>();
        List<ActionInputData> successRatingMovie = new ArrayList<>();
        List<ActionInputData> successRatingSerial = new ArrayList<>();
        List<String> nameSerial = new ArrayList<>();
        List<SerialInputData> serialRated = new ArrayList<>();
        List<StoreQueryActorAverage> nameShowAndGrade = new ArrayList<>();
        List<StoreQueryActorAverage> nameActorAndGrade = new ArrayList<>();

        for (int i = 0; i < actionInputData.getActionId(); i++) {
            actionInputDataList.add(input.getCommands().get(i));
        }
        for (var action : actionInputDataList) {
            if (action.getActionType().equals("command")
                    && action.getType().equals("rating")) {
                if (action.getSeasonNumber() == 0) {
                    ratingActionForMovie.add(action);
                } else {
                    ratingActionForSerial.add(action);
                }
            }
        }

        ratingActionForMovie.sort(new Comparator<ActionInputData>() {
            @Override
            public int compare(final ActionInputData o1, final ActionInputData o2) {
                int result = 0;
                if (!o1.getTitle().equals(o2.getTitle())) {
                    result = o1.getTitle().compareTo(o2.getTitle());
                } else {
                    result = o1.getUsername().compareTo(o2.getUsername());
                }
                return result;
            }
        });
        ratingActionForSerial.sort(new Comparator<ActionInputData>() {
            @Override
            public int compare(final ActionInputData o1, final ActionInputData o2) {
                int result = 0;
                if (!o1.getTitle().equals(o2.getTitle())) {
                    result = o1.getTitle().compareTo(o2.getTitle());
                } else if (!o1.getUsername().equals(o2.getUsername())) {
                    result = o1.getUsername().compareTo(o2.getUsername());
                } else {
                    result = Integer.valueOf(o1.getSeasonNumber()).
                            compareTo(Integer.valueOf(o2.getSeasonNumber()));
                }
                return result;
            }
        });

        for (int i = 0; i < ratingActionForMovie.size(); i++) {
            if (i == 0) {
                successRatingMovie.add(ratingActionForMovie.get(i));
            } else {
                if (!ratingActionForMovie.get(i).getUsername().
                        equals(ratingActionForMovie.get(i - 1).getUsername())) {
                    successRatingMovie.add(ratingActionForMovie.get(i));
                }
            }
        }
        for (int i = 0; i < ratingActionForSerial.size(); i++) {
            if (i == 0) {
                successRatingSerial.add(ratingActionForSerial.get(i));
                nameSerial.add(ratingActionForSerial.get(i).getTitle());
            } else {
                if (!ratingActionForSerial.get(i).getUsername().
                        equals(ratingActionForSerial.get(i - 1).getUsername())
                        || ratingActionForSerial.get(i).getSeasonNumber()
                        != ratingActionForSerial.get(i - 1).getSeasonNumber()) {
                    successRatingSerial.add(ratingActionForSerial.get(i));
                    nameSerial.add(ratingActionForSerial.get(i).getTitle());
                }
            }
        }

        for (var serial : input.getSerials()) {
            if (nameSerial.contains(serial.getTitle())) {
                serialRated.add(serial);
            }
        }
        List<ActionInputData> ratingSameSerial = new ArrayList<>();
        double gradeOfSerial;
        for (int i = 0; i < successRatingSerial.size(); i++) {
            if (i == 0) {
                ratingSameSerial.add(successRatingSerial.get(i));
            } else {
                if (!successRatingSerial.get(i).getTitle().
                        equals(successRatingSerial.get(i - 1).getTitle())) {
                    for (var serial : serialRated) {
                        if (serial.getTitle().equals(successRatingSerial.get(i - 1).getTitle())) {
                            gradeOfSerial = mediumGradeSerial(ratingSameSerial, serial);
                            nameShowAndGrade.add(new StoreQueryActorAverage(
                                    serial.getTitle(), gradeOfSerial));

                        }
                    }
                    ratingSameSerial.clear();
                    ratingSameSerial.add(successRatingSerial.get(i));
                } else {
                    ratingSameSerial.add(successRatingSerial.get(i));
                }
            }
        }
        for (var serial : serialRated) {
            if (serial.getTitle().equals(successRatingSerial.
                    get(successRatingSerial.size() - 1).getTitle())) {
                gradeOfSerial = mediumGradeSerial(ratingSameSerial, serial);
                nameShowAndGrade.add(new StoreQueryActorAverage(
                        serial.getTitle(), gradeOfSerial));
            }
        }
        ratingSameSerial.clear();

        for (int i = 0; i < successRatingMovie.size(); i++) {
            if (i == 0) {
                ratingSameSerial.add(successRatingMovie.get(i));
            } else {
                if (!successRatingMovie.get(i).getTitle().
                        equals(successRatingMovie.get(i - 1).getTitle())) {
                    gradeOfSerial = mediumGradeMovie(ratingSameSerial);
                    nameShowAndGrade.add(new StoreQueryActorAverage(
                            successRatingMovie.get(i - 1).getTitle(), gradeOfSerial));
                    ratingSameSerial.clear();
                    ratingSameSerial.add(successRatingMovie.get(i));
                } else {
                    ratingSameSerial.add(successRatingMovie.get(i));
                }
            }
        }
        gradeOfSerial = mediumGradeMovie(ratingSameSerial);
        nameShowAndGrade.add(new StoreQueryActorAverage(
                successRatingMovie.get(successRatingMovie.size() - 1).
                        getTitle(), gradeOfSerial));

        nameShowAndGrade.sort(new Comparator<StoreQueryActorAverage>() {
            @Override
            public int compare(final StoreQueryActorAverage o1,
                               final StoreQueryActorAverage o2) {
                return o1.getGrade().compareTo(o2.getGrade());
            }
        });

        List<Double> gradedList = new ArrayList<>();
        for (var entry1 : input.getActors()) {
            for (var entry2 : nameShowAndGrade) {
                if (entry1.getFilmography().contains(entry2.getNameShow())) {
                    gradedList.add(entry2.getGrade());
                }
            }
            if (!gradedList.isEmpty()) {
                double sum = 0;
                for (var grade : gradedList) {
                    sum += grade;
                }
                nameActorAndGrade.add(new StoreQueryActorAverage(
                        new StringBuffer(entry1.getName()), sum / gradedList.size()));
                gradedList.clear();
            }
        }
        nameActorAndGrade.sort(new Comparator<StoreQueryActorAverage>() {
            @Override
            public int compare(final StoreQueryActorAverage o1,
                               final StoreQueryActorAverage o2) {
                int result = 0;
                if (!o1.getGrade().equals(o2.getGrade())) {
                    result = o1.getGrade().compareTo(o2.getGrade());
                } else {
                    result = o1.getNameActor().compareTo(o2.getNameActor());
                }
                return result;
            }
        });

        if (actionInputData.getSortType().equals("desc")) {
            Collections.reverse(nameActorAndGrade);
        }

        if (actionInputData.getNumber() < nameActorAndGrade.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                if (i == 0) {
                    message.append(nameActorAndGrade.get(i).getNameActor());
                } else {
                    message.append(", ").append(nameActorAndGrade.
                            get(i).getNameActor());
                }
            }
        } else {
            for (int i = 0; i < nameActorAndGrade.size(); i++) {
                if (i == 0) {
                    message.append(nameActorAndGrade.get(i).getNameActor());
                } else {
                    message.append(", ").append(nameActorAndGrade.
                            get(i).getNameActor());
                }
            }
        }

        message.append("]");
        result.setMessage(message);
        result.setId(actionInputData.getActionId());
        return result;
    }
}
