// Xi, He(22872431), ZiHan Zhang (23194782)

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Cargo problem from the 2022 CITS2200 Project
 */
public class CargoImpl implements Cargo {
    class Node implements Comparable<Node> {
        private Query query;
        private int index; // queryIndex

        public Node(Query query, int index) {
            this.query = query;
            this.index = index;
        }

        /**
         * Node's sorting method, first sort by collect, if the same, then sort by index
         * @param o
         * @return
         */
        @Override
        public int compareTo(Node o) {
            int result = Integer.compare(query.collect, o.query.collect);
            if (result == 0) {
                result = Integer.compare(index, o.index);
            }
            return result;
        }
    }

    /**
     * {@inheritdoc}
     */
    public int[] departureMasses(int stops, Query[] queries) {
        // The query saved by nodes, all nodes are sorted by query.collect
        List<Node> nodes = new ArrayList<>(queries.length);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            Query query = queries[i];
            if (query.collect == query.deliver) {
                query.cargoMass = 0;
            }
            Node node = new Node(query, i);
            // Binary search should be inserted into the position, and then insert the new node for
            addNode(nodes, node);

            // The actual order of query indexes in nodes after sorting
            int[] sortIndexArray = new int[queries.length];
            for (int j = 0; j < nodes.size(); j++) {
                Node tmpNode = nodes.get(j);
                sortIndexArray[tmpNode.index] = j;
            }

            // Calculate the query that has been executed up to the current query index, (in fact, some of the previous queries in the queries cannot be executed because they have not yet reached that place)
            int collect = query.collect;
            int sortIndex = sortIndexArray[i];
            int mass = query.cargoMass;
            for (int j = 0; j < sortIndex; j++) {
                Query tmp = nodes.get(j).query;
                if (tmp.deliver > collect) {
                    mass += tmp.cargoMass;
                }
            }

            result[i] = mass;

        }

        return result;
    }

    /**
     * Binary search for the insertion index of the new node
     * @param nodes
     * @param node
     */
    private void addNode(List<Node> nodes, Node node) {
        if (nodes.size() == 0) {
            nodes.add(node);
        } else if (node.compareTo(nodes.get(0)) < 0) {
            nodes.add(0, node);
        } else if (node.compareTo(nodes.get(nodes.size() - 1)) > 0) {
            nodes.add(node);
        } else {
            int start = 0;
            int end = nodes.size() - 1;
            while (start + 1 < end) {
                int mid = (start + end) / 2;
                Node tmp = nodes.get(mid);
                if (tmp.compareTo(node) > 0) {
                    end = mid;
                } else {
                    start = mid;
                }
            }
            nodes.add(end, node);
        }
    }
}