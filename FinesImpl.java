// Xi, He(22872431), ZiHan Zhang (23194782)

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the Fines problem from the 2022 CITS2200 Project
 */
public class FinesImpl implements Fines {
    /**
     * {@inheritdoc}
     */

    class Node{

        private int value;
        private Node next;
    }

    public int countFines(int[] priorities) {
        // TODO: Implement your solution
        // One by one comparison, O(n2)
        int count = 0;
        for (int i = 0; i < priorities.length; i++) {
            int priority = priorities[i];
            for (int j = i + 1; j < priorities.length; j++) {
                if (priority < priorities[j]) {
                    count += 1;
                }
            }
        }

        return count;

    }
//    public int countFines(int[] priorities) {
//        // TODO: Implement your solution
//        int[][] arrays = new int[priorities.length][2];
//        for (int i = 0; i < priorities.length; i++) {
//            arrays[i][0] = i;
//            arrays[i][1] = priorities[i];
//        }
//        Arrays.sort(arrays, (o1, o2) -> Integer.compare(o2[1], o1[1]));
//        LinkedList<int[]> linkedList = new LinkedList<>(Arrays.asList(arrays));
//
//
//        int count = 0;
//        for (int i = 0; i < priorities.length; i++) {
//            Iterator<int[]> iterator = linkedList.iterator();
//            while (iterator.hasNext()) {
//                int[] item = iterator.next();
//                if (item[0] <= i) {
//                    iterator.remove();
//                    if (item[0] == i) {
//                        break;
//                    }
//                } else {
//                    count++;
//                }
//            }
//        }
//        return count;
//    }


//    public int countFines(int[] priorities) {
//        // TODO: Implement your solution
//        int[] rightMaxPriorities = new int[priorities.length];
//        for (int i = 0; i < priorities.length; i++) {
//            rightMaxPriorities[i] = priorities[i];
//        }
//        for (int i = priorities.length - 2; i >= 0; i--) {
//            if (priorities[i] < rightMaxPriorities[i + 1]) {
//                rightMaxPriorities[i] = rightMaxPriorities[i + 1];
//            }
//        }
//
//        int count = 0;
//        for (int i = 0; i < priorities.length; i++) {
//            for (int j = i + 1; j < priorities.length; j++) {
//                if (priorities[i] >= rightMaxPriorities[j]) {
//                    break;
//                }
//                if (priorities[i] < priorities[j]) {
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
}