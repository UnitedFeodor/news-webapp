package by.htp.ex.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final CommandProvider provider = new CommandProvider();
       

	private final String COMMAND = "command";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		parseAndExecuteCommand(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		parseAndExecuteCommand(request, response);
	}

	private void parseAndExecuteCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandName = request.getParameter(COMMAND);

		Command command = provider.getCommand(commandName);
		command.execute(request, response);
	}

}
