package javafx;

import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.Node;

/**
 * Subclass of Alert automatically using an Ok button, an Error symbol,
 * and the GNU/Linux message cutoff fix.
 */
public class WarningAlert extends Alert {
    public WarningAlert(String contentText) {
        super(Alert.AlertType.ERROR, contentText, ButtonType.OK);

        // Fix cutoff bug on GNU/Linux
        // See: http://stackoverflow.com/a/33905734/321301
        List<Node> kiddos = getDialogPane().getChildren();
        for (Node n : kiddos) {
            if (n instanceof Label) {
                Label label = (Label) n;
                label.setMinHeight(Label.USE_PREF_SIZE);
            }
        }
    }
}
