/*******************************************************************************
 * Copyright (c) 2016 Footeware.ca
 *******************************************************************************/
/**
 * 
 */
package ca.footeware.web.controllers;

import java.io.File;
import java.util.Set;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Footeware.ca
 *
 */
@Controller
public class JokesController {

	private DB db;
	private HTreeMap<String, String> map;

	private void init() {
		db = DBMaker.fileDB(new File("file.db")).closeOnJvmShutdown().make();
		map = db.hashMap("map");
		map.put("test", "I fart you choke");
		db.commit();
	}

	private HTreeMap<String, String> getMap() {
		if (map == null) {
			init();
		}
		return map;
	}

	@RequestMapping("/jokes")
	public String getTitles(Model model) {
		Set<String> titles = getMap().keySet();
		model.addAttribute("titles", titles);
		return "jokes";
	}

	@RequestMapping("/jokes/{title}")
	public String getJoke(@PathVariable("title") String title, Model model) {
		String joke = getMap().get(title);
		model.addAttribute("joke", joke);
		return "jokes";
	}
}
