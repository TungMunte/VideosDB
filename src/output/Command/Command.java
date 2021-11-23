package output.Command;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.*;
import output.Result;

public abstract class Command {

    abstract Result command(ActionInputData actionInputData,
                            List<UserInputData> userInputDataList,
                            List<MovieInputData> movieInputDataList,
                            List<SerialInputData> serialInputDataList);

}
