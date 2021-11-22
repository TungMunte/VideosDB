package output.Command;

import fileio.*;
import java.util.*;
import output.*;

public class CommandView extends Command {
    @Override
    public Result command(ActionInputData actionInputData, List<UserInputData> userInputDataList
            , List<MovieInputData> movieInputDataList, List<SerialInputData> serialInputDataList) {
        StringBuffer message;
        Result result = new Result();
        for (UserInputData userInputData : userInputDataList) { // iterate every user in list
            if (userInputData.getUsername().equals(actionInputData.getUsername())) { // check if username is found
                int check_movie_in_history = 0;
                for (var history : userInputData.getHistory().entrySet()) { // iterate every movie in history list
                    if (history.getKey().equals(actionInputData.getTitle())) {
                        history.setValue(history.getValue() + 1);
                        message = new StringBuffer("success -> ").append(actionInputData.getTitle())
                                .append(" was viewed with total views of ")
                                .append(history.getValue());
                        result = new Result(actionInputData.getActionId(), message);
                        check_movie_in_history = 1;
                        break;
                    }
                }
                if (check_movie_in_history == 0) {
                    userInputData.getHistory().put(actionInputData.getTitle(), 1);
                    message = new StringBuffer("success -> ").append(actionInputData.getTitle())
                            .append(" was viewed with total views of ")
                            .append("1");
                    result = new Result(actionInputData.getActionId(), message);
                }
                break;
            }
        }
        return result;
    }
}
