package com.example.ProjetExam.controller;

import com.example.ProjetExam.models.Cart;
import com.example.ProjetExam.models.Product;
import com.example.ProjetExam.models.User;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.example.ProjetExam.services.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.ProjetExam.services.userService;
import com.example.ProjetExam.services.productService;
import com.example.ProjetExam.services.cartService;



@Controller
public class UserController{
	
	@Autowired
	private userService userService;

	@Autowired
	private productService productService;

	@GetMapping("/register")
	public String registerUser()
	{
		return "register";
	}

	@GetMapping("/seeDetails")
	public String details()
	{
		return "single-product";
	}
	@GetMapping("/buy")
	public String buy()
	{
		return "buy";
	}
	
	
	
	@GetMapping("/home")
	public String home()
	{
		return "index";
	}


	@GetMapping("/login")
	public String userlogin(Model model) {
		
		return "userLogin";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		
		return "shop";
	}
	
	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public ModelAndView userlogin( @RequestParam("username") String username, @RequestParam("password") String pass,Model model,HttpServletResponse res) {
		
		System.out.println(pass);
		User u = this.userService.checkLogin(username, pass);
		System.out.println(u.getUsername());

		if(username.equals(u.getUsername())) {

			res.addCookie(new Cookie("username", u.getUsername()));
			ModelAndView mView  = new ModelAndView("index1");	
			mView.addObject("user", u);
			List<Product> products = this.productService.getProducts();

			if (products.isEmpty()) {
				mView.addObject("msg", "No products are available");
			} else {
				mView.addObject("products", products);
			}
			return mView;

		}else {
			ModelAndView mView = new ModelAndView("userLogin");
			mView.addObject("msg", "Please enter correct email and password");
			return mView;
		}
		
	}
	
	
	@GetMapping("/user/products")
	public ModelAndView getproduct() {

		ModelAndView mView = new ModelAndView("uproduct");

		List<Product> products = this.productService.getProducts();

		if(products.isEmpty()) {
			mView.addObject("msg","No products are available");
		}else {
			mView.addObject("products",products);
		}

		return mView;
	}
	@RequestMapping(value = "newuserregister", method = RequestMethod.POST)
	
	public ModelAndView newUseRegister(@ModelAttribute User user, @RequestParam("role") String role) {
	    // Check if username already exists in database
	    boolean exists = this.userService.checkUserExists(user.getUsername());

	    if (!exists) {
	        user.setRole(role); // Définir le rôle de l'utilisateur

	        this.userService.addUser(user);

	        System.out.println("New user created: " + user.getUsername());
	        ModelAndView mView = new ModelAndView("userLogin");
	        return mView;
	    } else {
	        System.out.println("New user not created - username taken: " + user.getUsername());
	        ModelAndView mView = new ModelAndView("register");
	        mView.addObject("msg", user.getUsername() + " is taken. Please choose a different username.");
	        return mView;
	    }
	}

	
	
	
	   //for Learning purpose of model
		@GetMapping("/test")
		public String Test(Model model)
		{
			System.out.println("test page");
			model.addAttribute("author","jay gajera");
			model.addAttribute("id",40);
			
			List<String> friends = new ArrayList<String>();
			model.addAttribute("f",friends);
			friends.add("xyz");
			friends.add("abc");
			
			return "test";
		}
		
		// for learning purpose of model and view ( how data is pass to view)
		
		@GetMapping("/test2")
		public ModelAndView Test2()
		{
			System.out.println("test page");
			//create modelandview object
			ModelAndView mv=new ModelAndView();
			mv.addObject("name","jay gajera 17");
			mv.addObject("id",40);
			mv.setViewName("test2");
			
			List<Integer> list=new ArrayList<Integer>();
			list.add(10);
			list.add(25);
			mv.addObject("marks",list);
			return mv;
			
			
		}


//	@GetMapping("carts")
//	public ModelAndView  getCartDetail()
//	{
//		ModelAndView mv= new ModelAndView();
//		List<Cart>carts = cartService.getCarts();
//	}
	  
}
