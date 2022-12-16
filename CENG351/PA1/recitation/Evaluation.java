package ceng.ceng351.recitation;

/**
 * @author Alperen Dalkiran
 * @project ceng 
 *
 */
public class Evaluation {

    public static void main(String args[]) {

        int total = -1;

        CPL cpl = new CPL();

        /****************************************************/
        cpl.initialize();
        /****************************************************/

        /****************************************************/
        // Drop tables
        try {
            total = cpl.dropTables();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Check if tables are dropped
        System.out.println("Dropped " + total + " tables: \n");
        /****************************************************/


        /****************************************************/
        // Create tables
        try {
            total = cpl.createTables();
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Check if tables are created
        System.out.println("Created " + total + " tables: \n");
        /****************************************************/

        /***********************************************************/
        //Insert a single player
        try {
            cpl.insertPlayer(new Player(11, "Liverpool", "Salah", 26, "CM"));
            cpl.insertPlayer(new Player(10, "Barcelona", "Messi", 31, "CF"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        /***********************************************************/


        /***********************************************************/
        try{
            Player p = cpl.getPlayer(10, "Barcelona");
            if(p != null){
                System.out.println("Player 1:\n" + p.toString());
            }

        } catch(Exception e) {
            System.out.println("Exception occured: \n\n" + e.toString());
        }
        /***********************************************************/

        /***********************************************************/
        //cpl.releaseDatabase();
        /***********************************************************/

    }

}
