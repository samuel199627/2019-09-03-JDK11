package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Portion> listAllPortionsCalories(int calories){
		String sql = "select  portion_id, portion_amount, portion_display_name,calories, saturated_fats, food_code " + 
				"from portion " + 
				"where calories<?" ;
		
		List<Portion> porzioniVertici=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calories);
			
		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					porzioniVertici.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			System.out.println("\nPORZIONI: \n");
			for(Portion p: porzioniVertici) {
				System.out.println(p.toString());
			}
			
			conn.close();
			return porzioniVertici ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	/*
	 	otteniamo sia le adiacenze in una direzione che dall'altra ma le filtriamo poi dopo nella creazione degli archi
	 */
	public List<Adiacenza> listAllAdiacenze(){
		String sql = "select p1.portion_display_name,p2.portion_display_name, count(distinct p1.food_code) as peso\n" + 
				"from portion as p1, portion as p2\n" + 
				"where p1.portion_display_name<>p2.portion_display_name and\n" + 
				"	p1.food_code=p2.food_code \n" + 
				"group by p1.portion_display_name,p2.portion_display_name" ;
		
		List<Adiacenza> adiacenze=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			
		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					adiacenze.add(new Adiacenza(
							res.getString("p1.portion_display_name"), 
							res.getString("p2.portion_display_name"), 
							res.getInt("peso")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			
			conn.close();
			return adiacenze ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	

}
