module stronghold {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive com.google.gson;
    requires transitive org.apache.commons.io;
	requires transitive org.apache.commons.codec;

    exports stronghold.view;
    exports stronghold.controller.messages;
    exports stronghold.model.map;
    exports stronghold.model.buildings;
    exports stronghold.model.people;
    exports stronghold.model.environment;
    exports stronghold.model;

    opens stronghold.model to com.google.gson;
    opens stronghold.view to javafx.fxml;
}
