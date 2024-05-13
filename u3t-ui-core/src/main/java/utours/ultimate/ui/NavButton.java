package utours.ultimate.ui;

public interface NavButton {

    String getName();

    void setName(String name);

    int getPosition();

    void setPosition(int position);

    void setOnClick(OnClickButton onClickButton);

    OnClickButton onClick();

}
