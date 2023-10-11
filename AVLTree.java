public class AVLTree {
    private TreeNode root;

    // Rotation methods (leftRotate and rightRotate)
    private TreeNode leftRotate(TreeNode y) {
        // Implementation of leftRotate
    }

    private TreeNode rightRotate(TreeNode x) {
        // Implementation of rightRotate
    }

    // Node insertion and search methods
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
    
}
