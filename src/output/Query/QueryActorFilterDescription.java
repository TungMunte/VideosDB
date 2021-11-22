package output.Query;

import fileio.*;
import java.util.*;
import output.*;

public class QueryActorFilterDescription extends Query {
    @Override
    public Result query(ActionInputData actionInputData, Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<String> actorList = new ArrayList<>();
        String[] actorListToSort;
        List<String> orderedActorList = new ArrayList<>();
        for (ActorInputData actorInputData : input.getActors()) {
            int checkMatchCareers = 0;
            for (int i = 0; i < actionInputData.getFilters().get(2).size(); i++) {
                if (actorInputData.getCareerDescription().contains(actionInputData.getFilters().get(2).get(i)) == true) {
                    checkMatchCareers++;
                }
            }
            if (checkMatchCareers == actionInputData.getFilters().get(2).size()) {
                actorList.add(actorInputData.getName());
            }
        }
        actorListToSort = actorList.toArray(new String[0]);
        if (actionInputData.getSortType().equals("acs")) {
            Arrays.sort(actorListToSort);
        } else {
            Arrays.sort(actorListToSort, Collections.reverseOrder());
        }
        orderedActorList.addAll(Arrays.asList(actorListToSort));
        for (int i = 0; i < orderedActorList.size(); i++) {
            if (i == 0) {
                message.append(orderedActorList.get(i));
            } else {
                message.append(", ").append(orderedActorList.get(i));
            }
        }
        message.append("]");
        result.setId(actionInputData.getActionId());
        result.setMessage(message);
        return result;
    }
}