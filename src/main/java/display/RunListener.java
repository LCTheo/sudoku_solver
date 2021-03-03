package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunListener implements ActionListener {

    private final Display display;

    public RunListener(Display display) {
        this.display = display;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        display.runResolver();
    }
}
