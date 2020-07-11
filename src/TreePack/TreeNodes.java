package TreePack;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @author bockey
 */
public class TreeNodes {
    int val;
    TreeNodes leftChild;
    TreeNodes rightChild;

    public TreeNodes(int val) {
        this.val = val;
    }

    public static TreeNodes createBinaryTree(LinkedList<Integer> list) {
        TreeNodes node = null;
        if (list == null || list.isEmpty()) {
            return null;
        }
        Integer val = list.removeFirst();
        if (val != null) {
            node = new TreeNodes(val);
            node.leftChild = createBinaryTree(list);
            node.rightChild = createBinaryTree(list);
        }
        return node;
    }

    public static void preOrderTraversal(TreeNodes nodes) {
        if (nodes == null) {
            return;
        }
        System.out.println(nodes.val);
        preOrderTraversal(nodes.leftChild);
        preOrderTraversal(nodes.rightChild);
    }

    public static void midOrderTraversal(TreeNodes node) {
        if (node == null) {
            return;
        }
        midOrderTraversal(node.leftChild);
        System.out.print(node.val);
        midOrderTraversal(node.rightChild);
    }

    public static void postOrderTraversal(TreeNodes node) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.leftChild);
        postOrderTraversal(node.rightChild);
        System.out.print(node.val);
    }

//    先看非递归前序遍历
//    首先申请一个新的栈，记为stack；
//    声明一个结点treeNode，让其指向node结点；
//    如果treeNode的不为空，将treeNode的值打印，并将treeNode入栈，然后让treeNode指向treeNode的右结点，
//    重复步骤3，直到treenode为空；
//    然后出栈，让treeNode指向treeNode的右孩子
//    重复步骤3，直到stack为空.

    public static void preOrderTraveralWithStack(TreeNodes node) {
        Stack<TreeNodes> stack = new Stack<TreeNodes>();
        TreeNodes treeNode = node;
        while (treeNode != null || !stack.isEmpty()) {
            //迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                System.out.print(treeNode.val);
                stack.push(treeNode);
                treeNode = treeNode.leftChild;
            }
            //如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                treeNode = treeNode.rightChild;
            }
        }
    }

//    具体过程：
//
//    申请一个新栈，记为stack，申请一个变量cur，初始时令treeNode为头节点；
//    先把treeNode节点压入栈中，对以treeNode节点为头的整棵子树来说，依次把整棵树的左子树压入栈中，即不断令treeNode=treeNode.leftChild，然后重复步骤2；
//    不断重复步骤2，直到发现cur为空，此时从stack中弹出一个节点记为treeNode，打印node的值，并让treeNode= treeNode.right，然后继续重复步骤2；
//    当stack为空并且cur为空时结束。
    public static void inOrderTraveralWithStack(TreeNodes node) {
        Stack<TreeNodes> stack = new Stack<TreeNodes>();
        TreeNodes treeNode = node;
        while (treeNode != null || !stack.isEmpty()) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.leftChild;
            }
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                System.out.print(treeNode.val);
                treeNode = treeNode.rightChild;
            }

        }
    }

    public static void postOrderTraveralWithStack(TreeNodes node) {
        Stack<TreeNodes> stack = new Stack<TreeNodes>();
        TreeNodes treeNode = node;
        TreeNodes lastVisit = null;   //标记每次遍历最后一次访问的节点
        while (treeNode != null || !stack.isEmpty()) {//节点不为空，结点入栈，并且指向下一个左孩子
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.leftChild;
            }
            //栈不为空
            if (!stack.isEmpty()) {
                //出栈
                treeNode = stack.pop();
                /**
                 * 这块就是判断treeNode是否有右孩子，
                 * 如果没有输出treeNode.data，让lastVisit指向treeNode，并让treeNode为空
                 * 如果有右孩子，将当前节点继续入栈，treeNode指向它的右孩子,继续重复循环
                 */
                if (treeNode.rightChild == null || treeNode.rightChild == lastVisit) {
                    System.out.print(treeNode.val + " ");
                    lastVisit = treeNode;
                    treeNode = null;
                } else {
                    stack.push(treeNode);
                    treeNode = treeNode.rightChild;
                }
            }
        }
    }

//    再给大家介绍一下层序遍历
//    具体步骤如下：
//    首先申请一个新的队列，记为queue；
//    将头结点head压入queue中；
//    每次从queue中出队，记为node，然后打印node值，如果node左孩子不为空，则将左孩子入队；如果node的右孩子不为空，则将右孩子入队；
//    重复步骤3，直到queue为空。

    public static void levelOrder(TreeNodes root) {
        LinkedList<TreeNodes> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.pop();
            System.out.print(root.val + " ");
            if (root.leftChild != null) queue.add(root.leftChild);
            if (root.rightChild != null) queue.add(root.rightChild);
        }
    }
}
