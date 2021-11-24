package output.Command;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.List;
import output.Result;

public final class CommandFavorite extends Command {
    @Override
    public Result command(final ActionInputData actionInputData,
                          final List<UserInputData> userInputDataList,
                          final List<MovieInputData> movieInputDataList,
                          final List<SerialInputData> serialInputDataList) {
        Result result = new Result();
        StringBuffer message;
        for (UserInputData userInputData : userInputDataList) {
            if (actionInputData.getUsername()
                    .equals(userInputData.getUsername())) {
                int checkMovieInHistory = 0;
                for (var history : userInputData
                        .getHistory().entrySet()) {
                    if (history.getKey()
                            .equals(actionInputData.getTitle())) {
                        int checkMovieInFavorite = 0;
                        for (var favorite : userInputData
                                .getFavoriteMovies()) {
                            if (favorite.equals(actionInputData.getTitle())) {
                                message = new StringBuffer("error -> ").
                                        append(favorite).append(" is already in favourite list");
                                result = new Result(actionInputData.getActionId(), message);
                                checkMovieInFavorite = 1;
                            }
                        }
                        if (checkMovieInFavorite == 0) { // succes
                            userInputData.getFavoriteMovies().add(actionInputData.getTitle());
                            message = new StringBuffer("success -> ").
                                    append(actionInputData.getTitle()).
                                    append(" was added as favourite");
                            result = new Result(actionInputData.getActionId(), message);
                        }
                        checkMovieInHistory = 1;
                        break;
                    }
                }
                if (checkMovieInHistory == 0) { // error
                    message = new StringBuffer("error -> ").
                            append(actionInputData.getTitle()).
                            append(" is not seen");
                    result = new Result(actionInputData.getActionId(), message);
                }
                break;
            }
        }
        return result;
    }

}
