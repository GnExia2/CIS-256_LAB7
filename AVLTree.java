class TreeNode {
    String name;
    TreeNode left;
    TreeNode right;
    int height;

    public TreeNode(String name) {
        this.name = name;
        this.height = 1;
    }
}


public class AVLTree {
    private TreeNode root;

    // Get the height of a node
    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    // Get the balance factor of a node
    private int getBalanceFactor(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    // Perform a right rotation
    private TreeNode rightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Perform a left rotation
    private TreeNode leftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Perform a leftright rotation
    private TreeNode leftrightRotate(TreeNode node) {
        node.left = leftRotate(node.left);
        return rightRotate(node);
    }

    // Perform a rightleft rotation
    private TreeNode rightleftRotate(TreeNode node) {
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }

    // Insert a name into the AVL tree
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

    // Search for a name in the AVL tree
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

    // Delete a name from the AVL tree
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

    // Find the minimum node in a subtree
    private TreeNode findMin(TreeNode node) {
        TreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // In-order traversal of the AVL tree
    public void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.name + " ");
            inOrderTraversal(node.right);
        }
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        avlTree.root = avlTree.insert(avlTree.root, "Alice");
        avlTree.root = avlTree.insert(avlTree.root, "Bob");
        avlTree.root = avlTree.insert(avlTree.root, "Charlie");
        avlTree.root = avlTree.insert(avlTree.root, "David");
        avlTree.root = avlTree.insert(avlTree.root, "Eve");
        avlTree.root = avlTree.insert(avlTree.root, "Frank");

        System.out.println("In-order traversal of AVL tree:");
        avlTree.inOrderTraversal(avlTree.root); // Should print the sorted order of inserted names

        String nameToSearch = "Charlie";
        TreeNode result = avlTree.search(avlTree.root, nameToSearch);
        if (result != null) {
            System.out.println("\nFound name: " + nameToSearch);
        } else {
            System.out.println("\nName " + nameToSearch + " not found");
        }

        System.out.println("Deleting David from the tree.");
        avlTree.root = avlTree.delete(avlTree.root, "David");

        System.out.println("In-order traversal of AVL tree after deletion:");
        avlTree.inOrderTraversal(avlTree.root); // Should print the sorted order after the deletion
    }
}
