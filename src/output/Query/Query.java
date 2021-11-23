package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import output.Result;

public abstract class Query {
    abstract Result query(ActionInputData actionInputData, Input input);
}
