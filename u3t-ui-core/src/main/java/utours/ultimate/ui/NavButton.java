package utours.ultimate.ui;

public interface NavButton {
    
    String getName();

    void setName(String name);

    int getPosition();

    void setOnClick(OnClickButton onClickButton);

    OnClickButton getOnClick();

}
