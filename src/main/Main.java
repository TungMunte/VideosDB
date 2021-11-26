package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActorInputData;
import fileio.ActionInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import java.util.*;
import org.json.*;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import output.Command.CommandFavorite;
import output.Command.CommandRating;
import output.Command.CommandView;
import output.Query.QueryActorAverage;
import output.Query.QueryActorAwards;
import output.Query.QueryUserRating;
import output.Query.QueryVideoFavorite;
import output.Query.QueryActorFilterDescription;
import output.Query.QueryVideoLongest;
import output.Query.QueryVideoRating;
import output.Query.QueryVideoMostViewed;

import output.Recommend.RecommendSearch;
import output.Recommend.RecommendPopular;
import output.Recommend.RecommendStandard;
import output.Recommend.RecommendBestUnseen;
import output.Recommend.RecommendFavorite;

import output.Result;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        //TODO add here the entry point to your implementation

        List<ActionInputData> actionInputDataList = new ArrayList<>();
        List<ActorInputData> actorInputDataList = new ArrayList<>();
        List<MovieInputData> movieInputDataList = new ArrayList<>();
        List<SerialInputData> serialInputDataList = new ArrayList<>();
        List<UserInputData> userInputDataList = new ArrayList<>();

        if (!input.getActors().isEmpty()) {
            actorInputDataList.addAll(input.getActors());
        }
        if (!input.getCommands().isEmpty()) {
            actionInputDataList.addAll(input.getCommands());
        }
        if (!input.getMovies().isEmpty()) {
            movieInputDataList.addAll(input.getMovies());
        }
        if (!input.getSerials().isEmpty()) {
            serialInputDataList.addAll(input.getSerials());
        }
        if (!input.getUsers().isEmpty()) {
            userInputDataList.addAll(input.getUsers());
        }

        List<String> orderApprearance = new ArrayList<>();
        for (MovieInputData movieInputData : movieInputDataList) {
            orderApprearance.add(movieInputData.getTitle());
        }
        for (SerialInputData serialInputData : serialInputDataList) {
            orderApprearance.add(serialInputData.getTitle());
        }

        List<Result> resultList = new ArrayList<>();
        CommandFavorite commandFavorite = new CommandFavorite();
        CommandRating commandRating = new CommandRating();
        CommandView commandView = new CommandView();
        QueryActorAverage queryActorAverage = new QueryActorAverage();
        QueryActorAwards queryActorAwards = new QueryActorAwards();
        QueryActorFilterDescription queryActorFilterDescription = new QueryActorFilterDescription();
        QueryUserRating queryUserRating = new QueryUserRating();
        QueryVideoFavorite queryVideoFavorite = new QueryVideoFavorite();
        QueryVideoLongest queryVideoLongest = new QueryVideoLongest();
        QueryVideoMostViewed queryVideoMostViewed = new QueryVideoMostViewed();
        QueryVideoRating queryVideoRating = new QueryVideoRating();
        RecommendBestUnseen recommendBestUnseen = new RecommendBestUnseen();
        RecommendFavorite recommendFavorite = new RecommendFavorite();
        RecommendPopular recommendPopular = new RecommendPopular();
        RecommendSearch recommendSearch = new RecommendSearch();
        RecommendStandard recommendStandard = new RecommendStandard();

        for (int i = 0; i < actionInputDataList.size(); i++) {
            if (actionInputDataList.get(i).getActionType().equals("command")) {
                if (actionInputDataList.get(i).getType().equals("favorite")) {
                    resultList.add(commandFavorite.command(actionInputDataList.get(i),
                            userInputDataList, movieInputDataList, serialInputDataList));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.get(i).getMessage())));
                } else if (actionInputDataList.get(i).getType().equals("view")) {
                    resultList.add(commandView.command(actionInputDataList.get(i),
                            userInputDataList, movieInputDataList, serialInputDataList));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.get(i).getMessage())));
                } else if (actionInputDataList.get(i).getType().equals("rating")) {
                    if (commandRating.checkUserUnseen(actionInputDataList.get(i),
                            userInputDataList)) {
                        resultList.add(commandRating.commandUserUnseen(input,
                                actionInputDataList.get(i), userInputDataList));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.get(i).getMessage())));
                    } else if (commandRating.checkUserRated(actionInputDataList,
                            actionInputDataList.get(i), i)) {
                        resultList.add(commandRating.commandUserRated(actionInputDataList.get(i)));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.get(i).getMessage())));
                    } else {
                        resultList.add(commandRating.command(actionInputDataList
                                        .get(i), userInputDataList,
                                movieInputDataList, serialInputDataList));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.get(i).getMessage())));
                    }
                }
            } else if (actionInputDataList.get(i).getActionType().equals("query")) {
                if (actionInputDataList.get(i).getObjectType().equals("actors")) {
                    if (actionInputDataList.get(i).getCriteria().equals("average")) {
                        resultList.add(queryActorAverage.query(actionInputDataList.get(i), input));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.get(i).getMessage())));
                    } else if (actionInputDataList.get(i).getCriteria().equals("awards")) {
                        resultList.add(queryActorAwards.query(actionInputDataList.get(i), input));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.get(i)
                                        .getMessage())));
                    } else if (actionInputDataList.get(i).getCriteria().equals("filter_description")) {
                        resultList.add(queryActorFilterDescription.query(actionInputDataList.
                                get(i), input));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.get(i).getMessage())));
                    }
                } else if (actionInputDataList.get(i).getObjectType().equals("movies")
                        || actionInputDataList.get(i).getObjectType().equals("shows")) {
                    if (actionInputDataList.get(i).
                            getCriteria().equals("ratings")) {
                        queryVideoRating.setMovieInputDataListMap(commandRating.
                                getMovieInputDataListMap());
                        queryVideoRating.setSerialInputDataListMap(commandRating.
                                getSerialInputDataListMap());
                        resultList.add(queryVideoRating.query(actionInputDataList.
                                get(i), input));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.
                                        get(i).getMessage())));
                    } else if (actionInputDataList.get(i).
                            getCriteria().equals("favorite")) {
                        resultList.add(queryVideoFavorite.query(actionInputDataList.
                                get(i), input));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.get(i).getMessage())));
                    } else if (actionInputDataList.get(i).getCriteria().equals("longest")) {
                        resultList.add(queryVideoLongest.query(actionInputDataList.get(i), input));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.
                                        get(i).getMessage())));
                    } else if (actionInputDataList.get(i).
                            getCriteria().equals("most_viewed")) {
                        resultList.add(queryVideoMostViewed.query(actionInputDataList.
                                get(i), input));
                        arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                                "", new String(resultList.
                                        get(i).getMessage())));
                    }
                } else if (actionInputDataList.get(i).getObjectType().equals("users")) {
                    queryUserRating.setUserNameActiv(commandRating.getUserNameActiv());
                    queryUserRating.setActionOfUser(commandRating.getActionOfUser());
                    resultList.add(queryUserRating.query(actionInputDataList.
                            get(i), input));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.
                                    get(i).getMessage())));
                }
            } else if (actionInputDataList.get(i).getActionType().equals("recommendation")) {
                if (actionInputDataList.get(i).getType().equals("standard")) {
                    recommendStandard.setOrderApprearance(orderApprearance);
                    resultList.add(recommendStandard.recommend(actionInputDataList.
                            get(i), input));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.
                                    get(i).getMessage())));

                } else if (actionInputDataList.get(i).getType().equals("best_unseen")) {

                    recommendBestUnseen.setOrderApprearance(orderApprearance);
                    recommendBestUnseen.setUnseenShowOfUser(commandRating.
                            getUnseenShowOfUser());
                    resultList.add(recommendBestUnseen.recommend(actionInputDataList.
                            get(i), input));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.get(i).getMessage())));

                } else if (actionInputDataList.get(i).getType().equals("popular")) {

                    recommendPopular.setOrderApprearance(orderApprearance);
                    recommendPopular.setUnseenShowOfUser(commandRating.getUnseenShowOfUser());
                    resultList.add(recommendPopular.recommend(actionInputDataList.get(i), input));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.get(i).getMessage())));

                } else if (actionInputDataList.get(i).getType().equals("favorite")) {

                    recommendFavorite.setOrderApprearance(orderApprearance);
                    recommendFavorite.setUnseenShowOfUser(commandRating.getUnseenShowOfUser());
                    resultList.add(recommendFavorite.recommend(actionInputDataList.get(i), input));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.get(i).getMessage())));

                } else if (actionInputDataList.get(i).getType().equals("search")) {

                    recommendSearch.setUnseenShowOfUser(commandRating.getUnseenShowOfUser());
                    resultList.add(recommendSearch.recommend(actionInputDataList.get(i), input));
                    arrayResult.add(fileWriter.writeFile(resultList.get(i).getId(),
                            "", new String(resultList.get(i).getMessage())));

                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
