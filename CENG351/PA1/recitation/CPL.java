package ceng.ceng351.recitation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author Alperen Dalkiran
 * @project ceng 
 *
 */
public class CPL implements ICPL{
	
    private static String user = "e1234567";// username;
    private static String password = "dummyPassword"; // password;
    private static String host = "momcorp.ceng.metu.edu.tr"; //"host name";
    private static String database = "db1234567";// database name;
    private static int port = 8080; // 8080;
     
    private Connection con;

    public CPL() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.con =  DriverManager.getConnection(url, this.user, this.password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } 
    }
	
    @Override
    public int dropTables() {

        int result;
        int numberofTablesDropped = 0;

        //Player(number:integer, teamname:char(20), name:char(30), age:integer, position:char(3))
        String queryDropPlayerTable = "drop table if exists Player";


        try {
            Statement statement = this.con.createStatement();

            //Team Player
            result = statement.executeUpdate(queryDropPlayerTable);
            numberofTablesDropped++;
            System.out.println(result);

            //close
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberofTablesDropped;
    }

    @Override
    public int createTables() {

        int result;
        int numberofTablesInserted = 0;


        //Player(number:integer, teamname:char(20), name:char(30), age:integer, position:char(3))
        String queryCreatePlayerTable = "create table Player (" + 
                                                                   "number int not null," + 
                                                                   "teamname char(20) not null," + 
                                                                   "name char(30)," +
                                                                   "age int," +
                                                                   "position char(3)," +
                                                                   "primary key (number,teamName))";
        try {
            Statement statement = this.con.createStatement();

            //Player Table
            result = statement.executeUpdate(queryCreatePlayerTable);
            System.out.println(result);
            numberofTablesInserted++;

            //close
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberofTablesInserted;
    }

	
    @Override
    public void insertPlayer(Player player) {
        int result = 0;

        //new Player(11, "Barcelona", "Neymar", 22, "LWF"),
        String query = "insert into Player values ('" + 
                        player.getNumber()+ "','" + 
                        player.getTeamname() + "','" + 
                        player.getName() + "','" + 
                        player.getAge() + "','" + 
                        player.getPosition() + "')";

        try {
            Statement st = this.con.createStatement();
            result = st.executeUpdate(query);
            System.out.println(result);

            //Close
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player getPlayer(int number, String teamname) {

        ResultSet rs;
        Player player  = null;

        String query = "select * from Player where number =" +
                                        number + 
                                        " and teamname = '" +  
                                        teamname + "';";

        try {
            Statement st = this.con.createStatement();
            rs = st.executeQuery(query);	

            //int number, String teamname, String name, int age, String position
            //First call method next() to make the first row current row.
            rs.next();

            int m_number = rs.getInt("number");
            String m_teamname = rs.getString("teamname");
            String m_name = rs.getString("name");
            int m_age = rs.getInt("age");
            String m_position = rs.getString("position");

            player = new Player(m_number,m_teamname, m_name, m_age, m_position);

            //Close
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

}
