/*******************************************************************************
 * Copyright (c) 2016 Footeware.ca
 *******************************************************************************/
/**
 * 
 */
package ca.footeware.web.controllers;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Footeware.ca
 *
 */
@Controller
public class JokesController {

	private DB db;
	private ConcurrentMap<String, String> map;

	private DB getDB() {
		if (db == null) {
			db = DBMaker.fileDB("file.db").transactionEnable().closeOnJvmShutdown().make();
			map = db.hashMap("map", Serializer.STRING, Serializer.STRING).createOrOpen();
			map.put("test", "I fart you choke");
			db.commit();
			db.close();
		}
		return db;
	}

	@RequestMapping("/jokes" )
	public String greeting(@RequestParam(value = "title", required = true) String title, Model model) {
		model.addAttribute("title", title);
		return "jokes";
	}
}
