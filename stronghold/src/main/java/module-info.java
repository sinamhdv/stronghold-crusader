module stronghold {
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive com.google.gson;
	requires transitive org.apache.commons.io;
	requires transitive org.apache.commons.codec;

	exports stronghold;
	exports stronghold.view;
	exports stronghold.controller.messages;
	exports stronghold.model.map;
	exports stronghold.model.buildings;
	exports stronghold.model.people;
	exports stronghold.model.environment;
	exports stronghold.model;
	exports stronghold.server;

	opens stronghold.model.chat to com.google.gson;
	opens stronghold.network to com.google.gson;
	opens stronghold.model to com.google.gson;
	opens stronghold.model.people to com.google.gson;
	opens stronghold.model.buildings to com.google.gson;
	opens stronghold.model.environment to com.google.gson;
	opens stronghold.model.map to com.google.gson;
	opens stronghold.view to javafx.fxml;
}
