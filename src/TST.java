import java.util.*;

/**
 * Based on princeton's implementation of a TST adapted to suit this project. I removed much of the
 * flexibility that princeton's had as it was unnecessary for this project as we would only be dealing with
 * strings.
 */

public class TST {
    private int size;
    private Node<String> root;

    private static class Node<String> {
        private char c;
        private Node<String> left, mid, right;
        private String val;
    }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size;
    }

    /**
     * @return true if key exists
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol
     *         table and {@code null} if the key is not in the symbol table
     */
    public String get(String key) {
        if (key == null) {
            System.out.println("Key should not be null");
        } else if (key.length() == 0) {
            System.out.println("Key of invalid size!");
        }
        Node<String> x = get(root, key, 0);
        return x == null ? null : x.val;
    }

    // Return subtrie corresponding to given key
    public Node<String> get(Node<String> x, String key, int d) {

        if (x == null) {
            return null;
        }
        if (key.length() == 0) {
            System.out.println("Key of invalid size!");
        }
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        }
        else if (c > x.c) {
            return get(x.right, key, d);
        }
        else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        }

        else {
            return x;
        }
    }

    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table. If the value is
     * {@code null}, this effectively deletes the key from the symbol table.
     *
     * @param key the key
     * @param val the value
     */
    public void put(String val, String key) {
        if (val == null) {
            System.out.println("Key should not be null");
        }
        if (!contains(key)) {
            size++;
            root = put(root, key, val, 0);
        }
    }

    private Node<String> put(Node<String> x, String key, String val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node<>();
            x.c = c;
        }

        if (c < x.c)
            x.left = put(x.left, key, val, d);
        else if (c > x.c)
            x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid, key, val, d + 1);
        else
            x.val = val;
        return x;
    }

    /**
     * Returns all of the keys in the set that start with {@code prefix}.
     *
     * @param prefix the prefix
     * @return all of the values in the set that start with {@code prefix}, as a
     *         list
     */
    public ArrayList<String> valuesWithPrefix(String prefix) {

        if (prefix == null) {
            ArrayList<String> nullEntered = new ArrayList<>();
            nullEntered.add("The string entered was null");
            return nullEntered;
        }

        ArrayList<String> values = new ArrayList<>();

        Node<String> x = get(root, prefix, 0);

        if (x == null) {
            return values;
        } else if (x.val != null) {
            values.add(x.val);
        }
        collect(x.mid, new StringBuilder(prefix), values);
        if (values.get(0) == null) {
            ArrayList<String> notRecogArrayList = new ArrayList<>();
            notRecogArrayList.add("The stop address was not recognised");
            return notRecogArrayList;
        }
        return values;
    }

    // All values in subtrie rooted at x with given prefix
    public void collect(Node<String> x, StringBuilder prefix, List<String> values) {
        if (x == null)
            return;
        collect(x.left, prefix, values);
        if (x.val != null)
            values.add(x.val);
        collect(x.mid, prefix.append(x.c), values);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, values);
    }
}