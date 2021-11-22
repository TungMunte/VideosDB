package output.Query;

import fileio.*;
import output.*;

public abstract class Query {
    abstract Result query(ActionInputData actionInputData, Input input);
}
