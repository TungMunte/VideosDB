package output.Recommend;

import fileio.ActionInputData;
import fileio.Input;
import output.Result;

public abstract class Recommend {
    abstract Result recommend(ActionInputData actionInputData, Input input);
}
