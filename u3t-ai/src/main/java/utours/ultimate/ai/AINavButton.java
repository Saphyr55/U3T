package utours.ultimate.ai;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.ui.NavButton;
import utours.ultimate.ui.OnClickButton;

@Component
@Mapping(type = Mapping.Type.Additional)
public class AINavButton implements NavButton {

    public static final int POSITION = 4;

    private String name = "AI";
    private OnClickButton onClickButton = event -> { };

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPosition() {
        return POSITION;
    }

    @Override
    public OnClickButton getOnClick() {
        return onClickButton;
    }

    @Override
    public void setOnClick(OnClickButton onClickButton) {
        this.onClickButton = onClickButton;
    }


}
