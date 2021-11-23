package output.Command;

import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.ShowInput;
import fileio.UserInputData;
import java.util.*;
import output.Result;

public final class CommandRating extends Command {
    private final Map<MovieInputData, List<Double>> movieInputDataListMap = new HashMap<>();
    private final Map<SerialInputData, List<ActionInputData>>
            serialInputDataListMap = new HashMap<>();
    private final List<String> userNameActiv = new ArrayList<>();
    private final List<ActionInputData> actionOfUser = new ArrayList<>();
    private final Map<UserInputData, Map<ShowInput, ActionInputData>>
            unseenShowOfUser = new HashMap<>();

    public Map<UserInputData, Map<ShowInput, ActionInputData>> getUnseenShowOfUser() {
        return unseenShowOfUser;
    }


    public List<ActionInputData> getActionOfUser() {
        return actionOfUser;
    }

    public List<String> getUserNameActiv() {
        return userNameActiv;
    }

    public Map<SerialInputData, List<ActionInputData>> getSerialInputDataListMap() {
        return serialInputDataListMap;
    }

    public Map<MovieInputData, List<Double>> getMovieInputDataListMap() {
        return movieInputDataListMap;
    }

    /**
     * find the movie
     *
     * @param actionInputData
     * @param movieInputDataList
     * @param nameMovie
     */
    public void findMovie(final List<MovieInputData> movieInputDataList,
                          final ActionInputData actionInputData,
                          final String nameMovie) {
        List<Double> gradeList = new ArrayList<>();
        for (MovieInputData movieInputData : movieInputDataList) {
            if (movieInputData.getTitle().equals(nameMovie)) {
                if (this.movieInputDataListMap.containsKey(movieInputData)) {
                    gradeList.addAll(this.movieInputDataListMap.get(movieInputData));
                    gradeList.add(actionInputData.getGrade());
                    this.movieInputDataListMap.replace(movieInputData,
                            this.movieInputDataListMap.get(movieInputData), gradeList);
                } else {
                    gradeList.add(actionInputData.getGrade());
                    this.movieInputDataListMap.put(movieInputData, gradeList);
                }
                break;
            }
        }
    }

    /**
     * find the serial
     *
     * @param actionInputData
     * @param serialInputDataList
     * @param nameSerial
     */
    public void findSerial(final List<SerialInputData> serialInputDataList,
                           final ActionInputData actionInputData,
                           final String nameSerial) {
        List<ActionInputData> actionInputDataList = new ArrayList<>();
        for (SerialInputData serialInputData : serialInputDataList) {
            if (serialInputData.getTitle().equals(nameSerial)) {
                if (this.serialInputDataListMap.containsKey(serialInputData)) {
                    actionInputDataList.addAll(this.serialInputDataListMap.get(serialInputData));
                    actionInputDataList.add(actionInputData);
                    this.serialInputDataListMap.replace(serialInputData,
                            this.serialInputDataListMap.get(serialInputData), actionInputDataList);
                } else {
                    actionInputDataList.add(actionInputData);
                    this.serialInputDataListMap.put(serialInputData, actionInputDataList);
                }
                break;
            }
        }
    }

    /**
     * find the show unwatched
     *
     * @param serialInputDataList
     * @param movieInputDataList
     * @param nameUnseenShow
     */
    public ShowInput findUnseenShow(final List<MovieInputData> movieInputDataList,
                                    final List<SerialInputData> serialInputDataList,
                                    final String nameUnseenShow) {
        ShowInput showInput = null;
        for (MovieInputData movieInputData : movieInputDataList) {
            if (movieInputData.getTitle().equals(nameUnseenShow)) {
                showInput = movieInputData;
                break;
            }
        }
        for (SerialInputData serialInputData : serialInputDataList) {
            if (serialInputData.getTitle().equals(nameUnseenShow)) {
                showInput = serialInputData;
                break;
            }
        }
        return showInput;
    }

    /**
     * add show unwatched to list
     *
     * @param actionInputData
     * @param showInput
     * @param userInputData
     */
    public void addUnseenShow(final UserInputData userInputData,
                              final ShowInput showInput,
                              final ActionInputData actionInputData) {
        Map<ShowInput, ActionInputData> unseenShow = new HashMap<>();
        unseenShow.put(showInput, actionInputData);
        if (this.unseenShowOfUser.isEmpty()) {
            this.unseenShowOfUser.put(userInputData, unseenShow);
        } else {
            int checkUserFound = 0;
            for (var entry : this.unseenShowOfUser.entrySet()) {
                if (entry.getKey().getUsername().equals(userInputData.getUsername())) {
                    checkUserFound = 1;
                    entry.getValue().put(showInput, actionInputData);
                }
            }
            if (checkUserFound == 0) {
                this.unseenShowOfUser.put(userInputData, unseenShow);
            }
        }
    }

    /**
     * return result object
     *
     * @param actionInputData
     * @param movieInputDataList
     * @param serialInputDataList
     * @param userInputDataList
     */
    @Override
    public Result command(final ActionInputData actionInputData,
                          final List<UserInputData> userInputDataList,
                          final List<MovieInputData> movieInputDataList,
                          final List<SerialInputData> serialInputDataList) {
        Result result = new Result();
        StringBuffer message;
        for (UserInputData userInputData : userInputDataList) { // iterate every user in list
            if (this.userNameActiv.isEmpty()) {
                this.userNameActiv.add(userInputData.getUsername());
            } else {
                if (!this.userNameActiv.contains(userInputData.getUsername())) {
                    this.userNameActiv.add(userInputData.getUsername());
                }
            }
            if (actionInputData.getUsername().
                    equals(userInputData.getUsername())) {
                for (var history : userInputData.getHistory().entrySet()) {
                    if (history.getKey().equals(actionInputData.getTitle())) {
                        this.actionOfUser.add(actionInputData);
                        message = new StringBuffer("success -> ")
                                .append(actionInputData.getTitle()).append(" was rated with ")
                                .append(actionInputData.getGrade()).append(" by ")
                                .append(userInputData.getUsername());
                        // add movie rated to HashMap
                        findMovie(movieInputDataList, actionInputData,
                                actionInputData.getTitle());
                        findSerial(serialInputDataList, actionInputData,
                                actionInputData.getTitle());
                        result = new Result(actionInputData.getActionId(), message);
                    }
                }
                break;
            }
        }
        return result;
    }

    /**
     * check if current user rated
     *
     * @param actionInputDataList
     * @param currAction
     * @param currPosition
     */
    public boolean checkUserRated(final List<ActionInputData> actionInputDataList,
                                  final ActionInputData currAction,
                                  final int currPosition) {
        boolean ifUserRated = false;
        for (int i = 0; i < currPosition; i++) {
            if (actionInputDataList.get(i).getUsername() != null
                    && actionInputDataList.get(i).getTitle() != null) {
                if (actionInputDataList.get(i).getUsername().
                        equals(currAction.getUsername())
                        && actionInputDataList.get(i).getTitle().
                        equals(currAction.getTitle())
                        && actionInputDataList.get(i).getSeasonNumber()
                        == currAction.getSeasonNumber()) {
                    ifUserRated = true;
                    break;
                }
            }
        }
        return ifUserRated;
    }

    /**
     * return result object of rated user
     *
     * @param actionInputData
     */
    public Result commandUserRated(final ActionInputData actionInputData) {
        Result result;
        StringBuffer message;

        message = new StringBuffer("error -> ").
                append(actionInputData.getTitle()).
                append(" has been already rated");
        result = new Result(actionInputData.getActionId(), message);

        return result;
    }

    /**
     * check if current user watched
     *
     * @param currAction
     * @param userInputDataList
     */
    public boolean checkUserUnseen(final ActionInputData currAction,
                                   final List<UserInputData> userInputDataList) {
        boolean result = true;
        for (UserInputData userInputData : userInputDataList) {
            if (userInputData.getUsername().
                    equals(currAction.getUsername())) {
                for (var entry : userInputData.getHistory().entrySet()) {
                    if (entry.getKey().equals(currAction.getTitle())) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * return object result of watched user
     *
     * @param actionInputData
     * @param userInputDataList
     * @param input
     */
    public Result commandUserUnseen(final Input input,
                                    final ActionInputData actionInputData,
                                    final List<UserInputData> userInputDataList) {
        Result result;
        StringBuffer message;
        for (UserInputData userInputData : userInputDataList) {
            if (userInputData.getUsername().
                    equals(actionInputData.getUsername())) {
                ShowInput showInput = findUnseenShow(input.getMovies(),
                        input.getSerials(), actionInputData.getTitle());
                addUnseenShow(userInputData, showInput, actionInputData);
                break;
            }
        }
        message = new StringBuffer("error -> ").
                append(actionInputData.getTitle()).
                append(" is not seen");
        result = new Result(actionInputData.getActionId(), message);
        return result;
    }
}
