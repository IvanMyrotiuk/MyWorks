package com.java.myrotiuk.repository.pizza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.java.myrotiuk.domain.Pizza;
import com.java.myrotiuk.domain.Pizza.Type;

//@Repository
public class SpringJDBCPizzaRepository implements PizzaRepository {

//	@Autowired
//	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTamplate; //Template
	
	@Override
	public Pizza find(long id) {
		String sql = "select * from pizza where id = ?";
		return jdbcTamplate.queryForObject(sql, new Object[]{id}, new PizzaMapper());
	}
	
	private static final class PizzaMapper implements RowMapper<Pizza>{
		@Override
		public Pizza mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Pizza(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), Type.valueOf(rs.getString("type")));
		}
	}
	
	public List<Pizza> getAll(){
		String sql = "select * from pizza";
		return jdbcTamplate.query(sql, new PizzaMapper());
	}
	
	public void update(Pizza pizza){
		String sql = "update pizza set name = ? where id = ?";
		jdbcTamplate.update(sql, pizza.getName(), pizza.getId());
	}
	
	public long insert(Pizza pizza){
		
		String sql = "insert into pizza(name, price, type) values (?,?,?);";
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTamplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				
				PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				
				stmt.setString(1, pizza.getName());
				stmt.setDouble(2, pizza.getPrice());
				stmt.setString(3, pizza.getType().toString());
				return stmt;
			}
		}, holder);
		
		return holder.getKey().intValue();
	}
	
	public void delete(long id){
		String sql = "delete from pizza where id = ?";
		jdbcTamplate.update(sql, id);
	}

}
