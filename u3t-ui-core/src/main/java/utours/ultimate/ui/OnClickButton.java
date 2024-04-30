package utours.ultimate.ui;

import utours.ultimate.ui.event.MouseEvent;

@FunctionalInterface
public interface OnClickButton {

    void performClick(MouseEvent event);

}
