package by.htp.ex.controller;

import java.io.IOException;
import java.io.Serial;

import by.htp.ex.controller.constants.ViewConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FrontController extends HttpServlet {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private final CommandProvider provider = new CommandProvider();
       

	//private final String COMMAND = "command";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		parseAndExecuteCommand(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		parseAndExecuteCommand(request, response);
	}

	private void parseAndExecuteCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String commandName = request.getParameter(ViewConstants.COMMAND);

		Command command = provider.getCommand(commandName);
		command.execute(request, response);
	}

	// TODO instead of error page every time, do the last request again and show a message?

}
