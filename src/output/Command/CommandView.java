package output.Command;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.*;
import output.Result;

public final class CommandView extends Command {
    @Override
    public Result command(final ActionInputData actionInputData,
                          final List<UserInputData> userInputDataList,
                          final List<MovieInputData> movieInputDataList,
                          final List<SerialInputData> serialInputDataList) {
        StringBuffer message;
        Result result = new Result();
        for (UserInputData userInputData
                : userInputDataList) {
            if (userInputData.getUsername().
                    equals(actionInputData.getUsername())) {
                int checkMovieInHistory = 0;
                for (var history : userInputData.
                        getHistory().entrySet()) {
                    if (history.getKey().equals(actionInputData.getTitle())) {
                        history.setValue(history.getValue() + 1);
                        message = new StringBuffer("success -> ").
                                append(actionInputData.getTitle()).
                                append(" was viewed with total views of ").
                                append(history.getValue());
                        result = new Result(actionInputData.getActionId(), message);
                        checkMovieInHistory = 1;
                        break;
                    }
                }
                if (checkMovieInHistory == 0) {
                    userInputData.getHistory().
                            put(actionInputData.getTitle(), 1);
                    message = new StringBuffer("success -> ").
                            append(actionInputData.getTitle()).
                            append(" was viewed with total views of ").
                            append("1");
                    result = new Result(actionInputData.getActionId(), message);
                }
                break;
            }
        }
        return result;
    }
}
