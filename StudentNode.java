import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.PrintWriter;

/**
 * a program that creates networks of nodes
 * network size and probability of node connection is specified by user
 * adjacency matrix for created network is written to a file whos path is
 * also specified by the user
 * @author Gianna Mule, 2017
 */
public class StudentNode {

    private static ArrayList<StudentNode> allStudents;
    private static int networkSize = 50;
    private static double probability = 0.3;
    private static Integer[][] A = new Integer[networkSize][networkSize];


    private ArrayList<StudentNode> connectedTo;
    private int id;

    /**
     * constructor for StudentNode objects
     * @param networkSize the size of the network the node is in; used to calculate initial importance
     * @param l the id of the node, for some reason i established this as a string, idk if it means anything
     */
    private StudentNode(int networkSize, int l) {
        connectedTo = new ArrayList<>();
        id = l;
    }

    /**
     *  create a new network "randomly"
     * @param prob the probability two nodes share a connection
     * @param students the list of students in the network
     */
    private void createNetwork(double prob, ArrayList<StudentNode> students) {
        for(StudentNode sn : students) {
            if ((this.id != sn.id) && (!this.connectedTo.contains(sn))) {
                double r = Math.random();
                System.out.println(r);
                if (r <= prob) {
                    this.connectedTo.add(sn);
                    sn.connectedTo.add(this);
                }
            }
        }
    }

    /**
     * fill static variable allStudents with new StudentNodes
     * send list into createNetwork
     */
    private static void fillStudents() {
        allStudents = new ArrayList<>();
        for(int i = 0; i < networkSize; i++) {
            allStudents.add(new StudentNode(networkSize,i));
        }
        for(StudentNode sn : allStudents) {
            sn.createNetwork(probability,allStudents);
        }
    }

    /**
     * create adjacency matrix from network
     */
    private static void createMatrix() {
        for(int r = 0; r < networkSize; r++) {
            for(int c = 0; c < networkSize; c++) {
                A[r][c] = 0;
            }
        }
        for(StudentNode parent : allStudents) { //parent.id is row
            for(StudentNode child : parent.connectedTo) { //child.id is column
                A[parent.id][child.id] = 1;
            }
        }
    }

    /**
     * writes matrix to specified file
     * @param pw the PrintWriter in charge of writing matrix
     */
    private static void printMatrix(PrintWriter pw) {
        for(Integer[] row : A) {
            for(Integer i : row) {
                pw.print("" + i + " ");
            }
            pw.println();
        }
    }

    public static void main(String[] args) {
        allStudents = new ArrayList<>();
        fillStudents();
        createMatrix();
        String pathname = "PATH NAME HERE";
        try {
            PrintWriter writer = new PrintWriter(new File(pathname));
            printMatrix(writer);
            writer.flush();
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
