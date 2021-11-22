package output.Command;

import fileio.*;
import java.util.*;
import output.*;

public abstract class Command {

    abstract Result command(ActionInputData actionInputData, List<UserInputData> userInputDataList,
                            List<MovieInputData> movieInputDataList, List<SerialInputData> serialInputDataList);

}
