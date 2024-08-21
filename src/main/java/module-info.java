module org.example.reminderfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens org.example.reminderfx to javafx.fxml;
    exports org.example.reminderfx;
}
