/*******************************************************************************
 * Copyright (c) 2016 Footeware.ca
 *******************************************************************************/
/**
 * 
 */
package ca.footeware.web.controllers;

import java.io.File;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Footeware.ca
 *
 */
@Controller
public class JokesController {

	private DB db;
	private ConcurrentMap<String, String> map;

	private void init() {
		db = DBMaker.fileDB(new File("file.db")).closeOnJvmShutdown().fileMmapEnable().make();
		map = db.hashMap("map", Serializer.STRING, Serializer.STRING).createOrOpen();
		map.put("Test", "I fart you choke");
		db.commit();
	}

	private ConcurrentMap<String, String> getMap() {
		if (map == null) {
			init();
		}
		return map;
	}

	private DB getDB() {
		if (db == null) {
			init();
		}
		return db;
	}

	@RequestMapping("/jokes")
	public String getTitles(Model model) {
		Set<String> titles = getMap().keySet();
		model.addAttribute("titles", titles);
		return "jokes";
	}

	@RequestMapping("/jokes/{title}")
	public String getJoke(@PathVariable("title") String title, Model model) {
		String body = getMap().get(title);
		model.addAttribute("title", title);
		model.addAttribute("body", body);
		return "joke";
	}

	@RequestMapping(value = "/addjoke", method = RequestMethod.GET)
	public String getAddJokePage(Model model) {
		return "addjoke";
	}

	@RequestMapping(value = "/jokes/add", method = RequestMethod.POST)
	public String postJoke(@RequestParam("title") String title, @RequestParam("body") String body, Model model) {
		String existing = getMap().get(title);
		if (existing != null) {
			model.addAttribute("error", "A joke by that title exists. Please choose another.");
			model.addAttribute("title", title);
			model.addAttribute("body", body);
			return "addjoke";
		}
		getMap().put(title, body);
		getDB().commit();
		Set<String> titles = getMap().keySet();
		model.addAttribute("titles", titles);
		return "jokes";
	}

	@RequestMapping(value = "/deletejoke/{title}", method = RequestMethod.GET)
	public String deleteJoke(@PathVariable("title") String title, Model model) {
		getMap().remove(title);
		getDB().commit();
		Set<String> titles = getMap().keySet();
		model.addAttribute("titles", titles);
		return "jokes";
	}
}
