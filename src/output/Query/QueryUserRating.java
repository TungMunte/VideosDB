package output.Query;

import fileio.ActionInputData;
import fileio.Input;
import java.util.*;
import java.util.stream.Collectors;
import output.Result;

public final class QueryUserRating extends Query {
    private List<String> userNameActiv = new ArrayList<>();
    private List<ActionInputData> actionOfUser = new ArrayList<>();

    public void setActionOfUser(final List<ActionInputData> actionOfUser) {
        this.actionOfUser = actionOfUser;
    }

    public void setUserNameActiv(final List<String> userNameActiv) {
        this.userNameActiv = userNameActiv;
    }

    @Override
    public Result query(final ActionInputData actionInputData, final Input input) {
        Result result = new Result();
        StringBuffer message = new StringBuffer().append("Query result: [");
        Map<String, Integer> unsortedNameUser = new HashMap<>();
        Map<String, Integer> sortedNameUser;
        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < this.userNameActiv.size(); i++) {
            int countAppearance = 0;
            for (ActionInputData actionOfUser : this.actionOfUser) {
                if (actionOfUser.getUsername().equals(this.userNameActiv.get(i))) {
                    countAppearance++;
                }
            }
            if (countAppearance != 0) {
                unsortedNameUser.put(this.userNameActiv.get(i), countAppearance);
            }
        }

        if (actionInputData.getSortType().equals("acs")) {
            sortedNameUser = unsortedNameUser.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        } else {
            sortedNameUser = unsortedNameUser.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.
                            toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        }

        for (var entry : sortedNameUser.entrySet()) {
            nameList.add(entry.getKey());
        }

        if (actionInputData.getNumber() < sortedNameUser.size()) {
            for (int i = 0; i < actionInputData.getNumber(); i++) {
                if (i == 0) {
                    message.append(nameList.get(i));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        } else {
            for (int i = 0; i < sortedNameUser.size(); i++) {
                if (i == 0) {
                    message.append(nameList.get(i));
                } else {
                    message.append(", ").append(nameList.get(i));
                }
            }
        }

        message.append("]");
        result.setId(actionInputData.getActionId());
        result.setMessage(message);
        return result;
    }
}
