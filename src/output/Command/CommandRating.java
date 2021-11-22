package output.Command;

import fileio.*;
import java.util.*;
import output.*;

public class CommandRating extends Command {
    private Map<MovieInputData, List<Double>> movieInputDataListMap = new HashMap<>();
    private Map<SerialInputData, List<ActionInputData>> serialInputDataListMap = new HashMap<>();
    private List<String> userNameActiv = new ArrayList<>();
    private List<ActionInputData> actionOfUser = new ArrayList<>();
    private Map<UserInputData, Map<ShowInput, ActionInputData>> unseenShowOfUser = new HashMap<>();

    public Map<UserInputData, Map<ShowInput, ActionInputData>> getUnseenShowOfUser() {
        return unseenShowOfUser;
    }

    public void setUnseenShowOfUser(Map<UserInputData, Map<ShowInput, ActionInputData>> unseenShowOfUser) {
        this.unseenShowOfUser = unseenShowOfUser;
    }

    public List<ActionInputData> getActionOfUser() {
        return actionOfUser;
    }

    public void setActionOfUser(List<ActionInputData> actionOfUser) {
        this.actionOfUser = actionOfUser;
    }

    public List<String> getUserNameActiv() {
        return userNameActiv;
    }

    public void setUserNameActiv(List<String> userNameActiv) {
        this.userNameActiv = userNameActiv;
    }

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

    /**
     * @param actionInputData
     * @param movieInputDataList
     * @param nameMovie
     * @brief : find MovieInputData object from list
     * add object, grade to movieInputDataListMap
     */
    public void findMovie(List<MovieInputData> movieInputDataList, ActionInputData actionInputData, String nameMovie) {
        List<Double> gradeList = new ArrayList<>();
        for (MovieInputData movieInputData : movieInputDataList) {
            if (movieInputData.getTitle().equals(nameMovie)) {
                if (this.movieInputDataListMap.containsKey(movieInputData) == true) {
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
     * @param actionInputData
     * @param serialInputDataList
     * @param nameSerial
     * @brief : find SerialInputData object from list
     * add object, grade to serialInputDataListMap
     */
    public void findSerial(List<SerialInputData> serialInputDataList, ActionInputData actionInputData, String nameSerial) {
        List<ActionInputData> actionInputDataList = new ArrayList<>();
        for (SerialInputData serialInputData : serialInputDataList) {
            if (serialInputData.getTitle().equals(nameSerial)) {
                if (this.serialInputDataListMap.containsKey(serialInputData) == true) {
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

    public ShowInput findUnseenShow(List<MovieInputData> movieInputDataList, List<SerialInputData> serialInputDataList, String nameUnseenShow) {
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

    public void addUnseenShow(UserInputData userInputData, ShowInput showInput, ActionInputData actionInputData) {
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
     * @param actionInputData
     * @param userInputDataList
     * @param movieInputDataList
     * @param serialInputDataList
     */
    @Override
    public Result command(ActionInputData actionInputData, List<UserInputData> userInputDataList
            , List<MovieInputData> movieInputDataList, List<SerialInputData> serialInputDataList) {
        Result result = new Result();
        StringBuffer message;
        for (UserInputData userInputData : userInputDataList) { // iterate every user in list
            if (this.userNameActiv.isEmpty()) {
                this.userNameActiv.add(userInputData.getUsername());
            } else {
                if (this.userNameActiv.contains(userInputData.getUsername()) == false) {
                    this.userNameActiv.add(userInputData.getUsername());
                }
            }
            if (actionInputData.getUsername().equals(userInputData.getUsername())) { // check if username is found
                for (var history : userInputData.getHistory().entrySet()) { // iterate every movie in history
                    if (history.getKey().equals(actionInputData.getTitle())) { // check if movie is found
                        this.actionOfUser.add(actionInputData);
                        message = new StringBuffer("success -> ")
                                .append(actionInputData.getTitle()).append(" was rated with ")
                                .append(actionInputData.getGrade()).append(" by ")
                                .append(userInputData.getUsername());
                        // add movie rated to HashMap
                        findMovie(movieInputDataList, actionInputData, actionInputData.getTitle());
                        findSerial(serialInputDataList, actionInputData, actionInputData.getTitle());
                        result = new Result(actionInputData.getActionId(), message);
                    }
                }
                break;
            }
        }
        return result;
    }

    public boolean checkUserRated(List<ActionInputData> actionInputDataList, ActionInputData currAction, int currPosition) {
        boolean IfUserRated = false;
        for (int i = 0; i < currPosition; i++) {
            if (actionInputDataList.get(i).getUsername().equals(currAction.getUsername())
                    && actionInputDataList.get(i).getTitle().equals(currAction.getTitle())
                    && actionInputDataList.get(i).getSeasonNumber() == currAction.getSeasonNumber()) {
                IfUserRated = true;
                break;
            }
        }
        return IfUserRated;
    }

    public Result commandUserRated(ActionInputData actionInputData) {
        Result result;
        StringBuffer message;

        message = new StringBuffer("error -> ").append(actionInputData.getTitle()).append(" has been already rated");
        result = new Result(actionInputData.getActionId(), message);

        return result;
    }

    public boolean checkUserUnseen(ActionInputData currAction, List<UserInputData> userInputDataList) {
        boolean result = true;
        for (UserInputData userInputData : userInputDataList) {
            if (userInputData.getUsername().equals(currAction.getUsername())) {
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

    public Result commandUserUnseen(Input input, ActionInputData actionInputData, List<UserInputData> userInputDataList) {
        Result result;
        StringBuffer message;
        for (UserInputData userInputData : userInputDataList) {
            if (userInputData.getUsername().equals(actionInputData.getUsername())) {
                ShowInput showInput = findUnseenShow(input.getMovies(), input.getSerials(), actionInputData.getTitle());
                addUnseenShow(userInputData, showInput, actionInputData);
                break;
            }
        }
        message = new StringBuffer("error -> ").append(actionInputData.getTitle()).append(" is not seen");
        result = new Result(actionInputData.getActionId(), message);
        return result;
    }
}
