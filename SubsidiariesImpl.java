// Xi, He(22872431), ZiHan Zhang (23194782)

import java.util.*;

/**
 * An implementation of the Subsidiaries problem from the 2022 CITS2200 Project
 */
public class SubsidiariesImpl implements Subsidiaries {
    class Company {
        private int id;
        private int root = -1;
        private int height = 1;
        private Company parent;
        private List<Company> childCompanyList = new ArrayList<>();

        public Company(int id) {
            this.id = id;
        }
    }

    /**
     * {@inheritdoc}
     */
    public int[] sharedOwners(int[] owners, Query[] queries) {
        // TODO: Implement your solution
        int[] result = new int[queries.length];
        // Build the tree, all the maps of each company id and company
        Map<Integer, Company> companyMap = parseCompanyTree(owners);
        for (int i = 0; i < queries.length; i++) {
            Query query = queries[i];
            Company one = companyMap.get(query.payer);
            Company two = companyMap.get(query.payee);
            result[i] = findNearestParent(one, two);
//            System.out.println((i + 1) + ": " + result[i]);
        }
        return result;
    }

    /**
     * smallest parent company
     *
     * @param one
     * @param two
     * @return
     */
    private int findNearestParent(Company one, Company two) {
        if (one.root != two.root) {
            // If they do not belong to the same top parent company, there is no solution
            return -1;
        }

        Set<Integer> parentPathIds1 = new HashSet<>();
        Set<Integer> parentPathIds2 = new HashSet<>();

        Company companyTmp1 = one;
        Company companyTmp2 = two;
        // The two subsidiaries start looking for the parent company level by level until they find a parent company that belongs to both companies
        while (companyTmp1 != null || companyTmp2 != null) {
            if (companyTmp1 != null) {
                int path1 = companyTmp1.id;
                if (parentPathIds2.contains(path1)) {
                    return path1;
                }
                parentPathIds1.add(path1);
                companyTmp1 = companyTmp1.parent;
            }

            if (companyTmp2 != null) {
                int path2 = companyTmp2.id;
                if (parentPathIds1.contains(path2)) {
                    return path2;
                }
                parentPathIds2.add(path2);
                companyTmp2 = companyTmp2.parent;
            }
        }

        return -1;
    }

    /**
     * Group all companies and their subsidiaries into a tree structure
     *
     * @param owners
     * @return
     */
    private Map<Integer, Company> parseCompanyTree(int[] owners) {
        Map<Integer, Company> companyMap = new HashMap<>();
        // Initialize all companies first
        for (int id = 0; id < owners.length; id++) {
            // Company
            Company company = companyMap.computeIfAbsent(id, Company::new);
            int parentId = owners[id];
            if (parentId != -1) {
                // a subsidiary company
                Company parentCompany = companyMap.computeIfAbsent(parentId, Company::new);
                parentCompany.childCompanyList.add(company);
                company.parent = parentCompany;
            } else {
                // If it is the top parent company
                company.root = company.id;
            }
        }

        // The root of all subsidiaries of the same top-level parent company is set to the same parent company id (if two subsidiaries have the same rootid, there is a solution)
        for (Company company : companyMap.values()) {
            if (company.parent == null) {
                company.root = company.id;
                Queue<Company> queue = new LinkedList<>();
                queue.add(company);
                while (!queue.isEmpty()) {
                    Company current = queue.poll();
                    for (Company child : current.childCompanyList) {
                        child.root = current.root;
                        child.height = current.height + 1;
                        queue.add(child);
                    }
                }
            }
        }

        return companyMap;
    }


}