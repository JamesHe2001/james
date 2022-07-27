// Xi, He(22872431), ZiHan Zhang (23194782)
import java.util.*;
import java.util.List;

/**
 * An implementation of the Shallows problem from the 2022 CITS2200 Project
 */
public class ShallowsImpl implements Shallows {

    class Path {
        Lane lane;
        int depth;

        public Path(Lane lane, int depth) {
            this.lane = lane;
            this.depth = depth;
        }
    }

    /**
     * {@inheritdoc}
     */
    public int[] maximumDraughts(int ports, Lane[] lanes, int origin) {
        // Use a priority queue, starting with the shallowest path each time

        int[] result = new int[ports];
        result[origin] = Integer.MAX_VALUE;
        // All paths to other ports from the initial port with max shallow
        Map<Integer, List<Lane>> portLanesMap = portLanes(lanes);
        // All the above paths are put into the priority queue, sorted in shallow reverse order
        PriorityQueue<Path> queue = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.depth, o1.depth));
        Set<Integer> visitPorts = new HashSet<>();
        for (Lane lane : portLanesMap.getOrDefault(origin, new ArrayList<>())) {
            queue.add(new Path(lane, lane.depth));
        }
        visitPorts.add(origin);

        // Start traversing until all ports have access paths
        while (!queue.isEmpty()) {
            Path path = queue.poll();
            Lane lane = path.lane;
            int target = lane.arrive;
            // Save the maximum depth of the target port
            result[target] = Math.max(result[target], path.depth);

            if (visitPorts.contains(target)) {
                continue;
            }
            visitPorts.add(target);

            // The path from the current port to other ports is added to the queue
            for (Lane laneTmp : portLanesMap.getOrDefault(target, new ArrayList<>())) {
                if (visitPorts.contains(laneTmp.arrive)) {
                    continue;
                }
                int depth = Math.min(path.depth, laneTmp.depth);
                queue.add(new Path(laneTmp, depth));
            }
        }

        return result;
    }


    private Map<Integer, List<Lane>> portLanes(Lane[] lanes) {
        Map<Integer, List<Lane>> map = new HashMap<>();
        for (int i = 0; i < lanes.length; i++) {
            Lane lane = lanes[i];
            List<Lane> list = map.getOrDefault(lane.depart, new ArrayList<>());
            list.add(lane);
            map.put(lane.depart, list);
        }
        return map;
    }
}
