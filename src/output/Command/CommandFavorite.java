package output.Command;

import fileio.*;
import java.util.*;
import output.*;

public class CommandFavorite extends Command {
    @Override
    public Result command(ActionInputData actionInputData, List<UserInputData> userInputDataList
            , List<MovieInputData> movieInputDataList, List<SerialInputData> serialInputDataList) {
        Result result = new Result();
        StringBuffer message;
        for (UserInputData userInputData : userInputDataList) { // iterate every user if list
            if (actionInputData.getUsername().equals(userInputData.getUsername())) { // check if username is found or not
                int check_movie_in_history = 0;
                for (var history : userInputData.getHistory().entrySet()) { // iterate every movie in history list
                    if (history.getKey().equals(actionInputData.getTitle())) { // check if movie in history list
                        int check_movie_in_favorite = 0;
                        for (var favorite : userInputData.getFavoriteMovies()) { // iterate every movie in favorite list
                            if (favorite.equals(actionInputData.getTitle())) { // check if movie in favorite list
                                message = new StringBuffer("error -> ").append(favorite).append(" is already in favourite list");
                                result = new Result(actionInputData.getActionId(), message);
                                check_movie_in_favorite = 1;
                            }
                        }
                        if (check_movie_in_favorite == 0) { // succes
                            userInputData.getFavoriteMovies().add(actionInputData.getTitle());
                            message = new StringBuffer("success -> ").append(actionInputData.getTitle()).append(" was added as favourite");
                            result = new Result(actionInputData.getActionId(), message);
                        }
                        check_movie_in_history = 1;
                        break;
                    }
                }
                if (check_movie_in_history == 0) { // error
                    message = new StringBuffer("error -> ").append(actionInputData.getTitle()).append(" is not seen");
                    result = new Result(actionInputData.getActionId(), message);
                }
                break;
            }
        }
        return result;
    }

}
