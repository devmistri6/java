import java.sql.*;
import java.util.Scanner;

public class VotingSys {
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);
    static int bjp = 0, congress = 0, aap = 0, bsp = 0;

    public static void main(String[] args) {
        VotingSys vs = new VotingSys();
        vs.createConnection();
    }

    public void createConnection() {
        while (true) {

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/btech71", "root", "");
                //System.out.println("Conncted Successfully");

                System.out.println("\n1. for the vote");
                System.out.println("2. for result ");
                System.out.println("3. show voted list");
                System.out.println("4. exit");
                System.out.println("Enter Choice :");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: {
                        validation();
                        // giveVote();
                        break;
                    }
                    case 2: {
                        electionResult();
                        break;
                    }
                    case 3: {
                        showVotedList();
                        break;
                    }
                    case 4: {
                        System.exit(0);
                    }
                    default: {
                        System.out.println("Invalid Input");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void giveVote() {
        try {

            System.out.println("\n********Welcome to the simple voting system project********\n");
            System.out.println("                         GUJARAT ELECTION                    \n");
            System.out.println("*************************************************************");
            System.out.println("|           1.BJP             |          2.Congress         |");
            System.out.println("*************************************************************");
            System.out.println("|           3.APP             |          4.BSP              |");
            System.out.println("*************************************************************\n\n");

            System.out.println("\nBJP- NARENDRBHAI MODI\n");
            System.out.println("CONGRESS- RAHUL GANDHI\n");
            System.out.println("AAP- KEJRIVAL\n");
            System.out.println("BSP- AADARSH VASANI\n\n\n");

            System.out.println("Press 1 to vote BJP");
            System.out.println("Press 2 to vote Congress");
            System.out.println("Press 3 to vote AAP");
            System.out.println("Press 4 to vote BSP");
            // System.out.println("Press 5 to show election result");
            System.out.print("Please choose : \n");
            int choose = sc.nextInt();
            // if (choose == 5) {
            // // e.electionResult();
            // } else
            if (choose <= 0 || choose > 4) {
                System.out.println("please enter the valisd entry!");
            } else {
                // e.calculateVote(choose);
            }
            switch (choose) {
                case 1:
                    bjp += 1;
                    break;
                case 2:
                    congress += 1;
                    break;
                case 3:
                    aap += 1;
                    break;
                case 4:
                    bsp += 1;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public void validation() {
    // try {
    // sc.nextLine();
    // System.out.println("enter the voter id to give a vote:");
    // String voter_id = sc.nextLine();
    // stmt = con.createStatement();
    // String query = "select * from election where voter_id='" + voter_id + "'";
    // rs = stmt.executeQuery(query);

    // if (rs.next()) {
    // giveVote();
    // //String query1 = "update election set voted_id='"+voter_id"' where voted id
    // is NULL";
    // String query1 = "UPDATE election SET voted_id = '" + voter_id + "' WHERE
    // voted_id IS NULL";

    // pstmt = con.prepareStatement(query1);
    // pstmt.executeUpdate();
    // pstmt.setString(1, "" + voter_id);
    // // String checkvoteId = rs.getString("voted_id");
    // // if (checkvoteId == voter_id) {
    // // System.out.println("You can't vote again");
    // } else {
    // System.out.println(" incorrect voter id");

    // }
    // // } else {
    // // System.out.println(" incorrect voter id");
    // // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    public void validation() {
        int rowsUpdated = 0; // Declare rowsUpdated outside the try block.
        try {
            sc.nextLine();
            System.out.println("Enter the voter id to give a vote:");
            String voter_id = sc.nextLine();
            stmt = con.createStatement();
            String query = "SELECT * FROM election WHERE voter_id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, voter_id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String checkQuery = "INSERT INTO voted (voter_id1) VALUES (?)";
                pstmt = con.prepareStatement(checkQuery);
                pstmt.setString(1, voter_id);
                rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated == 0) {
                    // No rows were affected by the INSERT statement, indicating a duplicate entry.
                    System.out.println("You can't vote again.");
                }
                giveVote();
            } else {
                System.out.println("\nIncorrect voter id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showVotedList() {
        try {
            stmt = con.createStatement();
            String query = "SELECT voter_id1 FROM voted";
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println("\nVoters who have already voted:");
                System.out.println(rs.getString("voter_id1"));
            } else {
                System.out.println("no candidate voted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void electionResult() {
        try {

            int wonByVote;

            if (bjp > congress && bjp > aap && bjp > bsp) {
                System.out.println("\n**BJP won the election**\n");
                System.out.println("Total vote of BJP: " + bjp);
                wonByVote = bjp - congress;
                System.out.println("BJP won by " + wonByVote + " votes to Congress");
                wonByVote = bjp - aap;
                System.out.println("BJP won by " + wonByVote + " votes to AAP");
                wonByVote = bjp - bsp;
                System.out.println("BJP won by " + wonByVote + " votes to BSP");

                System.out.println("            BJP---Congress---AAP---BSP");
                System.out.println("Total Vote   " + bjp + "       " + congress + "        " + aap + "     " + bsp);
            } else if (congress > aap && congress > bsp && congress > bjp) {
                System.out.println("\n**Congress won the election**\n");
                System.out.println("Total vote of Congress: " + congress);
                wonByVote = congress - bjp;
                System.out.println("Congress won by " + wonByVote + " votes to BJP");
                wonByVote = congress - aap;
                System.out.println("Congress won by " + wonByVote + " votes to AAP");
                wonByVote = congress - bsp;
                System.out.println("Congress won by " + wonByVote + " votes to BSP");

                System.out.println("            BJP---Congress---AAP---BSP");
                System.out.println("Total Vote   " + bjp + "       " + congress + "        " + aap + "     " + bsp);
            } else if (aap > bsp && aap > bjp && aap > congress) {
                System.out.println("\n**AAP won the election**\n");
                System.out.println("Total vote of AAP: " + aap);
                wonByVote = aap - congress;
                System.out.println("AAP won by " + wonByVote + " votes to Congress");
                wonByVote = aap - bjp;
                System.out.println("AAP won by " + wonByVote + " votes to BJP");
                wonByVote = aap - bsp;
                System.out.println("AAP won by " + wonByVote + " votes to BSP");

                System.out.println("            BJP---Congress---AAP---BSP");
                System.out.println("Total Vote   " + bjp + "       " + congress + "        " + aap + "     " + bsp);
            } else if (bsp > aap && bsp > bjp && bsp > congress) {
                System.out.println("\n**BSP won the election**\n");
                System.out.println("Total vote of BSP: " + bsp);
                wonByVote = bsp - congress;
                System.out.println("BSP won by " + wonByVote + " votes to Congress");
                wonByVote = bsp - bjp;
                System.out.println("BSP won by " + wonByVote + " votes to BJP");
                wonByVote = bsp - aap;
                System.out.println("BSP won by " + wonByVote + " votes to AAP");

                System.out.println("           BJP---Congress---AAP---BSP");
                System.out.println("Total Vote   " + bjp + "       " + congress + "        " + aap + "     " + bsp);
            }
            if (bjp == congress || bjp == aap || bjp == bsp) {
                System.out.println("\nNo one won the election\n\n");
                System.out.println("            BJP---Congress---AAP---BSP");
                System.out.println("Total Vote   " + bjp + "       " + congress + "        " + aap + "     " + bsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}