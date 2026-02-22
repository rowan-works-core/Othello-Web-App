package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Stone;
import com.example.service.OthelloService;


@Controller
public class OthelloController {
	
	private final OthelloService service;
	
	public OthelloController(OthelloService service) {
        this.service = service;
    }
	
	@GetMapping("/")
	public String showBoard(Model model) {
        model.addAttribute("board", service.getBoard());
        model.addAttribute("turn", service.getCurrentTurn());
        model.addAttribute("blackCount", service.countStones(Stone.BLACK));
        model.addAttribute("whiteCount", service.countStones(Stone.WHITE));
        model.addAttribute("gameOver", service.isGameOver());
        model.addAttribute("winner", service.getWinner());
        model.addAttribute("pass", service.wasLastMovePass());
        return "board";
	}
	
	@PostMapping("/place")
	public String placeStone(@RequestParam int row, @RequestParam int col) {
		
		service.placeStone(row, col);
		return "redirect:/";
	}

}
