package utours.ultimate.ui;

import java.util.Comparator;

public interface NavButton {

    String getName();

    void setName(String name);

    int getPosition();

    void setOnClick(OnClickButton onClickButton);

    OnClickButton onClick();

}
