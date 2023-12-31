package knight.arkham.objects.structures;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.AssetsHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.*;

public class Platform extends InteractiveStructure {
    private final Texture blueSprite;
    private final Texture pinkSprite;
    private boolean isBlue;
    public Platform(Rectangle rectangle, World world, boolean isBlue) {
        super(rectangle, world);

        this.isBlue = isBlue;

        Filter filter = new Filter();

        if (isBlue)
            filter.categoryBits = GROUND_BIT;
        else
            filter.categoryBits = PINK_GROUND_BIT;

        fixture.setFilterData(filter);

        blueSprite = new Texture("images/blue.jpg");
        pinkSprite = new Texture("images/pink.jpg");
    }

    public void draw(Batch batch) {

        if (isBlue)
            super.draw(batch, blueSprite);
        else
            super.draw(batch, pinkSprite);
    }

    @Override
    protected Fixture createFixture() {
        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds,0, actualWorld, this)
        );
    }

    public void changeColor(){
        isBlue = !isBlue;

        Filter filter = new Filter();

        if (isBlue)
            filter.categoryBits = GROUND_BIT;
        else
            filter.categoryBits = PINK_GROUND_BIT;

        fixture.setFilterData(filter);

        Sound sound = AssetsHelper.loadSound("drop.wav");

        sound.play(0.05f);
    }

    public void dispose() {
        blueSprite.dispose();
        pinkSprite.dispose();
    }
}
