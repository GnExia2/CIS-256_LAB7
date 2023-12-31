/**
 * Description: Program is an implementation of AVL Tree with rotation, insertion, deletion, and search methods.
 * Methods are tested in main method to ensure AVL Tree remains balanced.
 *
 * @author Cailey Murad, Alex Giang, Garrick Ngai
 * @since 10/13/23
 */

/**
 * TreeNode class to represent individual nodes in AVL Tree.
 */
class TreeNode {
    String name;
    TreeNode left;
    TreeNode right;
    int height;

    // constructor
    public TreeNode(String name) {
        this.name = name;
        this.height = 1;
    }
}

/**
 * AVLTree class to represent AVL Tree, includes insertion, deletion, and search methods.
 */
public class AVLTree {
    private TreeNode root;

    /**
     * Get the height of a node
     *
     * @param node node to search for height of
     * @return height of a node
     */
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * Get the balance factor of a node
     *
     * @param node node to get the balance factor of
     * @return balance factor
     */
    private int getBalanceFactor(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    /**
     * Perform a right rotation
     *
     * @param y root node of AVL Tree
     * @return root node of AVL Tree with right rotation performed
     */
    private TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * Perform a left rotation
     *
     * @param x root node of AVL Tree
     * @return root node of AVL Tree with left rotation performed
     */
    private TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Perform a left-right rotation
     *
     * @param node root node of AVL tree
     * @return root node of AVL Tree with left-right rotation performed
     */
    private TreeNode leftrightRotate(TreeNode node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    /**
     * Perform a right-left rotation
     *
     * @param node root node of AVL tree
     * @return root node of AVL Tree with right-left rotation performed
     */
    private TreeNode rightleftRotate(TreeNode node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    /**
     * Insert a name into the AVL tree
     *
     * @param node root node of AVL Tree
     * @param name name to be inserted into tree
     * @return root node of AVL Tree with inserted node with name
     */
    public TreeNode insert(TreeNode node, String name) {
        if (node == null) {
            return new TreeNode(name);
        }

        int compareResult = name.compareTo(node.name);

        if (compareResult < 0) {
            node.left = insert(node.left, name);
        } else if (compareResult > 0) {
            node.right = insert(node.right, name);
        } else {
            // Duplicate names are not allowed
            return node;
        }

        // Update the height of the current node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor and perform rotations if needed
        int balance = getBalanceFactor(node);

        // Left-Left case
        if (balance > 1 && name.compareTo(node.left.name) < 0) {
            return rightRotate(node);
        }

        // Right-Right case
        if (balance < -1 && name.compareTo(node.right.name) > 0) {
            return leftRotate(node);
        }

        // Left-Right case
        if (balance > 1 && name.compareTo(node.left.name) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right-Left case
        if (balance < -1 && name.compareTo(node.right.name) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Search for a name in the AVL tree
     *
     * @param node root node of AVL tree
     * @param name name of node to be searched for
     * @return node with name or null if not found
     */
    public TreeNode search(TreeNode node, String name) {
        if (node == null || name.equals(node.name)) {
            return node;
        }

        int compareResult = name.compareTo(node.name);

        if (compareResult < 0) {
            return search(node.left, name);
        }

        return search(node.right, name);
    }

    /**
     * Delete a name from the AVL tree
     *
     * @param node root node of AVL Tree
     * @param name name of node to be deleted
     * @return root node of AVL Tree with node deleted
     */
    public TreeNode delete(TreeNode node, String name) {
        if (node == null) {
            return node;
        }

        int compareResult = name.compareTo(node.name);

        if (compareResult < 0) {
            node.left = delete(node.left, name);
        } else if (compareResult > 0) {
            node.right = delete(node.right, name);
        } else {
            if ((node.left == null) || (node.right == null)) {
                TreeNode temp = null;
                if (node.left != null) {
                    temp = node.left;
                } else {
                    temp = node.right;
                }

                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                TreeNode temp = findMin(node.right);
                node.name = temp.name;
                node.right = delete(node.right, temp.name);
            }
        }

        if (node == null) {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalanceFactor(node);

        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Find the minimum node in a subtree
     *
     * @param node root node of AVL Tree
     * @return minimum node in tree
     */
    private TreeNode findMin(TreeNode node) {
        TreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * In-order traversal of the AVL tree
     *
     * @param node root node of AVL tree
     */
    public void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.name + " ");
            inOrderTraversal(node.right);
        }
    }

    /**
     * Tree visualization of the AVL tree in the form of a string
     *
     * @return character array visualFrame to form visualization of AVL tree
     */
    public char[][] visual() {
        // an array of chars whose size grows exponentially from the tree height
        char[][] visualFrame = new char[root.height * 2 - 1][(int) Math.pow(2, root.height + 2) + 1];

        // first filling puts the root at the top middle of the frame, then its children
        // are placed in the next row, 1/4 of the frame width to the left and to the right respectively
        int shift = visualFrame[0].length / 4; // to be halved again later
        for (char[] row : visualFrame)
            java.util.Arrays.fill(row, ' '); // fill array with spaces instead of ugly null characters

        fillVisualFrame(visualFrame, root, 0, visualFrame[0].length / 2 - 4, shift);

        return visualFrame;
    }

    /**
     * Copies the name of a node into a frame starting at the specified row and column
     * in addition to its children to the left and right below, shifted by shift / 2
     *
     * @param frame    character array to form visualization of AVL tree
     * @param currNode root node of AVL tree
     * @param row      current row in frame
     * @param col      current col in frame
     * @param shift    amount to shift
     */
    public static void fillVisualFrame(char[][] frame, TreeNode currNode, int row, int col, int shift) {
        for (int i = 0; i < Math.min(7, currNode.name.length()); i++)
            frame[row][col + i] = currNode.name.charAt(i);
        // fill the names of the children with recursive calls:
        if (currNode.left != null) {
            frame[row + 1][col - shift / 2] = '/'; // add diagonal connectors
            fillVisualFrame(frame, currNode.left, row + 2, col - shift, shift / 2);
        }
        if (currNode.right != null) {
            frame[row + 1][col + shift / 2 + 2] = '\\';
            fillVisualFrame(frame, currNode.right, row + 2, col + shift, shift / 2);
        }
    }

    // main method
    public static void main(String[] args) {
        AVLTree avlTree;

        System.out.println("Demonstrating Rotations...");

        System.out.println("\nCase 1: Inserting node in left subtree.");
        avlTree = new AVLTree();
        avlTree.root = avlTree.insert(avlTree.root, "Alice");
        avlTree.root = avlTree.insert(avlTree.root, "Bob");

        for (char[] row : avlTree.visual())
            System.out.println(row);

        System.out.println("Inserting Charlie.");
        avlTree.root = avlTree.insert(avlTree.root, "Charlie");

        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("Balance factor = " + avlTree.getBalanceFactor(avlTree.root));

        System.out.println("\nCase 2: Inserting node in right subtree.");
        avlTree = new AVLTree();
        avlTree.root = avlTree.insert(avlTree.root, "Bob");
        avlTree.root = avlTree.insert(avlTree.root, "Charlie");

        for (char[] row : avlTree.visual())
            System.out.println(row);

        System.out.println("Inserting Alice.");
        avlTree.root = avlTree.insert(avlTree.root, "Alice");

        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("Balance factor = " + avlTree.getBalanceFactor(avlTree.root));

        System.out.println("\nCase 3: Deleting node in left subtree.");
        avlTree = new AVLTree();
        avlTree.root = avlTree.insert(avlTree.root, "Alice");
        avlTree.root = avlTree.insert(avlTree.root, "Bob");
        avlTree.root = avlTree.insert(avlTree.root, "Charlie");

        for (char[] row : avlTree.visual())
            System.out.println(row);

        System.out.println("Deleting Charlie.");
        avlTree.root = avlTree.delete(avlTree.root, "Charlie");

        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("Balance factor = " + avlTree.getBalanceFactor(avlTree.root));

        System.out.println("\nCase 4: Deleting node in right subtree.");
        avlTree = new AVLTree();
        avlTree.root = avlTree.insert(avlTree.root, "Alice");
        avlTree.root = avlTree.insert(avlTree.root, "Bob");
        avlTree.root = avlTree.insert(avlTree.root, "Charlie");

        for (char[] row : avlTree.visual())
            System.out.println(row);

        System.out.println("Deleting Alice.");
        avlTree.root = avlTree.delete(avlTree.root, "Alice");

        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("Balance factor = " + avlTree.getBalanceFactor(avlTree.root));


        System.out.println("\nTesting Implementation...");
        avlTree = new AVLTree();

        System.out.println("\nInserting 10 items into tree: ");

        avlTree.root = avlTree.insert(avlTree.root, "Alice");
        avlTree.root = avlTree.insert(avlTree.root, "Bob");
        avlTree.root = avlTree.insert(avlTree.root, "Charlie");
        avlTree.root = avlTree.insert(avlTree.root, "David");
        avlTree.root = avlTree.insert(avlTree.root, "Eve");
        avlTree.root = avlTree.insert(avlTree.root, "Frank");
        avlTree.root = avlTree.insert(avlTree.root, "Gemma");
        avlTree.root = avlTree.insert(avlTree.root, "Hannah");
        avlTree.root = avlTree.insert(avlTree.root, "Isabel");
        avlTree.root = avlTree.insert(avlTree.root, "Jack");

        // visual representation of tree
        for (char[] row : avlTree.visual())
            System.out.println(row);

        System.out.println("In-order traversal of AVL tree:");
        avlTree.inOrderTraversal(avlTree.root);

        System.out.println("\nDeleting 5 elements from tree: ");

        System.out.println("Deleting Alice.");
        avlTree.root = avlTree.delete(avlTree.root, "Alice");
        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("\nDeleting Charlie.");
        avlTree.root = avlTree.delete(avlTree.root, "Charlie");
        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("\nDeleting Eve.");
        avlTree.root = avlTree.delete(avlTree.root, "Eve");
        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("\nDeleting Gemma.");
        avlTree.root = avlTree.delete(avlTree.root, "Gemma");
        for (char[] row : avlTree.visual())
            System.out.println(row);
        System.out.println("\nDeleting Jack.");
        avlTree.root = avlTree.delete(avlTree.root, "Jack");
        for (char[] row : avlTree.visual())
            System.out.println(row);

        System.out.println("In-order traversal of AVL tree after deletion:");
        avlTree.inOrderTraversal(avlTree.root);

        System.out.println("\nSearching for 5 elements from tree: ");

        System.out.println("\nSearching for Alice.");
        String nameToSearch = "Alice";
        TreeNode result = avlTree.search(avlTree.root, nameToSearch);
        if (result != null) {
            System.out.println("Found name: " + nameToSearch);
        } else {
            System.out.println("Name " + nameToSearch + " not found");
        }

        System.out.println("\nSearching for Bob.");
        nameToSearch = "Bob";
        result = avlTree.search(avlTree.root, nameToSearch);
        if (result != null) {
            System.out.println("Found name: " + nameToSearch);
        } else {
            System.out.println("Name " + nameToSearch + " not found");
        }

        System.out.println("\nSearching for Charlie.");
        nameToSearch = "Charlie.";
        result = avlTree.search(avlTree.root, nameToSearch);
        if (result != null) {
            System.out.println("Found name: " + nameToSearch);
        } else {
            System.out.println("Name " + nameToSearch + " not found");
        }

        System.out.println("\nSearching for David.");
        nameToSearch = "David";
        result = avlTree.search(avlTree.root, nameToSearch);
        if (result != null) {
            System.out.println("Found name: " + nameToSearch);
        } else {
            System.out.println("Name " + nameToSearch + " not found");
        }

        System.out.println("\nSearching for Eve.");
        nameToSearch = "Eve";
        result = avlTree.search(avlTree.root, nameToSearch);
        if (result != null) {
            System.out.println("Found name: " + nameToSearch);
        } else {
            System.out.println("Name " + nameToSearch + " not found");
        }
    }
}
