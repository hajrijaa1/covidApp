/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class Konekcija {
      private static final String korisnik = "root";
    private static final String sifra = "root";
    private static final String kon = "jdbc:mysql://localhost:3306/covid?serverTimezone=UTC";
    public Connection veza = null;
    public Konekcija() throws ClassNotFoundException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            veza = DriverManager.getConnection(kon,korisnik,sifra);
        }
        catch(SQLException e){
            System.err.println(e);
        }
    }
    
    
      public void unosPacijenta (String ime, String prezime, int godiste) throws SQLException{
        Statement upitBaza = (Statement) veza.createStatement();
        String upit = "INSERT INTO Pacijent (ime, prezime, godiste) VALUES ('"+ime+"', '"+prezime+"', '"+godiste+"')";
        try{
            upitBaza.executeUpdate(upit);
        } 
        catch (SQLException e) {
            System.err.println(e);
        }
    }
      
       public void unosVakcine(String naziv, int kolicina) throws SQLException{
        Statement upitBaza = (Statement) veza.createStatement();
        String upit = "INSERT INTO Vakcina (naziv, kolicina) VALUES ('"+naziv+"', '"+kolicina+"')";
        try{
            upitBaza.executeUpdate(upit);
        } 
        catch (SQLException e) {
            System.err.println(e);
        }
    }
       
         public void unosVakcineSkolicinom(String naziv, int kolicina) throws SQLException{
        Statement upitBaza = (Statement) veza.createStatement();
        String upit = "INSERT INTO Vakcina (naziv, kolicina) VALUES ('"+naziv+"', '"+kolicina+"')";
        try{
            upitBaza.executeUpdate(upit);
        } 
        catch (SQLException e) {
            System.err.println(e);
        }
    }
         
         
    public ResultSet IspisPacijenata() throws SQLException 
    {
        Statement upitBaza=(Statement) veza.createStatement();
        ResultSet rezultat=null;
        try
        {
            rezultat=upitBaza.executeQuery("select ime, prezime, godiste from Pacijent where termin1 is null or termin2 is null or vakcinisan is null"); 
            return rezultat;
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
        return rezultat;
    }
    
       public void unosTermina(LocalDate termin1, LocalDate termin2, int vakcina) throws SQLException{
        Date d=Date.valueOf(termin1);
        Date d2=Date.valueOf(termin2);
        Statement upitBaza = (Statement) veza.createStatement();
        String upit = "INSERT INTO Vakcina (termin1, termin2, vakcina) VALUES ('"+termin1+"', '"+termin2+"', '"+vakcina+"')";
        try{
            upitBaza.executeUpdate(upit);
        } 
        catch (SQLException e) {
            System.err.println(e);
        }
        
         /*Statement statement1 = veza.createStatement();
         String sql1 = "update Vakcina set termin1 = ?, termin2 = ?, vakcina = ?"; 
         statement1.executeUpdate(sql1);*/
         }
       
    
    public ResultSet pretragaPacijenta(String ime, String prezime) throws SQLException 
    {
        Statement upitBaza=(Statement) veza.createStatement();
        ResultSet rezultat=null;
        try
        {
            rezultat=upitBaza.executeQuery("select Pacijent.id, Pacijent.ime, Pacijent.prezime, Pacijent.godiste from Pacijent WHERE Pacijent.ime LIKE '%" + ime + "%' OR Pacijent.prezime LIKE '%" + prezime + "%' " ); 
            return rezultat;
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
        return rezultat;
    }
    
    //dio koda za potreban combo box
        public List<String> ImePacijenta(){
           String query="Select ime from Pacijent ";
           List<String> a=new ArrayList<>();
           try{
               Statement stmt=veza.createStatement();
               ResultSet rs=stmt.executeQuery(query);
               while(rs.next()){
                   String ime=rs.getString("ime");
                   a.add(ime);      
               }
               
           }catch(SQLException e){
               e.printStackTrace();
           }
           return a;
       }
         
    
    
         public List<String> NazivVakcine(){
           String query="Select naziv from Vakcina ";
           List<String> a=new ArrayList<>();
           try{
               Statement stmt=veza.createStatement();
               ResultSet rs=stmt.executeQuery(query);
               while(rs.next()){
                   String naziv=rs.getString("naziv");
                   a.add(naziv);      
               }
               
           }catch(SQLException e){
               e.printStackTrace();
           }
           return a;
       }
}
