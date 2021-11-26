package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import fileio.ActorInputData;
import java.util.*;
import output.Result;

public final class QueryActorFilterDescription extends Query {
    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        List<String> actorList = new ArrayList<>();
        String[] actorListToSort;
        List<String> orderedActorList = new ArrayList<>();

        for (ActorInputData actorInputData : input.getActors()) {
            int checkMatchCareers = 0;
            StringBuffer addString = new StringBuffer(" ");
            for (int i = 0; i < actionInputData.getFilters().get(2).size(); i++) {
                if (actorInputData.getCareerDescription().toLowerCase().
                        contains(addString.append(actionInputData.getFilters().get(2).get(i)).append(" "))) {
                    checkMatchCareers++;
                    addString.delete(0, addString.length());
                }
            }
            if (checkMatchCareers == actionInputData.getFilters().get(2).size()) {
                actorList.add(actorInputData.getName());
            }
        }
        actorListToSort = actorList.toArray(new String[0]);
        if (actionInputData.getSortType().equals("asc")) {
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
