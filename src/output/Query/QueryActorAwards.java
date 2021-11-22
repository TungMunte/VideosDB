package output.Query;

import fileio.*;
import java.util.*;
import java.util.stream.*;
import output.*;

public class QueryActorAwards extends Query {
    @Override
    public Result query(ActionInputData actionInputData, Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        Map<String, Integer> actorList = new HashMap<>();
        Map<String, Integer> arrangedActorList;
        List<String> actorsName = new ArrayList<>();

        for (ActorInputData actorInputData : input.getActors()) {
            if (actorInputData.getAwards().size() == actionInputData.getFilters().get(3).size()) {
                int numberOfAwards = 0;
                int check_match_awards = 0;
                for (int i = 0; i < actorInputData.getAwards().size(); i++) {
                    for (int j = 0; j < actorInputData.getAwards().size(); j++) {
                        if (actorInputData.getAwards().get(i).equals(actionInputData.getFilters().get(3).get(j))) {
                            check_match_awards++;
                            break;
                        }
                    }
                }
                if (check_match_awards == actorInputData.getAwards().size()) {
                    for (int i = 0; i < actorInputData.getAwards().size(); i++) {
                        numberOfAwards += actorInputData.getAwards().get(actionInputData.getFilters().get(3).get(i));
                    }
                }
                actorList.put(actorInputData.getName(), numberOfAwards);
            }
        }
        if (actionInputData.getSortType().equals("asc")) {
            arrangedActorList = actorList.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            arrangedActorList = actorList.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }
        for (var entry : arrangedActorList.entrySet()) {
            actorsName.add(entry.getKey());
        }
        for (int i = 0; i < actorsName.size(); i++) {
            if (i == 0) {
                message.append(actorsName.get(i));
            } else {
                message.append(", ").append(actorsName.get(i));
            }
        }
        message.append("]");
        result.setMessage(message);
        result.setId(actionInputData.getActionId());
        return result;
    }
}
