package output.Recommend;

import fileio.*;
import output.*;

public abstract class Recommend {
    abstract Result recommend(ActionInputData actionInputData, Input input);
}
