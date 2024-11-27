module controller.libraryapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires org.mariadb.jdbc;

    opens controller.libraryapp to javafx.fxml;

    opens controller.fxml_designs to javafx.fxml;
    exports controller.libraryapp;
    exports model;
    opens model to javafx.fxml;
    exports Util;
    opens Util to javafx.fxml;
}
