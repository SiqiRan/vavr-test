package io.vavr;

import io.vavr.experiment.Execution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerformanceExperimentTest {

    ArrayList<Integer> arrayList = new ArrayList<>();
    LinkedList<Integer> linkedList = new LinkedList<>();
    io.vavr.collection.List<Integer> vavrList = io.vavr.collection.List.of();
    ArrayList<Object> headSingle;
    ArrayList<Object> headTwoParams;
    List<Object> modifyHead;
    List<Object> modifyMiddle;
    List<Object> modifyTail;
    List<Object> getHead;
    List<Object> getMiddle;
    List<Object> getTail;
    List<Execution> arrayExecutionList = new ArrayList<>();
    List<Execution> linkedExecutionList = new LinkedList<>();
    List<Execution> vavrExecutionList = new ArrayList<>();


    long TIMES = 10000;

    PerformanceExperimentTest() {
        modifyHead = new ArrayList<>(Arrays.asList(0, 1));
        modifyMiddle = new ArrayList<>(Arrays.asList((int) (TIMES / 2), 1));
        modifyTail = new ArrayList<>(Arrays.asList((int) (TIMES - 1), 1));
        getMiddle = new ArrayList<>(Collections.singletonList((int) (TIMES / 2)));
        getHead = new ArrayList<>(Collections.singletonList(0));
        getTail = new ArrayList<>(Collections.singletonList((int)TIMES - 1));
        headTwoParams = new ArrayList<>(Arrays.asList(0, 1));
        headSingle = new ArrayList<>(Collections.singletonList(0));

    }

    @BeforeEach
    void setUp() {
        clearAllDataStructure();
        arrayExecutionList.clear();
        vavrExecutionList.clear();
        vavrList.append(1);
    }

    private void clearAllDataStructure() {
        linkedList.clear();
        arrayList.clear();
        vavrList = io.vavr.collection.List.of();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testInsertPerformance() throws InvocationTargetException, IllegalAccessException {
        arrayExecutionList.add(new Execution("insert head", headTwoParams, arrayList, TIMES));
        arrayExecutionList.add(new Execution("insert middle", headTwoParams, arrayList, TIMES));
        arrayExecutionList.add(new Execution("insert tail", headSingle, arrayList, TIMES));
        linkedExecutionList.add(new Execution("insert head", headTwoParams, linkedList, TIMES));
        linkedExecutionList.add(new Execution("insert middle", headTwoParams, linkedList, TIMES));
        linkedExecutionList.add(new Execution("insert tail", headSingle, linkedList, TIMES));
        vavrExecutionList.add(new Execution("insert head", headSingle, vavrList, TIMES));
        vavrExecutionList.add(new Execution("insert middle", headTwoParams, vavrList, TIMES));
        vavrExecutionList.add(new Execution("insert tail", headSingle, vavrList, TIMES));
        for (int i = 0; i < 3; i++) {
            arrayList.clear();
            linkedList.clear();
            List<Integer> arrayListResult = (List<Integer>) arrayExecutionList.get(i).execute();
            List<Integer> linkedListResult = (List<Integer>) linkedExecutionList.get(i).execute();
            io.vavr.collection.List<Integer> vavrListResult = (io.vavr.collection.List<Integer>) vavrExecutionList.get(i).execute();
            vavrListResult.append(5);
            assertEquals(arrayListResult.size(), TIMES);
            assertEquals(linkedListResult.size(), TIMES);
            assertEquals(vavrListResult.length(), TIMES);
        }
    }

    @Test
    void testRemovePerformance() throws InvocationTargetException, IllegalAccessException {
        fillVavrList();
        arrayExecutionList.add(new Execution("remove head", headTwoParams, arrayList, TIMES));
        arrayExecutionList.add(new Execution("remove middle", headTwoParams, arrayList, TIMES));
        arrayExecutionList.add(new Execution("remove tail", headSingle, arrayList, TIMES));
        linkedExecutionList.add(new Execution("remove head", headTwoParams, linkedList, TIMES));
        linkedExecutionList.add(new Execution("remove middle", headTwoParams, linkedList, TIMES));
        linkedExecutionList.add(new Execution("remove tail", headSingle, linkedList, TIMES));
        vavrExecutionList.add(new Execution("remove head", headSingle, vavrList, TIMES));
        vavrExecutionList.add(new Execution("remove middle", headSingle, vavrList, TIMES));
        vavrExecutionList.add(new Execution("remove tail", headSingle, vavrList, TIMES));
        for (int i = 0; i < 3; i++) {
            fillArrayList();
            fillLinkedList();
            List<Integer> arrayListResult = (List<Integer>) arrayExecutionList.get(i).execute();
            List<Integer> linkedListResult = (List<Integer>) linkedExecutionList.get(i).execute();
            io.vavr.collection.List<Integer> vavrListResult = (io.vavr.collection.List<Integer>) vavrExecutionList.get(i).execute();
            assertEquals(arrayListResult.size(), 0);
            assertEquals(vavrListResult.length(), 0);
            assertEquals(linkedListResult.size(), 0);
        }
    }

    private void fillArrayList() {
        for (long l = 0; l < TIMES; l++) {
            arrayList.add(1);
        }
    }

    private void fillVavrList() {
        for (long l = 0; l < TIMES; l++) {
            vavrList = vavrList.append(1);
        }
    }

    private void fillLinkedList() {
        for (long l = 0; l < TIMES; l++) {
            linkedList.add(1);
        }
    }

    @Test
    void testQueryPerformance() throws InvocationTargetException, IllegalAccessException {
        fillVavrList();
        fillArrayList();
        fillLinkedList();
        arrayExecutionList.add(new Execution("get head", headSingle, arrayList, TIMES));
        arrayExecutionList.add(new Execution("get middle", getMiddle, arrayList, TIMES));
        arrayExecutionList.add(new Execution("get tail", getTail,arrayList, TIMES));
        linkedExecutionList.add(new Execution("get head", getHead, linkedList, TIMES));
        linkedExecutionList.add(new Execution("get middle", getMiddle, linkedList, TIMES));
        linkedExecutionList.add(new Execution("get tail", getTail, linkedList, TIMES));
        vavrExecutionList.add(new Execution("get head", getHead, vavrList, TIMES));
        vavrExecutionList.add(new Execution("get middle", getMiddle, vavrList, TIMES));
        vavrExecutionList.add(new Execution("get tail", getTail, vavrList, TIMES));
        for (int i = 0; i < 3; i++) {
            List<Integer> arrayListResult = (List<Integer>) arrayExecutionList.get(i).execute();
            List<Integer> linkedListResult = (List<Integer>) linkedExecutionList.get(i).execute();
            io.vavr.collection.List<Integer> vavrListResult = (io.vavr.collection.List<Integer>) vavrExecutionList.get(i).execute();
            assertEquals(arrayListResult.size(), TIMES);
            assertEquals(vavrListResult.length(), TIMES);
            assertEquals(linkedListResult.size(), TIMES);
        }
    }

    @Test
    void testModifyPerformance() throws InvocationTargetException, IllegalAccessException {
        fillVavrList();
        fillArrayList();
        fillLinkedList();
        arrayExecutionList.add(new Execution("modify head", modifyHead, arrayList, TIMES));
        arrayExecutionList.add(new Execution("modify middle", modifyMiddle, arrayList, TIMES));
        arrayExecutionList.add(new Execution("modify tail", modifyTail,arrayList, TIMES));
        linkedExecutionList.add(new Execution("modify head", modifyHead, linkedList, TIMES));
        linkedExecutionList.add(new Execution("modify middle", modifyMiddle, linkedList, TIMES));
        linkedExecutionList.add(new Execution("modify tail", modifyTail, linkedList, TIMES));
        vavrExecutionList.add(new Execution("modify head", modifyHead, vavrList, TIMES));
        vavrExecutionList.add(new Execution("modify middle", modifyMiddle, vavrList, TIMES));
        vavrExecutionList.add(new Execution("modify tail", modifyTail, vavrList, TIMES));
        for (int i = 0; i < 3; i++) {
            List<Integer> arrayListResult = (List<Integer>) arrayExecutionList.get(i).execute();
            List<Integer> linkedListResult = (List<Integer>) linkedExecutionList.get(i).execute();
            io.vavr.collection.List<Integer> vavrListResult = (io.vavr.collection.List<Integer>) vavrExecutionList.get(i).execute();
            assertEquals(arrayListResult.size(), TIMES);
            assertEquals(vavrListResult.length(), TIMES);
            assertEquals(linkedListResult.size(), TIMES);
        }
    }

}