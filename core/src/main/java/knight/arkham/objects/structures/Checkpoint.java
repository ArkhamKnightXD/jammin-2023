package knight.arkham.objects.structures;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.*;

import static knight.arkham.helpers.Constants.DESTROYED_BIT;
import static knight.arkham.helpers.Constants.GAME_DATA_FILENAME;

public class Checkpoint extends InteractiveStructure {
    private final Texture sprite;
    private boolean isDestroyed;

    public Checkpoint(Rectangle rectangle, World world) {
        super(rectangle, world);

        sprite = new Texture("images/gray.jpg");
    }

    public void draw(Batch batch) {

        if (!isDestroyed)
            super.draw(batch, sprite);
    }

    @Override
    protected Fixture createFixture() {

        return Box2DHelper.createStaticFixture(
            new Box2DBody(actualBounds,0, actualWorld, this)
        );
    }

    public void createCheckpoint() {

        Filter filter = new Filter();

        filter.categoryBits = DESTROYED_BIT;
        fixture.setFilterData(filter);

        Sound sound = AssetsHelper.loadSound("okay.wav");
        sound.play();

        GameDataHelper.saveGameData(GAME_DATA_FILENAME, body.getPosition());

        isDestroyed = true;
    }

    public void dispose() {sprite.dispose();}
}
